package br.com.plataformafreelancer.fourcamp.dtos.requestDtos;

import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalvarAnalisePropostaDto {
    String emailEmpresa;
    int idProposta;
    StatusProposta statusProposta;
    String descricaoTransacao;
    LocalDate dataAtual;
}
