package br.com.plataformafreelancer.fourcamp.dtos.requestDtos;

import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import lombok.Data;

@Data
public class JwtDto {
    private int id;
    private String email;
    private String senha;
    private TipoUsuario tipoUsuario;
}
