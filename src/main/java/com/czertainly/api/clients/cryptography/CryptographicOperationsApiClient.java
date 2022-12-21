package com.czertainly.api.clients.cryptography;

import com.czertainly.api.clients.BaseApiClient;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.enums.CryptographicAlgorithm;
import com.czertainly.api.model.connector.cryptography.operations.*;
import com.czertainly.api.model.core.connector.ConnectorDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class CryptographicOperationsApiClient extends BaseApiClient {

    private static final String CRYPTOP_BASE_CONTEXT = "/v1/cryptographyProvider/tokens/{uuid}/keys";
    private static final String CRYPTOP_ENCRYPT_CONTEXT = CRYPTOP_BASE_CONTEXT + "/encrypt";
    private static final String CRYPTOP_DECRYPT_CONTEXT = CRYPTOP_BASE_CONTEXT + "/decrypt";
    private static final String CRYPTOP_CIPHER_ATTRS_CONTEXT = CRYPTOP_BASE_CONTEXT + "/cipher/{algorithm}/attributes";
    private static final String CRYPTOP_CIPHER_ATTRS_VALIDATE_CONTEXT = CRYPTOP_CIPHER_ATTRS_CONTEXT + "/validate";
    private static final String CRYPTOP_SIGN_CONTEXT = CRYPTOP_BASE_CONTEXT + "/sign";
    private static final String CRYPTOP_VERIFY_CONTEXT = CRYPTOP_BASE_CONTEXT + "/verify";
    private static final String CRYPTOP_SIGNATURE_ATTRS_CONTEXT = CRYPTOP_BASE_CONTEXT + "/signature/{algorithm}/attributes";
    private static final String CRYPTOP_SIGNATURE_ATTRS_VALIDATE_CONTEXT = CRYPTOP_SIGNATURE_ATTRS_CONTEXT + "/validate";
    private static final String CRYPTOP_RANDOM_CONTEXT = CRYPTOP_BASE_CONTEXT + "/random";
    private static final String CRYPTOP_RANDOM_ATTRS_CONTEXT = CRYPTOP_RANDOM_CONTEXT + "/attributes";
    private static final String CRYPTOP_RANDOM_ATTRS_VALIDATE_CONTEXT = CRYPTOP_RANDOM_ATTRS_CONTEXT + "/validate";

    private static final ParameterizedTypeReference<List<RequestAttributeDto>> ATTRIBUTE_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public CryptographicOperationsApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<BaseAttribute> listCipherAttributes(ConnectorDto connector, String tokenInstanceUuid, CryptographicAlgorithm algorithm) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_CIPHER_ATTRS_CONTEXT, tokenInstanceUuid, algorithm)
                .retrieve()
                .toEntityList(BaseAttribute.class)
                .block().getBody(),
                request,
                connector);
    }

    public void validateCipherAttributes(ConnectorDto connector, String tokenInstanceUuid, CryptographicAlgorithm algorithm, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_CIPHER_ATTRS_VALIDATE_CONTEXT, tokenInstanceUuid, algorithm)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

    public EncryptDataResponseDto encryptData(ConnectorDto connector, String tokenInstanceUuid, CipherDataRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_ENCRYPT_CONTEXT, tokenInstanceUuid)
                .body(Mono.just(requestDto), CipherDataRequestDto.class)
                .retrieve()
                .toEntity(EncryptDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public DecryptDataResponseDto decryptData(ConnectorDto connector, String tokenInstanceUuid, CipherDataRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_DECRYPT_CONTEXT, tokenInstanceUuid)
                .body(Mono.just(requestDto), CipherDataRequestDto.class)
                .retrieve()
                .toEntity(DecryptDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public List<BaseAttribute> listSignatureAttributes(ConnectorDto connector, String tokenInstanceUuid, CryptographicAlgorithm algorithm) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_SIGNATURE_ATTRS_CONTEXT, tokenInstanceUuid, algorithm)
                .retrieve()
                .toEntityList(BaseAttribute.class)
                .block().getBody(),
                request,
                connector);
    }

    public void validateSignatureAttributes(ConnectorDto connector, String tokenInstanceUuid, CryptographicAlgorithm algorithm, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_SIGNATURE_ATTRS_VALIDATE_CONTEXT, tokenInstanceUuid, algorithm)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

    public SignDataResponseDto signData(ConnectorDto connector, String tokenInstanceUuid, SignDataRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_SIGN_CONTEXT, tokenInstanceUuid)
                .body(Mono.just(requestDto), SignDataRequestDto.class)
                .retrieve()
                .toEntity(SignDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public VerifyDataResponseDto verifyData(ConnectorDto connector, String tokenInstanceUuid, VerifyDataRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_VERIFY_CONTEXT, tokenInstanceUuid)
                .body(Mono.just(requestDto), VerifyDataRequestDto.class)
                .retrieve()
                .toEntity(VerifyDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public List<BaseAttribute> listRandomAttributes(ConnectorDto connector, String tokenInstanceUuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_RANDOM_ATTRS_CONTEXT, tokenInstanceUuid)
                .retrieve()
                .toEntityList(BaseAttribute.class)
                .block().getBody(),
                request,
                connector);
    }

    public void validateRandomAttributes(ConnectorDto connector, String tokenInstanceUuid, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_RANDOM_ATTRS_VALIDATE_CONTEXT, tokenInstanceUuid)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

    public RandomDataResponseDto randomData(ConnectorDto connector, String tokenInstanceUuid, RandomDataRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_RANDOM_CONTEXT, tokenInstanceUuid)
                .body(Mono.just(requestDto), RandomDataRequestDto.class)
                .retrieve()
                .toEntity(RandomDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

}