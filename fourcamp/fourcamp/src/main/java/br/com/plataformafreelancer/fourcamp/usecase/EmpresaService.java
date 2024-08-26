package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.EmpresaJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.*;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEnderecoDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseFreelancerCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponsePropostaDto;
import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import br.com.plataformafreelancer.fourcamp.enuns.StatusValidacaoEntrega;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.exceptions.IdInvalidoException;
import br.com.plataformafreelancer.fourcamp.exceptions.NaoEncontradoException;
import br.com.plataformafreelancer.fourcamp.exceptions.ValorInvalidoException;
import br.com.plataformafreelancer.fourcamp.model.*;
import br.com.plataformafreelancer.fourcamp.utils.ConstantesPtBr;
import br.com.plataformafreelancer.fourcamp.utils.DatasUtil;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
import br.com.plataformafreelancer.fourcamp.utils.SenhaUtil;
import br.com.plataformafreelancer.fourcamp.utils.validators.entities.ValidadorDeProposta;
import br.com.plataformafreelancer.fourcamp.utils.validators.general.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpresaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaService.class);

    @Autowired
    EmpresaJdbcTemplateDaoImpl empresaJdbcTemplateDao;

    public void salvarDadosCadastrais(RequestEmpresaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "salvarDadosCadastrais", request);

        ValidadorDeEmail.validarEmail(request.getEmail());
        SenhaUtil.validarSenha(request.getSenha());
        ResponseEnderecoDto responseEnderecoDto = ValidadorDeCep.buscaEnderecoPor(request.getCep());
        String cnpjValidado = ValidadorDeCnpj.validarCnpj(request.getCnpj());
        ValidadorDeTelefones.validarNumeroTelefone(request.getTelefone());

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .senha(request.getSenha())
                .tipoUsuario(TipoUsuario.EMPRESA)
                .build();

        Endereco endereco = Endereco.builder()
                .logradouro(responseEnderecoDto.getLogradouro())
                .numero(request.getNumero())
                .complemento(request.getComplemento())
                .bairro(responseEnderecoDto.getBairro())
                .cidade(responseEnderecoDto.getLocalidade())
                .cep(responseEnderecoDto.getCep())
                .estado(responseEnderecoDto.getUf())
                .pais(request.getPais())
                .build();

        Empresa empresa = Empresa.builder()
                .usuario(usuario)
                .cnpj(cnpjValidado)
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .endereco(endereco)
                .nomeEmpresa(request.getNomeEmpresa())
                .ramoAtuacao(request.getRamoAtuacao())
                .site(request.getSite())
                .build();

        empresaJdbcTemplateDao.salvarDadosCadastrais(empresa);
        LoggerUtils.logElapsedTime(LOGGER, "salvarDadosCadastrais", System.currentTimeMillis());
    }

    public void salvarDadosProjeto(RequestProjetoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "salvarDadosProjeto", request);

        Projeto projeto = Projeto.builder()
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .orcamento(request.getOrcamento())
                .prazo(request.getPrazo())
                .empresaId(request.getIdEmpresa())
                .habilidades(request.getHabilidades())
                .statusValidacaoEntrega(StatusValidacaoEntrega.ATIVO)
                .dataCriacao(DatasUtil.coletarDataHoraAtual())
                .build();

        empresaJdbcTemplateDao.salvarDadosProjeto(projeto);
        LoggerUtils.logElapsedTime(LOGGER, "salvarDadosProjeto", System.currentTimeMillis());
    }

    public void analisarProposta(RequestAnalisarPropostaDto requestAnalisarPropostaDto, JwtDto jwtDto) {
        LoggerUtils.logRequestStart(LOGGER, "analisarProposta", requestAnalisarPropostaDto);

        String emailEmpresa = jwtDto.getEmail();

        int idPropostaValidado =
                ValidadorDeProposta.validarIdProposta(requestAnalisarPropostaDto.getIdProposta());

        StatusProposta statusPropostaValidado =
                ValidadorDeProposta.validarStatusProposta(requestAnalisarPropostaDto.getStatusProposta());

        String descricaoDaTransacao = ConstantesPtBr.CARTEIRA_REGISTRAR_PAGAMENTO + idPropostaValidado;

        LocalDate dataAtual = DatasUtil.coletarDataAtual();

        SalvarAnalisePropostaDto salvarAnalisePropostaDto =
                SalvarAnalisePropostaDto
                        .builder()
                        .emailEmpresa(emailEmpresa)
                        .idProposta(idPropostaValidado)
                        .statusProposta(statusPropostaValidado)
                        .descricaoTransacao(descricaoDaTransacao)
                        .dataAtual(dataAtual)
                        .build();

        empresaJdbcTemplateDao.analisarProposta(salvarAnalisePropostaDto);
        LoggerUtils.logElapsedTime(LOGGER, "analisarProposta", System.currentTimeMillis());
    }

    public void avaliarFreelancer(RequestAvaliacaoDto request, JwtDto jwtDto) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarFreelancer", request);

        Avaliacao avaliacao = Avaliacao.builder()
                .empresaId(jwtDto.getId())
                .freelancerId(request.getFreelancerId())
                .projetoId(request.getProjetoId())
                .comentario(request.getComentario())
                .avaliado(TipoUsuario.FREELANCER)
                .nota(request.getNota())
                .dataAvaliacao(DatasUtil.coletarDataHoraAtual())
                .build();

        empresaJdbcTemplateDao.avaliarFreelancer(avaliacao);
        LoggerUtils.logElapsedTime(LOGGER, "avaliarFreelancer", System.currentTimeMillis());
    }

    public List<ResponseFreelancerDto> listarFreelancer() {
        List<ResponseFreelancerDto> lista = empresaJdbcTemplateDao.listarFreelacer();
        if (lista == null || lista.isEmpty()) {
            throw new NaoEncontradoException(ErrorCode.LISTA_VAZIA.getCustomMessage());
        }

        return lista;
    }

    public ResponseFreelancerCompletaDto obterDetalhesFreelancer(RequestIdDto requestIdDto) {
        ResponseFreelancerCompletaDto freelancer = null;

        if(!ValidadorDeNumerosPositivos.validarNumero(requestIdDto.getId())){
            freelancer = empresaJdbcTemplateDao.obterDetalhesFreelancer(requestIdDto.getId());
        }

        if (freelancer == null) {
            throw new NaoEncontradoException(ErrorCode.OBJETO_VAZIO.getCustomMessage());
        } else {
            return freelancer;
        }
    }

    public List<ResponsePropostaDto> listarPropostasPorProjeto(RequestIdDto requestIdDto) {

        if (!ValidadorDeNumerosPositivos.validarNumero(requestIdDto.getId())) {
            throw new IdInvalidoException(ConstantesPtBr.ID_INVALIDO);
        }

        List<ResponsePropostaDto> propostas = empresaJdbcTemplateDao.listarPropostasPorProjeto(requestIdDto.getId());
        if (propostas == null || propostas.isEmpty()) {
            throw new NaoEncontradoException(ErrorCode.LISTA_VAZIA.getCustomMessage());
        }
        return propostas;
    }

    public void atualizarDadosEmpresa(RequestAtualizarEmpresaDto empresa) {
        ValidadorDeTelefones.validarNumeroTelefone(empresa.getTelefone());

        empresaJdbcTemplateDao.atualizarDadosEmpresa(empresa);
    }

    public void atualizarDadosProjeto(RequestAtualizarProjetoDto projeto) {
        empresaJdbcTemplateDao.atualizarProjeto(projeto);
    }

    public void excluirProjetoSeNaoAssociado(RequestIdDto requestIdDto) {
        if(!ValidadorDeNumerosPositivos.validarNumero(requestIdDto.getId())){
            empresaJdbcTemplateDao.excluirProjetoSeNaoAssociado(requestIdDto.getId());
        }
    }

    public void validarEntregaDoProjeto(RequestValidarEntregaProjetoDto requestValidarEntregaProjetoDto, JwtDto jwtDto) {
        LocalDate dataAtual = DatasUtil.coletarDataAtual();
        int idProjetoValidado;
        int idEmpresaValidado;

        if (!ValidadorDeNumerosPositivos.validarNumero(requestValidarEntregaProjetoDto.getIdProjeto())) {
            throw new ValorInvalidoException(ConstantesPtBr.ID_INVALIDO);
        } else {
            idProjetoValidado = Integer.parseInt(requestValidarEntregaProjetoDto.getIdProjeto());
        }

        if (!ValidadorDeNumerosPositivos.validarNumero(requestValidarEntregaProjetoDto.getIdEmpresa())) {
            throw new ValorInvalidoException(ConstantesPtBr.ID_INVALIDO);
        } else {
            idEmpresaValidado = jwtDto.getId();
        }

        StatusValidacaoEntrega statusValidacaoEntregaValidado =
                ValidadorDeStatusEntrega.validarStatus(requestValidarEntregaProjetoDto.getValidacao());

        ValidarEntregaProjeto validarEntregaProjeto = ValidarEntregaProjeto
                .builder()
                .idEmpresa(idEmpresaValidado)
                .idProjeto(idProjetoValidado)
                .statusValidacaoEntrega(statusValidacaoEntregaValidado)
                .observacao(requestValidarEntregaProjetoDto.getObservacaoEntrega())
                .dataValidacao(dataAtual)
                .build();

        empresaJdbcTemplateDao.validarEntregaDoProjeto(validarEntregaProjeto, dataAtual);
    }
}
