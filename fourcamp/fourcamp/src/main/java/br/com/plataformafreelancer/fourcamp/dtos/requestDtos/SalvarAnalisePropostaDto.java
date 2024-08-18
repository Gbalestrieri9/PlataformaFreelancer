package br.com.plataformafreelancer.fourcamp.dtos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalvarAnalisePropostaDto {
    String emailEmpresa;
    int idProposta;
    String status;
    String descricaoTransacao;
}
