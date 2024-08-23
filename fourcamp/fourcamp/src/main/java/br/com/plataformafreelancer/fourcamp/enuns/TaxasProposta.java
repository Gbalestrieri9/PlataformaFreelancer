package br.com.plataformafreelancer.fourcamp.enuns;

public enum TaxasProposta {
    TAXA_PADRAO("Taxa administrativa padrão", 0.1);

    private final String descricao;
    private final double valor;

    TaxasProposta(String descricao, double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }
}
