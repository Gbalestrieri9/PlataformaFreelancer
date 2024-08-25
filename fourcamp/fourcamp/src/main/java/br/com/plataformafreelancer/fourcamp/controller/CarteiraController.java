package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.DepositarValorRequestDTO;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.JwtDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.SacarValorRequestDTO;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.MovimentacaoResponseDBDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseSaldoCarteiraDBDTO;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.CarteiraService;
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

import java.util.List;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarteiraController.class);
    @Autowired
    CarteiraService carteiraService;

    // Disponível para todos os perfis
    @Operation(summary = "Verificar saldo da carteira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o saldo"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/buscar-saldo")
    public ResponseEntity<?> buscarSaldo(@RequestHeader("Authorization") String token) {
        LoggerUtils.logRequestStart(LOGGER, "visualizarSaldo", token);
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        ResponseSaldoCarteiraDBDTO response = carteiraService.visualizarSaldo(jwtDto);
        LoggerUtils.logElapsedTime(LOGGER, "visualizarSaldo", startTime);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Disponível para todos os perfis
    @Operation(summary = "Verificar movimentações da carteira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o as movimentações"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/buscar-movimentacoes")
    public ResponseEntity<?> buscarMovimentacoes(@RequestHeader("Authorization") String token) {
        LoggerUtils.logRequestStart(LOGGER, "visualizarSaldo", token);
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        List<MovimentacaoResponseDBDto> movimentacoes =
                carteiraService.buscarMovimentacoes(jwtDto);

        if (movimentacoes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        LoggerUtils.logElapsedTime(LOGGER, "visualizarSaldo", startTime);
        return new ResponseEntity<>(movimentacoes, HttpStatus.OK);
    }

    // Dsiponível somente para empresas
    @Operation(summary = "Deposita valor na carteira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o saldo atualizado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/depositar-valor")
    public ResponseEntity<?> depositarValor(@RequestHeader("Authorization") String token,@RequestBody DepositarValorRequestDTO depositarValorRequestDTO) {
        LoggerUtils.logRequestStart(LOGGER, "depositarValor", depositarValorRequestDTO);
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        carteiraService.depositarValor(depositarValorRequestDTO,jwtDto);
        ResponseEntity<StandardResponse> response = ResponseEntity.ok(
                StandardResponse
                        .builder()
                        .message(ConstantesPtBr.DEPOSITO_REALIZADO_COM_SUCESSO)
                        .build()
        );
        LoggerUtils.logElapsedTime(LOGGER, "depositarValor", startTime);
        return response;
    }

    // Disponível para empresas e freelancers
    @Operation(summary = "Saca, da carteira, o valor informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma mensagem de sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/sacar-valor")
    public ResponseEntity<?> sacarValor(@RequestHeader("Authorization") String token,@RequestBody SacarValorRequestDTO sacarValorRequestDTO) {
        LoggerUtils.logRequestStart(LOGGER, "sacarValor", sacarValorRequestDTO);
        long startTime = System.currentTimeMillis();
        JwtDto jwtDto = JwtUtil.decodeToken(token);

        carteiraService.sacarValor(sacarValorRequestDTO,jwtDto);
        ResponseEntity<StandardResponse> response = ResponseEntity.ok(
                StandardResponse
                        .builder()
                        .message(ConstantesPtBr.SAQUE_REALIZADO_COM_SUCESSO)
                        .build()
        );
        LoggerUtils.logElapsedTime(LOGGER, "sacarValor", startTime);
        return response;
    }
}
