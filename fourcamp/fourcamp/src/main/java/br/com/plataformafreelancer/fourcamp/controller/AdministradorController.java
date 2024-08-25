package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.JwtDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequesAprovarProjetoDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAdministradorDto;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.AdministradorService;
import br.com.plataformafreelancer.fourcamp.utils.ConstantesPtBr;
import br.com.plataformafreelancer.fourcamp.utils.JwtUtil;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdministradorController.class);
    @Autowired
    AdministradorService administradorService;

    @PostMapping("/v1/cadastrar-administrador")
    public ResponseEntity<?> cadastrarAdministrador(@RequestHeader("Authorization") String token, @RequestBody RequestAdministradorDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarAdministrador", request);
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        administradorService.salvarAdministrador(request, jwtDto);
        ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Administrador cadastrado com sucesso!").build());
        LoggerUtils.logElapsedTime(LOGGER, "cadastrarAdministrador", startTime);
        return response;
    }

    @PostMapping("/v1/aprovar-projeto")
    public ResponseEntity<?> aprovarProjeto(@RequestHeader("Authorization") String token, @RequestBody RequesAprovarProjetoDto requestAprovarEntregaProjetoDto) {
        LoggerUtils.logRequestStart(LOGGER, "aprovarProjeto", requestAprovarEntregaProjetoDto);
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        administradorService.aprovarProjeto(requestAprovarEntregaProjetoDto, jwtDto);

        ResponseEntity<StandardResponse> response = ResponseEntity.ok(
                StandardResponse.builder().message(ConstantesPtBr.SUCESSO_PROJETO_APROVADO).build());
        LoggerUtils.logElapsedTime(LOGGER, "aprovarProjeto", startTime);
        return response;
    }

    @GetMapping("/v1/projetos-pendentes")
    public ResponseEntity<?> listaProjetosPendente(@RequestHeader("Authorization") String token) {
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        List<Projeto> lista = administradorService.listarProjetoPendente(jwtDto);
        LoggerUtils.logElapsedTime(LOGGER, "listaProjetosPendente", startTime);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
