package br.com.plataformafreelancer.fourcamp.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.util.Locale;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@Builder
public class CustomErrorResponse implements ErrorResponse {

    private String mensagem;
    private String path;
    private Integer code;

    public CustomErrorResponse(String mensagem, String path, Integer code) {
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
