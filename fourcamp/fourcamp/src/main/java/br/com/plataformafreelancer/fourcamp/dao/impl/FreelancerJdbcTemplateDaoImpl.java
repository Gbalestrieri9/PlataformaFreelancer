package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.IFreelancerJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.EmpresaCompletaDtoRowMapper;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.EmpresaDtoRowMapper;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.ProjetoCompatibilidadeDtoRowMapper;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.ProjetoDtoRowMapper;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RegistarEntregaDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAtualizarFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseProjetoCompatibilidadeDto;
import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Freelancer;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.Proposta;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;

@Service
public class FreelancerJdbcTemplateDaoImpl implements IFreelancerJdbcTemplateDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreelancerJdbcTemplateDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void salvarDadosCadastrais(Freelancer freelancer) {
        LoggerUtils.logRequestStart(LOGGER, "salvarDadosCadastrais", freelancer);
        String sql = "CALL cadastrar_freelancer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                freelancer.getUsuario().getEmail(),
                freelancer.getUsuario().getSenha(),
                freelancer.getUsuario().getTipoUsuario().toString(),
                freelancer.getEndereco().getLogradouro(),
                freelancer.getEndereco().getNumero(),
                freelancer.getEndereco().getComplemento(),
                freelancer.getEndereco().getBairro(),
                freelancer.getEndereco().getCidade(),
                freelancer.getEndereco().getCep(),
                freelancer.getEndereco().getEstado(),
                freelancer.getEndereco().getPais(),
                freelancer.getNome(),
                freelancer.getCpf(),
                freelancer.getDataNascimento().toString(),
                freelancer.getTelefone(),
                freelancer.getDescricao(),
                freelancer.getDisponibilidade(),
                freelancer.getDataCriacao(),
                freelancer.getStatusFreelancer().toString(),
                freelancer.getHabilidades().toArray(new String[0])
        );
        LoggerUtils.logElapsedTime(LOGGER, "salvarDadosCadastrais", System.currentTimeMillis());
    }

    @Override
    public void salvarProposta(Proposta proposta) {
        LoggerUtils.logRequestStart(LOGGER, "salvarProposta", proposta);
        String sql = "CALL criar_proposta(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) preparedStatement -> {
            preparedStatement.setInt(1, proposta.getFreelancerId());
            preparedStatement.setInt(2, proposta.getProjetoId());
            preparedStatement.setBigDecimal(3, BigDecimal.valueOf(proposta.getValorFinal()));
            preparedStatement.setBigDecimal(4, BigDecimal.valueOf(proposta.getValorFreelancer()));
            preparedStatement.setString(5, proposta.getDataCriacao());
            preparedStatement.setString(6, String.valueOf(proposta.getStatusProposta()));
            preparedStatement.setString(7, proposta.getObservacao());
            preparedStatement.setObject(8, proposta.getDataInicio(), Types.DATE);
            preparedStatement.setObject(9, proposta.getDataFim(), Types.DATE);
            preparedStatement.execute();
            return null;
        });
        LoggerUtils.logElapsedTime(LOGGER, "salvarProposta", System.currentTimeMillis());
    }

    @Override
    public void avaliarEmpresa(Avaliacao avaliacao) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarEmpresa", avaliacao);
        String sql = "CALL enviar_avaliacao(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                avaliacao.getEmpresaId(),
                avaliacao.getFreelancerId(),
                avaliacao.getProjetoId(),
                avaliacao.getAvaliado().toString(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getDataAvaliacao()
        );
        LoggerUtils.logElapsedTime(LOGGER, "avaliarEmpresa", System.currentTimeMillis());
    }

    public List<ResponseEmpresaDto> listarEmpresas() {
        String sql = "SELECT * FROM listar_empresas()";
        return jdbcTemplate.query(sql, new EmpresaDtoRowMapper());
    }

    @Override
    public List<Projeto> listarTodosProjetos() {
        String sql = "SELECT * FROM listar_todos_projetos()";
        return jdbcTemplate.query(sql, new ProjetoDtoRowMapper());
    }

    @Override
    public ResponseEmpresaCompletaDto obterDetalhesEmpresa(Integer empresaId) {
        String sql = "SELECT * FROM obter_detalhes_empresa(?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{empresaId}, new EmpresaCompletaDtoRowMapper());
    }

    @Override
    public List<ResponseProjetoCompatibilidadeDto> buscarProjetosCompativeis(int idFreelancer) {
        LoggerUtils.logRequestStart(LOGGER, "buscarProjetosCompativeis", idFreelancer);
        String sql = "SELECT * FROM buscar_projetos_compatíveis(?)";
        return jdbcTemplate.query(sql, new Object[]{idFreelancer}, new ProjetoCompatibilidadeDtoRowMapper());
    }

    @Override
    public void registrarEntregaProjeto(RegistarEntregaDto registrarEntregaDto) {
        LoggerUtils.logRequestStart(LOGGER, "registrarEntregaProjeto", registrarEntregaDto);

        String sql = "CALL public.registrar_entrega_projeto(?, ?, ?, ?)";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) preparedStatement -> {
            preparedStatement.setInt(1, registrarEntregaDto.getIdFreelancer());
            preparedStatement.setInt(2, registrarEntregaDto.getIdProjeto());
            preparedStatement.setString(3, registrarEntregaDto.getObservacao());
            preparedStatement.setObject(4, registrarEntregaDto.getDataEntrega(), Types.DATE);
            preparedStatement.execute();
            return null;
        });
    }

    public void atualizarDadosFreelancer(RequestAtualizarFreelancerDto request) {
        LoggerUtils.logRequestStart(LOGGER, "atualizarDadosFreelancer", request);
        String sql = "CALL atualizar_freelancer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                request.getIdFreelancer(),
                request.getTelefone(),
                request.getLogradouro(),
                request.getNumero(),
                request.getComplemento(),
                request.getBairro(),
                request.getCidade(),
                request.getCep(),
                request.getEstado(),
                request.getPais(),
                request.getDescricao(),
                request.getDisponibilidade(),
                request.getStatus(),
                request.getHabilidades().toArray(new String[0])
        );
        LoggerUtils.logElapsedTime(LOGGER, "atualizarDadosFreelancer", System.currentTimeMillis());
    }
}
