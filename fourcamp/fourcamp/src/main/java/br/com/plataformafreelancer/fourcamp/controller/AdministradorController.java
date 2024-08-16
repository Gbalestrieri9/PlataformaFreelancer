package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAdministradorDto;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.AdministradorService;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {
    @Autowired
    AdministradorService administradorService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdministradorController.class);

    @PostMapping("/v1/cadastrar-administrador")
    public ResponseEntity<?> cadastrarAdministrador(@RequestBody RequestAdministradorDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarAdministrador", request);
        long startTime = System.currentTimeMillis();

        administradorService.salvarAdministrador(request);
        ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Administrador cadastrado com sucesso!").build());
        LoggerUtils.logElapsedTime(LOGGER, "cadastrarAdministrador", startTime);
        return response;
    }
}
