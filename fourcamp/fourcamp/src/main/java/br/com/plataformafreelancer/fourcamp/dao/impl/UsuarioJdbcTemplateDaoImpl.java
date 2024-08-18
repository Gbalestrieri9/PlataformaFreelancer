package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.IUsuarioJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioJdbcTemplateDaoImpl implements IUsuarioJdbcTemplateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Usuario buscarClientePorEmail(String email) {
        String query = "SELECT * FROM usuario WHERE email = ?";
        @SuppressWarnings("deprecation")
        List<Usuario> freelancers = jdbcTemplate.query(query, new Object[]{email}, (rs, rowNum) -> {
            return new Usuario(
                    rs.getString("email"),
                    rs.getString("senha"),
                    TipoUsuario.valueOf(rs.getString("tipousuario"))
            );
        });

        if (!freelancers.isEmpty()) {
            return freelancers.get(0);
        } else {
            return null;
        }
    }
}
