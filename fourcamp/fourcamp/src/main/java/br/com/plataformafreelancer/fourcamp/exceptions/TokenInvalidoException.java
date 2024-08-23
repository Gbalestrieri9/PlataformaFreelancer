package br.com.plataformafreelancer.fourcamp.exceptions;

public class TokenInvalidoException extends NegocioException {
    public TokenInvalidoException(String mensagem) {
        super(mensagem);
    }
}
