package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.AdministradorJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAdministradorDto;
import br.com.plataformafreelancer.fourcamp.enuns.StatusAdministrador;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.model.Administrador;
import br.com.plataformafreelancer.fourcamp.model.Usuario;
import br.com.plataformafreelancer.fourcamp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministradorService.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private DataService dataService;
    @Autowired
    private CpfService cpfService;

    @Autowired
    private SenhaService senhaService;

    @Autowired
    private AdministradorJdbcTemplateDaoImpl administradorJdbcTemplateDao;

    public void salvarAdministrador(RequestAdministradorDto requestAdministradorDto) {
        LoggerUtils.logRequestStart(LOGGER, "salvarAdministrador", requestAdministradorDto);

        emailService.validarEmail(requestAdministradorDto.getEmail());
        cpfService.validarCpf(requestAdministradorDto.getCpf());
        senhaService.validarSenha(requestAdministradorDto.getSenha());
        NomeService.validarNome(requestAdministradorDto.getNome());

        Usuario usuario = Usuario.builder()
                .email(requestAdministradorDto.getEmail())
                .senha(requestAdministradorDto.getSenha())
                .tipoUsuario(TipoUsuario.ADMINISTRADOR)
                .build();

        Administrador administrador = Administrador
                .builder()
                .usuario(usuario)
                .nome(requestAdministradorDto.getNome())
                .cpf(requestAdministradorDto.getCpf())
                .dataCriacao(DataService.coletarDataHoraAtual())
                .statusAdministrador(StatusAdministrador.ATIVO)
                .build();

        administradorJdbcTemplateDao.salvarAdministrador(administrador);
        LoggerUtils.logElapsedTime(LOGGER, "salvarAdministrador", System.currentTimeMillis());
    }
}
