package com.cifer.categories.exceptions;

import com.cifer.categories.operation.Code;
import lombok.Getter;

/**
 * Custom exception class that could by thrown by application and handled properly
 */
@Getter
public class GenericException extends RuntimeException{
//    This code will be used in order to identify and handle exception
    private final Code code;
    public GenericException(Code code) {
        this.code = code;
    }
    public GenericException(Code code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    public GenericException(Code code, String message) {
        super(message);
        this.code = code;
    }
    public GenericException(Code code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
