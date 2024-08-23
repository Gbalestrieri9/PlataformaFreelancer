package br.com.plataformafreelancer.fourcamp.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

public class ErrorResponseFactory {

    public static CustomErrorResponse createResponseError(String message, String path, Integer httpStatus) {
        return new CustomErrorResponse(message, path, httpStatus);
    }
}