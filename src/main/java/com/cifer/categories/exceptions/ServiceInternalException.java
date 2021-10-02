package com.cifer.categories.exceptions;

import com.cifer.categories.operation.Code;

/**
 * Server internal exception
 */
public class ServiceInternalException extends GenericException{
    public ServiceInternalException(String message, Throwable cause) {
        super(Code.SERVER_INTERNAL, message, cause);
    }
    public ServiceInternalException(String message) {
        super(Code.SERVER_INTERNAL, message);
    }
    public ServiceInternalException(Throwable cause) {
        super(Code.SERVER_INTERNAL, cause);
    }
}
