package br.com.plataformafreelancer.fourcamp.model;

import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Proposta {
    private Integer propostaId;
    private Integer freelancerId;
    private Integer projetoId;
    private double valor;
    private String dataCriacao;
    private StatusProposta statusProposta;
    private String observacao;
}

