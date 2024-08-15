package br.com.plataformafreelancer.fourcamp.exceptions;

import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;

public class ValorInvalidoException extends NegocioException {
    public ValorInvalidoException(String message) {
        super(message);
    }
}
