package br.com.plataformafreelancer.fourcamp.dtos.requestDtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestValidarEntregaProjetoDto {
    private String idEmpresa;
    private String idProjeto;
    @Schema(description = "Registra aprovação ou recusa da entrega. Parametros aceitos: 'ACEITA' ou 'RECUSADA'")
    private String validacao;
    private String observacaoEntrega;
}
