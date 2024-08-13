package br.com.plataformafreelancer.fourcamp.dao;

import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseSaldoCarteiraDBDTO;
import br.com.plataformafreelancer.fourcamp.exceptions.PlataformaFreelancerDBException;

public interface ICarteiraJdbcTemplateDao {

    ResponseSaldoCarteiraDBDTO visualizarSaldo(String documento) throws PlataformaFreelancerDBException;
}
