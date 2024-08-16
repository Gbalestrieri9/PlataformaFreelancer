package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.exceptions.EmailInvalidoException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidadorDeEmail {

    private ValidadorDeEmail(){}
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    public static String validarEmail(String email) {
        String emailValidado;

        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new EmailInvalidoException(ErrorCode.EMAIL_INVALIDO.getCustomMessage() + email);
        } else {
            emailValidado = email;
        }

        return emailValidado;
    }
}
