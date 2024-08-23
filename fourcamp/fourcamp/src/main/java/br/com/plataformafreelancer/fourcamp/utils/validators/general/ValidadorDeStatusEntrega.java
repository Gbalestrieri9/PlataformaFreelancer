package br.com.plataformafreelancer.fourcamp.utils.validators.general;

import br.com.plataformafreelancer.fourcamp.enuns.StatusValidacaoEntrega;
import br.com.plataformafreelancer.fourcamp.exceptions.StatusDaPropostaInvalidoException;
import br.com.plataformafreelancer.fourcamp.utils.ConstantesPtBr;

import java.util.Arrays;
import java.util.List;

public class ValidadorDeStatusEntrega {

    private ValidadorDeStatusEntrega() {
    }

    public static StatusValidacaoEntrega validarStatus(String validacao) {
        StatusValidacaoEntrega statusValidacaoEntregaValidado
                = StatusValidacaoEntrega.valueOf(validacao);

        List<StatusValidacaoEntrega> listaDeStatusValidacaoEntrega =
                Arrays.asList(
                        StatusValidacaoEntrega.ACEITA,
                        StatusValidacaoEntrega.RECUSADA
                );

        if (!listaDeStatusValidacaoEntrega.contains(statusValidacaoEntregaValidado)) {
            throw new StatusDaPropostaInvalidoException(ConstantesPtBr.STATUS_VALIDACAO_INVALIDO);
        } else {
            return statusValidacaoEntregaValidado;
        }
    }
}
