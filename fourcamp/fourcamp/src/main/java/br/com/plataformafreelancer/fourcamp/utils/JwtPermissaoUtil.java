package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.JwtDto;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;

import java.util.List;

public class JwtPermissaoUtil {

    public boolean VerificaTipoUsuario (JwtDto jwtDto ,List<TipoUsuario> tipoPermitidos){
        return tipoPermitidos.contains(jwtDto.getTipoUsuario());
    }

}
