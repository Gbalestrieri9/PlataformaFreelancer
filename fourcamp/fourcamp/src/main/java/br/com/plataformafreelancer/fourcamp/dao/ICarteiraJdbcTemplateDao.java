package br.com.plataformafreelancer.fourcamp.dao;

import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseSaldoCarteiraDBDTO;
import br.com.plataformafreelancer.fourcamp.exceptions.PlataformaFreelancerDBException;

public interface ICarteiraJdbcTemplateDao {

    ResponseSaldoCarteiraDBDTO visualizarSaldo(String email);

    ResponseSaldoCarteiraDBDTO depositarValor(String email, float valor);

    ResponseSaldoCarteiraDBDTO sacarValor(String email, float valor);
}
