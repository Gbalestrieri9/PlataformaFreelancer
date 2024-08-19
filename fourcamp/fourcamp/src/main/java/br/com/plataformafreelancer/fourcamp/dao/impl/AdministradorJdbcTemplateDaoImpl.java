package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.IAdministradorJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.model.Administrador;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdministradorJdbcTemplateDaoImpl implements IAdministradorJdbcTemplateDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministradorJdbcTemplateDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Optional<Administrador> findById(Integer idAdministrador) {
        return Optional.empty();
    }

    @Override
    public void salvarAdministrador(Administrador administrador) {
        LoggerUtils.logRequestStart(LOGGER, "salvarAdministrador", administrador);
        String sql = "CALL cadastrar_administrador(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                administrador.getUsuario().getEmail(),
                administrador.getUsuario().getSenha(),
                administrador.getUsuario().getTipoUsuario().toString(),
                administrador.getNome(),
                administrador.getCpf(),
                administrador.getDataCriacao(),
                administrador.getStatusAdministrador().toString()
        );

        LoggerUtils.logElapsedTime(LOGGER, "salvaAdministrador", System.currentTimeMillis());
    }
}