package br.com.plataformafreelancer.fourcamp.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseSaldoCarteiraDBDTO {
    private BigDecimal visualizarSaldo;
}
