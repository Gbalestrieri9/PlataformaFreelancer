package br.com.plataformafreelancer.fourcamp.enuns;

public enum TipoMovimentacao {
    SAQUE ("Saque"),
    DEPOSITO ( "Depósito"),
    TRANSFERENCIA( "Transferencia");
    private final String descricao;

    TipoMovimentacao(String descricao){
        this.descricao = descricao;
    }
}
