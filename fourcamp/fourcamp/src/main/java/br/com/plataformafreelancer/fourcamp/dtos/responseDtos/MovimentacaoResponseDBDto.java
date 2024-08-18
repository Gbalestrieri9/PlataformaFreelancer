package br.com.plataformafreelancer.fourcamp.dtos.responseDtos;

import br.com.plataformafreelancer.fourcamp.enuns.TipoMovimentacao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovimentacaoResponseDBDto {
    private TipoMovimentacao tipoMovimentacao;
    private BigDecimal valorMovimentacao;
    private Date dataMovimentacao;
    private String descricaoMovimentacao;
}
