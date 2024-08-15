package br.com.plataformafreelancer.fourcamp.dtos.responseDtos;

import br.com.plataformafreelancer.fourcamp.enuns.TipoMovimentacao;

import java.math.BigDecimal;
import java.util.Date;

public class MovimentacaoResponseDBDto {
    private TipoMovimentacao tipoMovimentacao;
    private BigDecimal valorMovimentacao;
    private Date dataMovimentacao;
}
