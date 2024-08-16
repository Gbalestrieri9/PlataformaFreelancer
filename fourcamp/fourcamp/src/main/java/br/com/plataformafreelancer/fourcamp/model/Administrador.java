package br.com.plataformafreelancer.fourcamp.model;

import br.com.plataformafreelancer.fourcamp.enuns.StatusAdministrador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Administrador {
    private Integer idAdministrador;
    private Usuario usuario;
    private String nome;
    private String cpf;
    private String dataCriacao;
    private StatusAdministrador statusAdministrador;

}