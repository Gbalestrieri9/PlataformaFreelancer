package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.JwtDto;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtPermissaoUtil {

    public boolean VerificaTipoUsuario(JwtDto jwtDto, List<TipoUsuario> tipoPermitidos) {
        return tipoPermitidos.contains(jwtDto.getTipoUsuario());
    }

}
