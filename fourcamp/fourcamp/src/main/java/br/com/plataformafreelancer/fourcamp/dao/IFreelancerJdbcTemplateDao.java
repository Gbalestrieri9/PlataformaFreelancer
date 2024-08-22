package br.com.plataformafreelancer.fourcamp.dao;

import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RegistarEntregaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseProjetoCompatibilidadeDto;
import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Freelancer;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.Proposta;

import java.util.List;

public interface IFreelancerJdbcTemplateDao {

    void salvarDadosCadastrais(Freelancer freelancer);

    void salvarProposta(Proposta proposta);

    void avaliarEmpresa(Avaliacao avaliacao);

    List<ResponseEmpresaDto> listarEmpresas();

    List<Projeto> listarTodosProjetos();

    ResponseEmpresaCompletaDto obterDetalhesEmpresa(Integer empresaId);

    List<ResponseProjetoCompatibilidadeDto> buscarProjetosCompativeis(int idFreelancer);

    void registrarEntregaProjeto(RegistarEntregaDto registrarEntregaDto);
}
