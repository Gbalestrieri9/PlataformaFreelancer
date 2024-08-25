package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.IAdministradorJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.ProjetoDtoRowMapper;
import br.com.plataformafreelancer.fourcamp.model.Administrador;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
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

    @Override
    public void aprovarProjeto(int idValidado, LocalDate dataAtual) {
        LoggerUtils.logRequestStart(LOGGER, "aprovarProjeto", idValidado);
        String sql = "CALL public.aprovar_projeto(?, ?)";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) preparedStatement -> {
            preparedStatement.setInt(1, idValidado);
            preparedStatement.setObject(2, dataAtual, Types.DATE);
            preparedStatement.execute();
            return null;
        });

        LoggerUtils.logElapsedTime(LOGGER, "aprovarProjeto", System.currentTimeMillis());
    }

    @Override
    public List<Projeto> listarProjetoPendente() {
        String sql = "SELECT * FROM listar_projetos_pendentes()";
        return jdbcTemplate.query(sql, new ProjetoDtoRowMapper());
    }

}