package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.IUsuarioJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.UsuarioRowMapper;
import br.com.plataformafreelancer.fourcamp.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UsuarioJdbcTemplateDaoImpl implements IUsuarioJdbcTemplateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Usuario buscarClientePorEmail(String email) {
        String sql = "SELECT * FROM public.buscar_usuario_por_email(?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, new UsuarioRowMapper());
    }
}
