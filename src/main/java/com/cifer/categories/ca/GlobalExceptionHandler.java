package com.cifer.categories.ca;

import com.cifer.categories.exceptions.GenericException;
import com.cifer.categories.operation.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * ControllerAdvice to handle all exceptions.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static String ERROR_VIEW = "error_message";

    @ExceptionHandler({GenericException.class})
    public ModelAndView handleGenericException(GenericException exception, WebRequest request) {
        return handleCode(exception.getCode());
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(Exception exception, WebRequest request) {
        log.error("Unhandled exception", exception);
        return handleCode(Code.SERVER_INTERNAL);
    }

    private ModelAndView handleCode(Code code) {
        var mav = new ModelAndView();
        mav.addObject("message", code.description());
        mav.setStatus(code.httpStatus());
        mav.setViewName(ERROR_VIEW);
        return mav;
    }
}
