package br.com.plataformafreelancer.fourcamp.enuns;

public enum StatusProposta {
    PENDENTE("PENDENTE"),
    ACEITA("ACEITA"),
    RECUSADA("RECUSADA");

    private final String status;

    StatusProposta(String status){
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
