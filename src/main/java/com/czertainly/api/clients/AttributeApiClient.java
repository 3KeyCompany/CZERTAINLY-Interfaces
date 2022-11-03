package com.czertainly.api.clients;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.attribute.v2.AbstractBaseAttribute;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.callback.RequestAttributeCallback;
import com.czertainly.api.model.core.connector.ConnectorDto;
import com.czertainly.api.model.core.connector.FunctionGroupCode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeApiClient extends BaseApiClient {

    private static final String ATTRIBUTE_BASE_CONTEXT = "/v1/{functionGroup}/{kind}/attributes";
    private static final String ATTRIBUTE_VALIDATION_CONTEXT = ATTRIBUTE_BASE_CONTEXT + "/validate";

    private static final ParameterizedTypeReference<List<RequestAttributeDto>> ATTRIBUTE_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public AttributeApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<BaseAttribute> listAttributeDefinitions(ConnectorDto connector, FunctionGroupCode functionGroupCode, String kind) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, false);

        return processRequest(r -> r
                .uri(connector.getUrl() + ATTRIBUTE_BASE_CONTEXT, functionGroupCode.getCode(), kind)
                .retrieve()
                .toEntityList(BaseAttribute.class)
                .block().getBody(),
                request,
                connector);
    }

    public List<AbstractBaseAttribute> listAttributeDefinitions1(ConnectorDto connector, FunctionGroupCode functionGroupCode, String kind) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, false);

        return processRequest(r -> r
                        .uri(connector.getUrl() + ATTRIBUTE_BASE_CONTEXT, functionGroupCode.getCode(), kind)
                        .retrieve()
                        .toEntityList(AbstractBaseAttribute.class)
                        .block().getBody(),
                request,
                connector);
    }

    public Void validateAttributes(ConnectorDto connector, FunctionGroupCode functionGroupCode, List<RequestAttributeDto> attributes, String functionGroupType) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + ATTRIBUTE_VALIDATION_CONTEXT, functionGroupCode.getCode(), functionGroupType)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

    public Object attributeCallback(ConnectorDto connector, AttributeCallback callback, RequestAttributeCallback callbackRequest) throws ConnectorException {
        HttpMethod method = HttpMethod.valueOf(callback.getCallbackMethod());

        URI uri;
        UriBuilder uriBuilder = UriComponentsBuilder.fromUriString(connector.getUrl());
        uriBuilder.path(callback.getCallbackContext());

        if (callbackRequest.getRequestParameter() != null) {
            callbackRequest.getRequestParameter().entrySet().stream()
                    .filter(q -> q.getValue() != null)
                    .forEach(q -> uriBuilder.queryParam(q.getKey(), q.getValue() instanceof Map ? ((Map) q.getValue()).get("value") : q.getValue()));
        }

        if (callbackRequest.getPathVariable() != null) {
            Map<String, Serializable> updatedPathVariables = new HashMap<>();
            for(Map.Entry<String,Serializable> entry : callbackRequest.getPathVariable().entrySet()){
                if( entry.getValue() instanceof Map){
                    updatedPathVariables.put(entry.getKey(), (Serializable) ((Map)entry.getValue()).get("value"));
                }else{
                    updatedPathVariables.put(entry.getKey(), entry.getValue());
                }
            }
            uri = uriBuilder.build(updatedPathVariables);
        } else {
            uri = uriBuilder.build();
        }

        WebClient.RequestBodySpec request =
                prepareRequest(method, connector, true)
                        .uri(uri);

        if (callbackRequest.getBody() != null) {
            request.bodyValue(callbackRequest.getBody());
        }

        return processRequest(r -> r
                .retrieve()
                .toEntity(Object.class)
                .block().getBody(),
                request,
                connector);
    }
}
