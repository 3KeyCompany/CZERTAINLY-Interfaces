package com.czertainly.api.clients;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.common.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.attribute.content.BaseAttributeContent;
import com.czertainly.api.model.common.attribute.content.FileAttributeContent;
import com.czertainly.api.model.core.connector.ConnectorDto;
import com.czertainly.api.model.core.connector.ConnectorStatus;
import com.czertainly.core.util.AttributeDefinitionUtils;
import com.czertainly.core.util.KeyStoreUtils;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.*;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseApiClient {
    private static final Logger logger = LoggerFactory.getLogger(BaseApiClient.class);

    // Basic auth attribute names
    public static final String ATTRIBUTE_USERNAME = "username";
    public static final String ATTRIBUTE_PASSWORD = "password";

    // Certificate attribute names
    public static final String ATTRIBUTE_KEYSTORE_TYPE = "keyStoreType";
    public static final String ATTRIBUTE_KEYSTORE = "keyStore";
    public static final String ATTRIBUTE_KEYSTORE_PASSWORD = "keyStorePassword";
    public static final String ATTRIBUTE_TRUSTSTORE_TYPE = "trustStoreType";
    public static final String ATTRIBUTE_TRUSTSTORE = "trustStore";
    public static final String ATTRIBUTE_TRUSTSTORE_PASSWORD = "trustStorePassword";

    // API key attribute names
    public static final String ATTRIBUTE_API_KEY_HEADER = "apiKeyHeader";
    public static final String ATTRIBUTE_API_KEY = "apiKey";

    protected WebClient webClient;

    public WebClient.RequestBodyUriSpec prepareRequest(HttpMethod method, ConnectorDto connector, Boolean validateConnectorStatus) {
        if(validateConnectorStatus){
            validateConnectorStatus(connector.getStatus());
        }
        WebClient.RequestBodySpec request;

        // for backward compatibility
        if (connector.getAuthType() == null) {
            request = webClient.method(method);
            return (WebClient.RequestBodyUriSpec) request;
        }

        List<ResponseAttributeDto> authAttributes = connector.getAuthAttributes();

        switch (connector.getAuthType()) {
            case NONE:
                request = webClient.method(method);
                break;
            case BASIC:
                BaseAttributeContent<String> username = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_USERNAME, authAttributes);
                BaseAttributeContent<String> password = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_PASSWORD, authAttributes);

                request = webClient
                        .method(method)
                        .headers(h -> h.setBasicAuth(username.getValue(), password.getValue()));
                break;
            case CERTIFICATE:
                SslContext sslContext = createSslContext(authAttributes);
                HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
                webClient.mutate().clientConnector(new ReactorClientHttpConnector(httpClient)).build();

                request = webClient.method(method);
                break;
            case API_KEY:
                BaseAttributeContent<String> apiKeyHeader = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_API_KEY_HEADER, authAttributes);
                BaseAttributeContent<String> apiKey = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_API_KEY, authAttributes);

                request = webClient
                        .method(method)
                        .headers(h -> h.set(apiKeyHeader.getValue(), apiKey.getValue()));
                break;
            case JWT:
                throw new UnsupportedOperationException("JWT is unimplemented");
            default:
                throw new IllegalArgumentException("Unknown auth type " + connector.getAuthType());
        }

        return (WebClient.RequestBodyUriSpec) request;
    }

    public void validateConnectorStatus(ConnectorStatus connectorStatus) throws ValidationException {
        if(connectorStatus.equals(ConnectorStatus.WAITING_FOR_APPROVAL)){
            throw new ValidationException(ValidationError.create("Connector has invalid status: Waiting For Approval"));
        }
    }

    private SslContext createSslContext(List<ResponseAttributeDto> attributes) {
        try {
            SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();

            KeyManager km = null;
            FileAttributeContent keyStoreData = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_KEYSTORE, attributes);
            if (keyStoreData != null && !keyStoreData.getValue().isEmpty()) {
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()); //"SunX509"

                BaseAttributeContent<String> keyStoreType = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_KEYSTORE_TYPE, attributes);
                BaseAttributeContent<String> keyStorePassword = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_KEYSTORE_PASSWORD, attributes);
                byte[] keyStoreBytes = Base64.getDecoder().decode(keyStoreData.getValue());

                kmf.init(KeyStoreUtils.bytes2KeyStore(keyStoreBytes, keyStorePassword.getValue(), keyStoreType.getValue()), keyStorePassword.getValue().toCharArray());
                km = kmf.getKeyManagers()[0];
            }

            sslContextBuilder.keyManager(km);

            TrustManager tm;
            FileAttributeContent trustStoreData = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_TRUSTSTORE, attributes);
            if (trustStoreData != null && !trustStoreData.getValue().isEmpty()) {
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()); //"SunX509"

                BaseAttributeContent<String> trustStoreType = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_TRUSTSTORE_TYPE, attributes);
                BaseAttributeContent<String> trustStorePassword = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_TRUSTSTORE_PASSWORD, attributes);
                byte[] trustStoreBytes = Base64.getDecoder().decode(trustStoreData.getValue());

                tmf.init(KeyStoreUtils.bytes2KeyStore(trustStoreBytes, trustStorePassword.getValue(), trustStoreType.getValue()));
                tm = tmf.getTrustManagers()[0];

                sslContextBuilder.trustManager(tm);
            }

            return sslContextBuilder.protocols("TLSv1.2").build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize SslContext.", e);
        }
    }

    private static final ParameterizedTypeReference<List<String>> ERROR_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public static WebClient prepareWebClient() {
        return WebClient.builder()
                .filter(ExchangeFilterFunction.ofResponseProcessor(BaseApiClient::handleHttpExceptions))
                .build();
    }

    private static Mono<ClientResponse> handleHttpExceptions(ClientResponse clientResponse) {
        if (HttpStatus.UNPROCESSABLE_ENTITY.equals(clientResponse.statusCode())) {
            return clientResponse.bodyToMono(ERROR_LIST_TYPE_REF).flatMap(body ->
                    Mono.error(new ValidationException(body.stream()
                                    .map(ValidationError::create)
                                    .collect(Collectors.toList())
                            )
                    )
            );
        }
        if (HttpStatus.NOT_FOUND.equals(clientResponse.statusCode())) {
            return clientResponse.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new NotFoundException(body)));
        }
        if (clientResponse.statusCode().is4xxClientError()) {
            return clientResponse.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new ConnectorClientException(body, clientResponse.statusCode())));
        }
        if (clientResponse.statusCode().is5xxServerError()) {
            return clientResponse.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new ConnectorServerException(body, clientResponse.statusCode())));
        }
        return Mono.just(clientResponse);
    }

    public static <T, R> R processRequest(Function<T, R> func, T request, ConnectorDto connector) throws ConnectorException {
        try {
            return func.apply(request);
        } catch (Exception e) {
            Throwable unwrapped = Exceptions.unwrap(e);
            logger.error(unwrapped.getMessage(), unwrapped);

            if (unwrapped instanceof IOException || unwrapped instanceof WebClientRequestException) {
                throw new ConnectorCommunicationException(unwrapped.getMessage(), unwrapped, connector);
            } else if (unwrapped instanceof ConnectorException) {
                ConnectorException ce = (ConnectorException) unwrapped;
                ce.setConnector(connector);
                throw ce;
            } else {
                throw e;
            }
        }
    }
}
