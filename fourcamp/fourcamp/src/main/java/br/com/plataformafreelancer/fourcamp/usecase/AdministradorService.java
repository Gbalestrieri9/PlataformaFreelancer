package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.AdministradorJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequesAprovarProjetoDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAdministradorDto;
import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.enuns.StatusAdministrador;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.exceptions.IdInvalidoException;
import br.com.plataformafreelancer.fourcamp.exceptions.NaoEncontradoException;
import br.com.plataformafreelancer.fourcamp.model.Administrador;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.Usuario;
import br.com.plataformafreelancer.fourcamp.utils.ConstantesPtBr;
import br.com.plataformafreelancer.fourcamp.utils.DatasUtil;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
import br.com.plataformafreelancer.fourcamp.utils.SenhaUtil;
import br.com.plataformafreelancer.fourcamp.utils.validators.general.ValidadorDeCpf;
import br.com.plataformafreelancer.fourcamp.utils.validators.general.ValidadorDeEmail;
import br.com.plataformafreelancer.fourcamp.utils.validators.general.ValidadorDeNomes;
import br.com.plataformafreelancer.fourcamp.utils.validators.general.ValidadorDeNumerosPositivos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdministradorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministradorService.class);

    @Autowired
    private DatasUtil datasUtil;

    @Autowired
    private SenhaUtil senhaUtil;

    @Autowired
    private AdministradorJdbcTemplateDaoImpl administradorJdbcTemplateDao;

    public void salvarAdministrador(RequestAdministradorDto requestAdministradorDto) {
        String cpfValidado;

        LoggerUtils.logRequestStart(LOGGER, "salvarAdministrador", requestAdministradorDto);

        ValidadorDeEmail.validarEmail(requestAdministradorDto.getEmail());
        cpfValidado = ValidadorDeCpf.validarCpf(requestAdministradorDto.getCpf());
        senhaUtil.validarSenha(requestAdministradorDto.getSenha());
        ValidadorDeNomes.validarNome(requestAdministradorDto.getNome());

        Usuario usuario = Usuario.builder()
                .email(requestAdministradorDto.getEmail())
                .senha(requestAdministradorDto.getSenha())
                .tipoUsuario(TipoUsuario.ADMINISTRADOR)
                .build();

        Administrador administrador = Administrador
                .builder()
                .usuario(usuario)
                .nome(requestAdministradorDto.getNome())
                .cpf(cpfValidado)
                .dataCriacao(DatasUtil.coletarDataHoraAtual())
                .statusAdministrador(StatusAdministrador.ATIVO)
                .build();

        administradorJdbcTemplateDao.salvarAdministrador(administrador);
        LoggerUtils.logElapsedTime(LOGGER, "salvarAdministrador", System.currentTimeMillis());
    }

    public void aprovarProjeto(RequesAprovarProjetoDto requestAprovarEntregaProjetoDto) {
        int idValidado;
        LocalDate dataAtual = DatasUtil.coletarDataAtual();

        if (!ValidadorDeNumerosPositivos.validarNumero(requestAprovarEntregaProjetoDto.getIdProjeto())) {
            throw new IdInvalidoException(ConstantesPtBr.ID_INVALIDO);
        } else {
            idValidado = Integer.parseInt(requestAprovarEntregaProjetoDto.getIdProjeto());
        }

        administradorJdbcTemplateDao.aprovarProjeto(idValidado, dataAtual);
    }

    public List<Projeto> listarProjetoPendente() {
        List<Projeto> lista = administradorJdbcTemplateDao.listarProjetoPendente();
        if (lista == null || lista.isEmpty()) {
            throw new NaoEncontradoException(ErrorCode.LISTA_VAZIA.getCustomMessage());
        }
        return lista;
    }
}
