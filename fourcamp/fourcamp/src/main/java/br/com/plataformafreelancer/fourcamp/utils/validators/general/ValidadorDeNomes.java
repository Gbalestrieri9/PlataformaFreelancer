package br.com.plataformafreelancer.fourcamp.utils.validators.general;

import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.exceptions.NomeInvalidoException;
import org.springframework.stereotype.Service;

@Service
public class ValidadorDeNomes {

    public static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty() || !nome.matches("^[A-Za-zÀ-ÿ ]+$")) {
            throw new NomeInvalidoException(ErrorCode.NOME_INVALIDO.getCustomMessage() + nome);
        }
    }
}

