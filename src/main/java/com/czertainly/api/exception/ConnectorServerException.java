package com.czertainly.api.exception;

import com.czertainly.api.model.core.connector.ConnectorDto;
import org.springframework.http.HttpStatus;

public class ConnectorServerException extends ConnectorException {

    private HttpStatus httpStatus;

    public ConnectorServerException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ConnectorServerException(HttpStatus httpStatus, ConnectorDto connector) {
        super(connector);
        this.httpStatus = httpStatus;
    }

    public ConnectorServerException(String message, HttpStatus httpStatus, ConnectorDto connector) {
        super(message, connector);
        this.httpStatus = httpStatus;
    }

    public ConnectorServerException(String message, Throwable cause, HttpStatus httpStatus, ConnectorDto connector) {
        super(message, cause, connector);
        this.httpStatus = httpStatus;
    }

    public ConnectorServerException(Throwable cause, HttpStatus httpStatus, ConnectorDto connector) {
        super(cause, connector);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
