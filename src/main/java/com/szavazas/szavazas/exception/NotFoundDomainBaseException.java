package com.szavazas.szavazas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundDomainBaseException extends BaseException {

    public NotFoundDomainBaseException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
