package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.FreelancerJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.*;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEnderecoDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseProjetoCompatibilidadeDto;
import br.com.plataformafreelancer.fourcamp.enuns.*;
import br.com.plataformafreelancer.fourcamp.exceptions.NaoEncontradoException;
import br.com.plataformafreelancer.fourcamp.model.*;
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
public class FreelancerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FreelancerService.class);

    @Autowired
    private SenhaUtil senhaUtil;

    @Autowired
    private FreelancerJdbcTemplateDaoImpl freelancerJdbcTemplateDaoImpl;

    public void salvarDadosCadastrais(RequestFreelancerDto request) {
        LoggerUtils.logRequestStart(LOGGER, "salvarDadosCadastrais", request);

        ValidadorDeEmail.validarEmail(request.getEmail());
        senhaUtil.validarSenha(request.getSenha());
        ValidadorDeNomes.validarNome(request.getNome());
        ValidadorDeCpf.validarCpf(request.getCpf());
        DatasUtil.validarDataNascimento(request.getDataNascimento());
        ValidadorDeTelefones.validarNumeroTelefone(request.getTelefone());

        ResponseEnderecoDto responseEnderecoDto = ValidadorDeCep.buscaEnderecoPor(request.getCep());

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .senha(request.getSenha())
                .tipoUsuario(TipoUsuario.FREELANCER)
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


        Freelancer freelancer = Freelancer.builder()
                .usuario(usuario)
                .nome(request.getNome())
                .cpf(request.getCpf())
                .dataNascimento(DatasUtil.converterParaLocalDate(request.getDataNascimento()))
                .telefone(request.getTelefone())
                .endereco(endereco)
                .descricao(request.getDescricao())
                .disponibilidade(request.getDisponibilidade())
                .dataCriacao(DatasUtil.coletarDataHoraAtual())
                .statusFreelancer(StatusFreelancer.ATIVO)
                .habilidades(request.getHabilidades())
                .build();

        freelancerJdbcTemplateDaoImpl.salvarDadosCadastrais(freelancer);
        LoggerUtils.logElapsedTime(LOGGER, "salvarDadosCadastrais", System.currentTimeMillis());
    }

    public void salvarProposta(RequestPropostaDto requestPropostaDto, JwtDto jwtDto) {
        LoggerUtils.logRequestStart(LOGGER, "salvarProposta", requestPropostaDto);

        int idFreelancerValidado = ValidadorDeProposta.validarIdFreelancer(requestPropostaDto.getFreelancerId());
        int idProjetoValidado = ValidadorDeProposta.validarIdFreelancer(requestPropostaDto.getProjetoId());
        double valorFreelancerValidado = ValidadorDeProposta.validarValor(requestPropostaDto.getValor());

        // Adiciona taxa de administração ao valor final
        double valorFinal = taxarProposta(valorFreelancerValidado);

        LocalDate dataInicioValidada = ValidadorDeProposta.validarDataInicio(requestPropostaDto.getDataInicio());
        LocalDate dataFimValidada = ValidadorDeProposta.validarDataFim(requestPropostaDto.getDataFim());

        // Validar consistencia de datas
        ValidadorDeProposta.validarConsistenciaDatasInicioFim(dataInicioValidada, dataFimValidada);

        Proposta proposta = Proposta.builder()
                .freelancerId(idFreelancerValidado)
                .projetoId(idProjetoValidado)
                .valorFreelancer(valorFreelancerValidado)
                .valorFinal(valorFinal)
                .dataInicio(dataInicioValidada)
                .dataFim(dataFimValidada)
                .observacao(requestPropostaDto.getObservacao())
                .statusProposta(StatusProposta.PENDENTE)
                .dataCriacao(DatasUtil.coletarDataHoraAtual())
                .build();

        freelancerJdbcTemplateDaoImpl.salvarProposta(proposta);
        LoggerUtils.logElapsedTime(LOGGER, "salvarProposta", System.currentTimeMillis());
    }

    public void avaliarEmpresa(RequestAvaliacaoDto request, JwtDto jwtDto) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarEmpresa", request);

        Avaliacao avaliacao = Avaliacao.builder()
                .empresaId(request.getEmpresaId())
                .freelancerId(request.getFreelancerId())
                .projetoId(request.getProjetoId())
                .comentario(request.getComentario())
                .avaliado(TipoUsuario.EMPRESA)
                .nota(request.getNota())
                .dataAvaliacao(DatasUtil.coletarDataHoraAtual())
                .build();

        freelancerJdbcTemplateDaoImpl.avaliarEmpresa(avaliacao);
        LoggerUtils.logElapsedTime(LOGGER, "avaliarEmpresa", System.currentTimeMillis());
    }

    public List<ResponseEmpresaDto> listarEmpresa(JwtDto jwtDto) {
        List<ResponseEmpresaDto> lista = freelancerJdbcTemplateDaoImpl.listarEmpresas();
        if (lista == null || lista.isEmpty()) {
            throw new NaoEncontradoException(ErrorCode.LISTA_VAZIA.getCustomMessage());
        }
        return lista;
    }

    public List<Projeto> listarTodosProjetos(JwtDto jwtDto) {
        List<Projeto> lista = freelancerJdbcTemplateDaoImpl.listarTodosProjetos();
        if (lista == null || lista.isEmpty()) {
            throw new NaoEncontradoException(ErrorCode.LISTA_VAZIA.getCustomMessage());
        }
        return lista;
    }

    public ResponseEmpresaCompletaDto obterDetalhesEmpresa(RequestIdDto requestIdDto) {
        ResponseEmpresaCompletaDto empresa = null;

        if(!ValidadorDeNumerosPositivos.validarNumero(requestIdDto.getId())){
            empresa = freelancerJdbcTemplateDaoImpl.obterDetalhesEmpresa(requestIdDto.getId());

            if (empresa == null) {
                throw new NaoEncontradoException(ErrorCode.OBJETO_VAZIO.getCustomMessage());
            }
        }

        return empresa;
    }

    public List<ResponseProjetoCompatibilidadeDto> listaProjetosCompativeis(JwtDto jwtDto) {
        List<ResponseProjetoCompatibilidadeDto> lista = freelancerJdbcTemplateDaoImpl.buscarProjetosCompativeis(jwtDto.getId());
        if (lista == null || lista.isEmpty()) {
            throw new NaoEncontradoException(ErrorCode.LISTA_VAZIA.getCustomMessage());
        }
        return lista;
    }

    public void atualizarDadosFreelancer(RequestAtualizarFreelancerDto freelancer) {
        ValidadorDeTelefones.validarNumeroTelefone(freelancer.getTelefone());

        ResponseEnderecoDto endereco = ValidadorDeCep.buscaEnderecoPor(freelancer.getCep());

        freelancer.setBairro(endereco.getBairro());
        freelancer.setCidade(endereco.getLocalidade());
        freelancer.setEstado(endereco.getUf());
        freelancer.setLogradouro(endereco.getLogradouro());

        freelancerJdbcTemplateDaoImpl.atualizarDadosFreelancer(freelancer);
    }

    private double taxarProposta(double proposta) {
        return proposta + (proposta * TaxasProposta.TAXA_PADRAO.getValor());
    }

    public void registrarEntregaProjeto(EntregarProjetoRequestDto entregarProjetoRequestDto, JwtDto jwtDto) {
        int idDoProjetoValidado
                = Integer.parseInt(entregarProjetoRequestDto.getIdProjeto());
        int idDoFreelancerValidado
                = jwtDto.getId();
        RegistarEntregaDto registrarEntregaDto = RegistarEntregaDto
                .builder()
                .idFreelancer(idDoFreelancerValidado)
                .idProjeto(idDoProjetoValidado)
                .observacao(entregarProjetoRequestDto.getObservacao())
                .dataEntrega(DatasUtil.coletarDataAtual())
                .build();

        freelancerJdbcTemplateDaoImpl.registrarEntregaProjeto(registrarEntregaDto);
    }
}
