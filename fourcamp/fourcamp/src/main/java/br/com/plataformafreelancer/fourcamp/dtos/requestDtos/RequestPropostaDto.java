package br.com.plataformafreelancer.fourcamp.dtos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestPropostaDto {
    private String freelancerId;
    private String projetoId;
    private String valor;
    private String observacao;
}

