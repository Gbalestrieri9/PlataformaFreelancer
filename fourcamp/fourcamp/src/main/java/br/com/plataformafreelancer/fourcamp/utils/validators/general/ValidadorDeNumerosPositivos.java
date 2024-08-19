package br.com.plataformafreelancer.fourcamp.utils.validators.general;

public class ValidadorDeNumerosPositivos {

    private ValidadorDeNumerosPositivos() {
    }

    public static boolean validarNumero(long numero) {
        return numero > 0;
    }
}
