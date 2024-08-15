package br.com.plataformafreelancer.fourcamp.dao;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAnalisarPropostaDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAtualizarProjetoDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseFreelancerCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponsePropostaDto;
import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Empresa;
import br.com.plataformafreelancer.fourcamp.model.Projeto;

import java.util.List;

public interface IEmpresaJdbcTemplateDao {
    void salvarDadosCadastrais(Empresa empresa);

    void salvarDadosProjeto(Projeto projeto);

    void analisarProposta(RequestAnalisarPropostaDto request);

    void avaliarFreelancer(Avaliacao avaliacao);

    List<ResponseFreelancerDto> listarFreelacer();

    ResponseFreelancerCompletaDto obterDetalhesFreelancer(Integer freelancerId);

    List<ResponsePropostaDto> listarPropostasPorProjeto(Integer projetoId);

    void atualizarProjeto(RequestAtualizarProjetoDto request);

    void excluirProjetoSeNaoAssociado(Integer idProjeto);
}
