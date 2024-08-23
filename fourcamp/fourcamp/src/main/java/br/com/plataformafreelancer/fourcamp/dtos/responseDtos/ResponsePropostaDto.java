package br.com.plataformafreelancer.fourcamp.dtos.responseDtos;

import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePropostaDto {
    private Integer propostaId;
    private String freelancerNome;
    private String freelancerTelefone;
    private String freelancerEmail;
    private Integer projetoId;
    private BigDecimal valor;
    private String dataCriacao;
    private StatusProposta statusProposta;
    private String observacao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
