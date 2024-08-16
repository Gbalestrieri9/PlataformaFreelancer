package br.com.plataformafreelancer.fourcamp.dtos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SacarValorRequestDTO {
    private String email;
    private String valor;
}
