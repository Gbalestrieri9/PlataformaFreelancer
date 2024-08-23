package br.com.plataformafreelancer.fourcamp.dao;

import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.MovimentacaoResponseDBDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseSaldoCarteiraDBDTO;

import java.time.LocalDate;
import java.util.List;

public interface ICarteiraJdbcTemplateDao {

    ResponseSaldoCarteiraDBDTO visualizarSaldo(String email);

    ResponseSaldoCarteiraDBDTO depositarValor(String email, float valor, LocalDate dataDaTransacao);

    ResponseSaldoCarteiraDBDTO sacarValor(String email, float valor, LocalDate dataDaTransacao);

    List<MovimentacaoResponseDBDto> buscarMovimentacoes(String emailValidado);
}
