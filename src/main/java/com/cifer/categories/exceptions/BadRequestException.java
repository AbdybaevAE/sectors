package com.cifer.categories.exceptions;

import com.cifer.categories.operation.Code;

/**
 * Bad request exception
 */
public class BadRequestException extends GenericException{
    public BadRequestException(String message, Throwable cause) {
        super(Code.BAD_REQUEST, message, cause);
    }
    public BadRequestException(String message) {
        super(Code.BAD_REQUEST, message);
    }
}
