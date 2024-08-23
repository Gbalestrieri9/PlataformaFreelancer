package br.com.plataformafreelancer.fourcamp.model;

import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Proposta {
    private Integer propostaId;
    private Integer freelancerId;
    private Integer projetoId;
    private double valorFinal;
    private double valorFreelancer;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String dataCriacao;
    private StatusProposta statusProposta;
    private String observacao;
}

