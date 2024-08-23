package br.com.plataformafreelancer.fourcamp.dtos.responseDtos;

import br.com.plataformafreelancer.fourcamp.enuns.StatusAdministrador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAdministradorDto {
    private Integer idAdministrador;
    private String email;
    private String cpf;
    private String nome;
    private String dataCriacao;
    private StatusAdministrador statusAdministrador;
}