package br.com.plataformafreelancer.fourcamp.utils.validators.entities;

import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import br.com.plataformafreelancer.fourcamp.exceptions.DataInvalidaException;
import br.com.plataformafreelancer.fourcamp.exceptions.StatusDaPropostaInvalidoException;
import br.com.plataformafreelancer.fourcamp.exceptions.ValorInvalidoException;
import br.com.plataformafreelancer.fourcamp.utils.ConstantesPtBr;
import br.com.plataformafreelancer.fourcamp.utils.validators.general.ValidadorDeNumerosPositivos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class ValidadorDeProposta {

    private ValidadorDeProposta() {
    }

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);


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

    public static LocalDate validarDataInicio(String dataInicio) {
        LocalDate dataInicioValidada;

        try{
            dataInicioValidada = LocalDate.parse(dataInicio, DATE_FORMATTER);

            if(dataInicioValidada.isBefore(LocalDate.now())){
                throw new DataInvalidaException(ConstantesPtBr.DATA_INICIO_PROJETO_INVALIDA);
            }

        } catch (DateTimeParseException e){
            throw new DataInvalidaException(ConstantesPtBr.DATA_FORMATO_INVALIDO);
        }


        return dataInicioValidada;
    }

    public static LocalDate validarDataFim(String dataFim) {
        LocalDate dataFimValidada;

        try{
            dataFimValidada = LocalDate.parse(dataFim, DATE_FORMATTER);

            if(dataFimValidada.isBefore(LocalDate.now())){
                throw new DataInvalidaException(ConstantesPtBr.DATA_FIM_PROJETO_INVALIDA);
            }

        } catch (DateTimeParseException e){
            throw new DataInvalidaException(ConstantesPtBr.DATA_FORMATO_INVALIDO);
        }

        return dataFimValidada;
    }

    public static void validarConsistenciaDatasInicioFim(LocalDate dataInicio, LocalDate dataFim) {
        if(dataFim.isBefore(dataInicio) || dataFim.equals(dataInicio)){
            throw new DataInvalidaException(ConstantesPtBr.DATAS_INICIO_FIM_PROJETO_INCONSISTENTES);
        }
    }
}
