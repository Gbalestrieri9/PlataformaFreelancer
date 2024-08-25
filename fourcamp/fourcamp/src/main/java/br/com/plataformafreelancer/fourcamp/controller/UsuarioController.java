package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestLoginDto;
import br.com.plataformafreelancer.fourcamp.usecase.UsuarioService;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreelancerController.class);

    @Autowired
    UsuarioService usuarioService;

    @Operation(summary = "Login de qualquer usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/login-usuario")
    public String login(@RequestBody RequestLoginDto request) {
        LoggerUtils.logRequestStart(LOGGER, "login", request);
        long startTime = System.currentTimeMillis();

        String token = usuarioService.login(request.getEmail(), request.getSenha());

        LoggerUtils.logElapsedTime(LOGGER, "login", startTime);
        return token;
    }
}
