package br.com.plataformafreelancer.fourcamp.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.util.Locale;


public class CustomErrorResponse implements ErrorResponse {

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private String mensagem;
    private String path;
    private Integer code;

    public CustomErrorResponse(String message, HttpServletRequest request, HttpStatus status) {
    }

    public CustomErrorResponse(String mensagem, String path, Integer code){
        this.mensagem = mensagem;
        this.path = path;
        this.code = code;
    }



    @JsonIgnore
    public HttpStatusCode getStatusCode() {
        return null;
    }

    @JsonIgnore
    public ProblemDetail getBody() {
        return null;
    }

    @JsonIgnore
    public String getTypeMessageCode() {
        return ErrorResponse.super.getTypeMessageCode();
    }

    @JsonIgnore
    public String getTitleMessageCode() {
        return ErrorResponse.super.getTitleMessageCode();
    }

    @JsonIgnore
    public String getDetailMessageCode() {
        return ErrorResponse.super.getDetailMessageCode();
    }

    @JsonIgnore
    public Object[] getDetailMessageArguments() {
        return ErrorResponse.super.getDetailMessageArguments();
    }

    @JsonIgnore
    public Object[] getDetailMessageArguments(MessageSource messageSource, Locale locale) {
        return ErrorResponse.super.getDetailMessageArguments(messageSource, locale);
    }

    @JsonIgnore
    public ProblemDetail updateAndGetBody(MessageSource messageSource, Locale locale) {
        return ErrorResponse.super.updateAndGetBody(messageSource, locale);
    }
}
