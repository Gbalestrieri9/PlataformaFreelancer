package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.AdministradorJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAdministradorDto;
import br.com.plataformafreelancer.fourcamp.enuns.StatusAdministrador;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.model.Administrador;
import br.com.plataformafreelancer.fourcamp.model.Usuario;
import br.com.plataformafreelancer.fourcamp.utils.*;
import br.com.plataformafreelancer.fourcamp.utils.validators.general.ValidadorDeCpf;
import br.com.plataformafreelancer.fourcamp.utils.validators.general.ValidadorDeEmail;
import br.com.plataformafreelancer.fourcamp.utils.validators.general.ValidadorDeNomes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
