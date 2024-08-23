package br.com.plataformafreelancer.fourcamp.utils.validators.general;

import br.com.plataformafreelancer.fourcamp.exceptions.ValorInvalidoException;
import br.com.plataformafreelancer.fourcamp.utils.ConstantesPtBr;

public class ValidadorDeNumerosPositivos {

    private ValidadorDeNumerosPositivos() {
    }

    public static boolean validarNumero(long numero) {
        return numero > 0;
    }

    public static boolean validarNumero(String numero) {

        try{
            int numeroValidado = Integer.parseInt(numero);

            return numeroValidado > 0;
        } catch (NumberFormatException e){
            throw new ValorInvalidoException(ConstantesPtBr.ID_INVALIDO);
        }
    }
}
