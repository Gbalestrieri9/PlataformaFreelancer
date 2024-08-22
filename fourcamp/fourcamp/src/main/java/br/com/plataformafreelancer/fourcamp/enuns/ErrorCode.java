package br.com.plataformafreelancer.fourcamp.enuns;

import lombok.Getter;

@Getter
public enum ErrorCode {
    AVALIACAO_EXISTE("Avaliação já existe", "Avaliação já existe"),
    EMPRESA_NAO_EXISTE("Empresa não existe", "Empresa não existe"),
    FREELANCER_NAO_EXISTE("Freelancer não existe", "Freelancer não existe"),
    PROJETO_NAO_EXISTE("Projeto não existe", "Projeto não existe"),
    FREELANCER_NAO_ASSOCIADO("Freelancer não está associado ao projeto", "Freelancer não está associado ao projeto"),
    EMPRESA_NAO_ASSOCIADA("Empresa não está associada ao projeto", "Empresa não está associada ao projeto"),
    NOTA_INVALIDA("Nota deve ser entre 1 e 5", "Nota deve ser entre 1 e 5"),
    EMAIL_JA_CADASTRADO("Email", "Email já está cadastrado"),
    CPF_JA_CADASTRADO("CPF", "CPF já está cadastrado"),
    CNPJ_JA_CADASTRADO("CNPJ", "CNPJ já está cadastrado"),
    ID_EMPRESA_NAO_EXISTE("ID de Empresa", "ID de Empresa não existe"),
    ID_PROJETO_NAO_EXISTE("ID de Projeto", "ID de Projeto não existe"),
    ID_FREELANCER_NAO_EXISTE("ID de Freelancer", "ID de Freelancer não existe"),
    PROPOSTA_JA_ACEITA("Proposta já foi aceita e não pode ser recusada", "Proposta já foi aceita e não pode ser recusada"),
    PROPOSTA_NAO_EXISTE("Proposta com id", "Proposta com id não existe"),
    PROJETO_JA_ASSOCIADO("Projeto já possui um freelancer associado", "Projeto já possui um freelancer associado"),
    CEP_INVALIDO("CEP inválido", "CEP inválido: "),
    CEP_NAO_ENCONTRADO("CEP não encontrado", "CEP não encontrado: "),
    CNPJ_INVALIDO("CNPJ inválido", "CNPJ inválido: "),
    CPF_INVALIDO("CPF inválido", "CPF inválido: "),
    EMAIL_INVALIDO("Email inválido", "Email inválido: "),
    NOME_INVALIDO("Nome inválido", "Nome inválido: "),
    SENHA_INVALIDA("Senha inválida", "Senha inválida: a senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula, um número e um caractere especial."),
    TELEFONE_INVALIDO("Número de telefone inválido", "Número de telefone inválido: "),
    LISTA_VAZIA("lista vazia", "Nenhum dado foi encontrado!"),
    OBJETO_VAZIO("objeto não encontrado", "Nenhum dado foi encontrado!"),
    PROJETO_NAO_EXCLUIDO("Projeto com id", "Projeto não pode ser excluído porque está associado a um freelancer."),
    DADO_INVALIDO("Dado invalido no Json", "Dado Inválido no Json"),
    OUTRO_ERRO("Outro erro", "Erro desconhecido"),
    VALOR_INVALIDO_VALOR_DEPOSITO("Valor inválido", "O valor informado para depósito é inválido: "),
    SAQUE_VALOR_INVÁLIDO("Valor do saque maior que o valor disponível", "Valor inválido. Saque não pode ser maior que o valor disponível."),
    ID_FREELANCER_FORMATO_INVALIDO("Id não é um número", "ID do freelancer inválido, por favor informar um ID válido."),
    ID_FREELANCER_ID_INVALIDO("ID do freelancer é um número negativo ou zero", "ID do freelancer inválido, por favor informar um ID válido."),
    VALOR_PROPOSTA_INVALIDO("Formato do valor inválido ou não é um número", "Valor da proposta inválido"),
    ID_PROPOSTA_ID_INVALIDO("ID da proposta é um número negativo ou zero", "ID da proposta inválido, por favor informar um ID válido."),
    ID_PROPOSTA_FORMATO_INVALIDO("Formato do ID da proposta inválido ou não é um número","Id da proposta inválido"),
    PROPOSTA_STATUS_INEXISTENTE("Status da proposta não existe", "Status da proposta inválido. Opções aceitas: ACEITA, RECUSADA" ),
    SALDO_INSUFICIENTE("Saldo insuficiente", "Saldo Insuficiente"),
    DATA_INVALIDA("Data inválida", "Data ou formato da data inválido."),
    PROJETO_AGUARDANDO_APROVACAO_EMPRESA("Projeto já foi entregue.", "Projeto já foi entregue."),
    PROJETO_AINDA_NAO_INICIADO("Sua proposta ainda não foi aceita.", "Sua proposta ainda não foi aceita."),
    PROJETO_AGUARDANDO_APROVACAO_ADM("Aguardando aprovação do Administrador.", "O projeto está aguardando a valicação do Administrador"),
    PROJETO_NAO_PERTENCE_A_EMPRESA("Empresa não está associada ao projeto", "Não foi possível atualizar este projeto, entre em contato com o administrador."),
    PROJETO_NAO_DISPONIVEL_PARA_ENCERRAMENTO("Projeto não disponível para encerramento.", "Projeto não disponível para encerramento.");


    private final String message;
    private final String customMessage;

    ErrorCode(String message, String customMessage) {
        this.message = message;
        this.customMessage = customMessage;
    }

    public static ErrorCode fromMessage(String message) {
        for (ErrorCode code : ErrorCode.values()) {
            if (message.contains(code.getMessage())) {
                return code;
            }
        }
        return OUTRO_ERRO;
    }
}
