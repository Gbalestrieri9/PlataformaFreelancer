package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.exceptions.ValorInvalidoException;

public class ValidadorDeValoresNegativos {

    private ValidadorDeValoresNegativos() {
    }

    public static float validarValorFloat(String valor) {
        float valorValidado;

        try {
            valorValidado = Float.parseFloat(valor);
            if (valorValidado <= 0) {
                throw new ValorInvalidoException(
                        (ErrorCode.VALOR_INVALIDO_VALOR_DEPOSITO.getCustomMessage() + valor)
                );
            }
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException(
                    (ErrorCode.VALOR_INVALIDO_VALOR_DEPOSITO.getCustomMessage() + valor)
            );
        }
        return valorValidado;
    }

}
