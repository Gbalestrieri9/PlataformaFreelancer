package br.com.plataformafreelancer.fourcamp.dao.impl.mapper;

import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.model.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRowMapper implements RowMapper<Usuario> {

    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Usuario.builder()
                .id(rs.getInt("id_usuario"))
                .email(rs.getString("email_usuario"))
                .senha(rs.getString("senha_usuario"))
                .tipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")))
                .build();
    }
}
