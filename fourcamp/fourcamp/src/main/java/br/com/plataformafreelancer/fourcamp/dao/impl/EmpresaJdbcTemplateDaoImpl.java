package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.IEmpresaJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.FreelancerCompletaDtoRowMapper;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.FreelancerDtoRowMapper;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.PropostaRowMapper;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAtualizarEmpresaDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAtualizarProjetoDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.SalvarAnalisePropostaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseFreelancerCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponsePropostaDto;
import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Empresa;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.ValidarEntregaProjeto;
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

@Service
public class EmpresaJdbcTemplateDaoImpl implements IEmpresaJdbcTemplateDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaJdbcTemplateDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void salvarDadosCadastrais(Empresa empresa) {
        LoggerUtils.logRequestStart(LOGGER, "salvarDadosCadastrais", empresa);
        String sql = "CALL cadastrar_empresa(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                empresa.getUsuario().getEmail(),
                empresa.getUsuario().getSenha(),
                empresa.getUsuario().getTipoUsuario().toString(),
                empresa.getCnpj(),
                empresa.getNome(),
                empresa.getTelefone(),
                empresa.getEndereco().getLogradouro(),
                empresa.getEndereco().getNumero(),
                empresa.getEndereco().getComplemento(),
                empresa.getEndereco().getBairro(),
                empresa.getEndereco().getCidade(),
                empresa.getEndereco().getCep(),
                empresa.getEndereco().getEstado(),
                empresa.getEndereco().getPais(),
                empresa.getNomeEmpresa(),
                empresa.getRamoAtuacao(),
                empresa.getSite()
        );
        LoggerUtils.logElapsedTime(LOGGER, "salvarDadosCadastrais", System.currentTimeMillis());
    }

    @Override
    public void salvarDadosProjeto(Projeto projeto) {
        LoggerUtils.logRequestStart(LOGGER, "salvarDadosProjeto", projeto);
        String sql = "CALL cadastrarProjeto(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                projeto.getTitulo(),
                projeto.getDescricao(),
                projeto.getOrcamento(),
                projeto.getPrazo(),
                projeto.getStatusValidacaoEntrega().toString(),
                projeto.getDataCriacao(),
                projeto.getEmpresaId(),
                projeto.getHabilidades().toArray(new String[0])
        );
        LoggerUtils.logElapsedTime(LOGGER, "salvarDadosProjeto", System.currentTimeMillis());
    }

    @Override
    public void analisarProposta(SalvarAnalisePropostaDto salvarAnalisePropostaDto) {
        LoggerUtils.logRequestStart(LOGGER, "analisarProposta", salvarAnalisePropostaDto);
        String sql = "CALL public.atualizar_status_proposta(?, ?, ?, ?, ?)";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) preparedStatement -> {
            preparedStatement.setString(1, salvarAnalisePropostaDto.getEmailEmpresa());
            preparedStatement.setInt(2, salvarAnalisePropostaDto.getIdProposta());
            preparedStatement.setString(3, String.valueOf(salvarAnalisePropostaDto.getStatusProposta()));
            preparedStatement.setString(4, salvarAnalisePropostaDto.getDescricaoTransacao());
            preparedStatement.setObject(5, salvarAnalisePropostaDto.getDataAtual());
            preparedStatement.execute();
            return null;
        });
        LoggerUtils.logElapsedTime(LOGGER, "analisarProposta", System.currentTimeMillis());
    }

    @Override
    public void avaliarFreelancer(Avaliacao avaliacao) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarFreelancer", avaliacao);
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
        LoggerUtils.logElapsedTime(LOGGER, "avaliarFreelancer", System.currentTimeMillis());
    }

    @Override
    public List<ResponseFreelancerDto> listarFreelacer() {
        String sql = "SELECT * FROM public.listar_freelancers()";
        return jdbcTemplate.query(sql, new FreelancerDtoRowMapper());
    }

    @Override
    public ResponseFreelancerCompletaDto obterDetalhesFreelancer(Integer freelancerId) {
        String sql = "SELECT * FROM public.obter_detalhes_freelancer(?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{freelancerId}, new FreelancerCompletaDtoRowMapper());
    }

    @Override
    public List<ResponsePropostaDto> listarPropostasPorProjeto(Integer projetoId) {
        String sql = "SELECT * FROM listar_propostas_por_projeto(?)";

        return jdbcTemplate.query(sql, new Object[]{projetoId}, new PropostaRowMapper());
    }

    public void atualizarDadosEmpresa(RequestAtualizarEmpresaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "atualizarDadosEmpresa", request);
        String sql = "CALL atualizar_empresa(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                request.getIdEmpresa(),
                request.getNome(),
                request.getTelefone(),
                request.getRamoAtuacao(),
                request.getSite()
        );
        LoggerUtils.logElapsedTime(LOGGER, "atualizarDadosEmpresa", System.currentTimeMillis());
    }

    @Override
    public void atualizarProjeto(RequestAtualizarProjetoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "atualizarProjeto", request);
        String sql = "CALL atualizar_projeto(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                request.getIdProjeto(),
                request.getTitulo(),
                request.getDescricao(),
                request.getOrcamento(),
                request.getPrazo(),
                request.getHabilidades().toArray(new String[0])
        );
        LoggerUtils.logElapsedTime(LOGGER, "atualizarProjeto", System.currentTimeMillis());
    }

    @Override
    public void excluirProjetoSeNaoAssociado(Integer idProjeto) {
        LoggerUtils.logRequestStart(LOGGER, "excluirProjetoSeNaoAssociado", idProjeto);
        String sql = "CALL excluir_projeto_se_nao_associado(?)";
        jdbcTemplate.update(sql, idProjeto);
        LoggerUtils.logElapsedTime(LOGGER, "excluirProjetoSeNaoAssociado", System.currentTimeMillis());
    }

    @Override
    public void validarEntregaDoProjeto(ValidarEntregaProjeto validarEntregaProjeto, LocalDate dataAtual) {
        LoggerUtils.logRequestStart(LOGGER, "excluirProjetoSeNaoAssociado", validarEntregaProjeto);
        String sql = "CALL public.salvar_validacao_entrega_projeto(?, ?, ?, ?, ?)";
        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) preparedStatement -> {
            preparedStatement.setInt(1, validarEntregaProjeto.getIdEmpresa());
            preparedStatement.setInt(2, validarEntregaProjeto.getIdProjeto());
            preparedStatement.setString(3, String.valueOf(validarEntregaProjeto.getStatusValidacaoEntrega()));
            preparedStatement.setString(4, validarEntregaProjeto.getObservacao());
            preparedStatement.setObject(5, dataAtual, Types.DATE);
            preparedStatement.execute();
            return null;
        });
        LoggerUtils.logElapsedTime(LOGGER, "excluirProjetoSeNaoAssociado", System.currentTimeMillis());
    }
}
