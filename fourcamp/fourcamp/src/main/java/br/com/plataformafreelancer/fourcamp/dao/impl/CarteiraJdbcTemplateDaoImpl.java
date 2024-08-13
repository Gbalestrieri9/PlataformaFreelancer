package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.ICarteiraJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseSaldoCarteiraDBDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CarteiraJdbcTemplateDaoImpl implements ICarteiraJdbcTemplateDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public ResponseSaldoCarteiraDBDTO visualizarSaldo(String documento)  {
        String sql = "SELECT public.visualizar_saldo(" + documento + ")";

        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(ResponseSaldoCarteiraDBDTO.class)
        );
    }
}
