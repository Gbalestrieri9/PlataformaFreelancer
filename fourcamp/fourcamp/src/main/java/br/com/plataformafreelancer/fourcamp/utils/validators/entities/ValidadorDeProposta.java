package br.com.plataformafreelancer.fourcamp.utils.validators.entities;

import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import br.com.plataformafreelancer.fourcamp.exceptions.StatusDaPropostaInvalidoException;
import br.com.plataformafreelancer.fourcamp.exceptions.ValorInvalidoException;
import br.com.plataformafreelancer.fourcamp.utils.ValidadorDeNumerosPositivos;

import java.util.Arrays;
import java.util.List;

public class ValidadorDeProposta {

    private ValidadorDeProposta() {
    }

    public static int validarIdFreelancer(String id) {
        int idValidado;

        try {
            idValidado = Integer.parseInt(id);

            if (!ValidadorDeNumerosPositivos.validarNumero(idValidado)) {
                throw new ValorInvalidoException(ErrorCode.ID_FREELANCER_ID_INVALIDO.getCustomMessage());
            }

        } catch (NumberFormatException e) {
            throw new ValorInvalidoException(ErrorCode.ID_FREELANCER_FORMATO_INVALIDO.getCustomMessage());
        }

        return idValidado;
    }

    public static int validarIdProposta(String id) {
        int idValidado;

        try {
            idValidado = Integer.parseInt(id);

            if (!ValidadorDeNumerosPositivos.validarNumero(idValidado)) {
                throw new ValorInvalidoException(ErrorCode.ID_PROPOSTA_ID_INVALIDO.getCustomMessage());
            }

        } catch (NumberFormatException e) {
            throw new ValorInvalidoException(ErrorCode.ID_PROPOSTA_FORMATO_INVALIDO.getCustomMessage());
        }

        return idValidado;
    }

    public static double validarValor(String valor) {
        double valorValidado;

        try {
            valorValidado = Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException(ErrorCode.VALOR_PROPOSTA_INVALIDO.getCustomMessage());
        }

        return valorValidado;
    }

    public static String validarStatusProposta(String statusProposta) {
        List<String> listaDeStatusDePropostas =
                Arrays.asList(
                        StatusProposta.ACEITA.getStatus(),
                        StatusProposta.PENDENTE.getStatus(),
                        StatusProposta.RECUSADA.getStatus()
                );

        if(!listaDeStatusDePropostas.contains(statusProposta)){
            throw new StatusDaPropostaInvalidoException(ErrorCode.PROPOSTA_STATUS_INEXISTENTE.getCustomMessage());
        } else {
            return statusProposta;
        }
    }
}
