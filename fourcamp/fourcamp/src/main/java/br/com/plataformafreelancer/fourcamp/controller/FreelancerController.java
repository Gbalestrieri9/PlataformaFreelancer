package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.*;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseProjetoCompatibilidadeDto;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.FreelancerService;
import br.com.plataformafreelancer.fourcamp.usecase.UsuarioService;
import br.com.plataformafreelancer.fourcamp.utils.ConstantesPtBr;
import br.com.plataformafreelancer.fourcamp.utils.JwtUtil;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/freelancer")
public class FreelancerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FreelancerController.class);
    @Autowired
    FreelancerService freelancerService;
    @Autowired
    UsuarioService usuarioService;

    @Operation(summary = "Cadastrar um novo freelancer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/cadastrar-freelancer")
    public ResponseEntity<?> cadastrarFreelancer(@RequestBody RequestFreelancerDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarFreelancer", request);
        long startTime = System.currentTimeMillis();

        freelancerService.salvarDadosCadastrais(request);
        ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Freelancer cadastrado com sucesso!").build());
        LoggerUtils.logElapsedTime(LOGGER, "cadastrarFreelancer", startTime);
        return response;
    }

    @Operation(summary = "Enviar uma proposta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proposta enviada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/enviar-proposta")
    public ResponseEntity<?> enviarProposta(@RequestHeader("Authorization") String token, @RequestBody RequestPropostaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "enviarProposta", request);
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        freelancerService.salvarProposta(request, jwtDto);

        ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Proposta enviada!").build());
        LoggerUtils.logElapsedTime(LOGGER, "enviarProposta", startTime);
        return response;
    }

    @Operation(summary = "Avaliar uma empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação enviada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/avaliar-empresa")
    public ResponseEntity<?> avaliarEmpresa(@RequestHeader("Authorization") String token, @RequestBody RequestAvaliacaoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarEmpresa", request);
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        freelancerService.avaliarEmpresa(request, jwtDto);
        ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Avaliação enviada com sucesso!").build());
        LoggerUtils.logElapsedTime(LOGGER, "avaliarEmpresa", startTime);
        return response;
    }

    @Operation(summary = "Listar empresas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresas listadas com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/listar-empresas")
    public ResponseEntity<?> listaEmpresa(@RequestHeader("Authorization") String token) {
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        List<ResponseEmpresaDto> lista = freelancerService.listarEmpresa(jwtDto);
        LoggerUtils.logElapsedTime(LOGGER, "listaEmpresa", startTime);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Listar projetos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projetos listados com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/listar-projetos")
    public ResponseEntity<?> listaProjetos(@RequestHeader("Authorization") String token) {
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        List<Projeto> lista = freelancerService.listarTodosProjetos(jwtDto);
        LoggerUtils.logElapsedTime(LOGGER, "listaProjetos", startTime);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Exibir detalhes de uma empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes da empresa exibidos com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/exibir-detalhes-empresa/{id}")
    public ResponseEntity<?> exibirDetalhesEmpresa(@RequestHeader("Authorization") String token,@PathVariable("id") Integer id) {
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        ResponseEmpresaCompletaDto empresa = freelancerService.obterDetalhesEmpresa(jwtDto,id);
        LoggerUtils.logElapsedTime(LOGGER, "exibirDetalhesEmpresa", startTime);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @Operation(summary = "Buscar projetos compatíveis com freelancer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projetos compatíveis listados com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/buscar-projeto-compativel/{id}")
    public ResponseEntity<?> buscarProjetoCompativel(@RequestHeader("Authorization") String token,@PathVariable("id") Integer id) {
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        List<ResponseProjetoCompatibilidadeDto> empresa = freelancerService.listaProjetosCompativeis(jwtDto,id);
        LoggerUtils.logElapsedTime(LOGGER, "buscarProjetoCompativel", startTime);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @Operation(summary = "Atualizar dados de um freelancer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/v1/atualizar-freelancer")
    public ResponseEntity<?> atualizarFreelancer(@RequestHeader("Authorization") String token,@RequestBody RequestAtualizarFreelancerDto request) {
        LoggerUtils.logRequestStart(LOGGER, "atualizarFreelancer", request);
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        freelancerService.atualizarDadosFreelancer(request,jwtDto);
        ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Freelancer atualizado com sucesso!").build());
        LoggerUtils.logElapsedTime(LOGGER, "atualizarFreelancer", startTime);
        return response;
    }

    @Operation(summary = "Entregar projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto entregue com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/entregar-projeto")
    public ResponseEntity<?> registrarEntregaDoProjeto(@RequestHeader("Authorization") String token,@RequestBody EntregarProjetoRequestDto entregarProjetoRequestDto) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarEmpresa", entregarProjetoRequestDto);
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        freelancerService.registrarEntregaProjeto(entregarProjetoRequestDto,jwtDto);

        ResponseEntity<StandardResponse> response =
                ResponseEntity.ok(StandardResponse
                        .builder()
                        .message(ConstantesPtBr.SUCESSO_ENTREGA_PROJETO)
                        .build()
                );
        LoggerUtils.logElapsedTime(LOGGER, "avaliarEmpresa", startTime);
        return response;
    }
}
