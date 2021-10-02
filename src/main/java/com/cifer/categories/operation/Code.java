package com.cifer.categories.operation;

import org.springframework.http.HttpStatus;

public enum Code {
    BAD_REQUEST,
    SERVER_INTERNAL;

    /**
     * Return http status for code.
     * @return
     */
    public HttpStatus httpStatus() {
        return switch (this) {
            case BAD_REQUEST -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    /**
     * Return human readable description for code.
     * According spring coding style such things must be implemented in resource files using MessageSource and locale.
     * For faster development I decided to do it in switch case manner.
     * @return
     */
    public String description() {
        return switch (this) {
            case BAD_REQUEST -> "Wrong data provided.";
            case SERVER_INTERNAL -> "Something wrong happened. Please, try again later.";
        };
    }
}
