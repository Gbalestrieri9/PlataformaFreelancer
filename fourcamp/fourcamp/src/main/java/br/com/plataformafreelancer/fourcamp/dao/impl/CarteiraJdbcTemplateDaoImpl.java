package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.ICarteiraJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.MovimentacaoResponseDBDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseSaldoCarteiraDBDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarteiraJdbcTemplateDaoImpl implements ICarteiraJdbcTemplateDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public ResponseSaldoCarteiraDBDTO visualizarSaldo(String email){
        String sql = "SELECT public.visualizar_saldo('" + email + "')";

        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(ResponseSaldoCarteiraDBDTO.class)
        );
    }

    @Override
    public ResponseSaldoCarteiraDBDTO depositarValor(String email, float valor, LocalDate dataDaTransacao) {
        String sql = "SELECT public.depositar_valor('" + email + "', " + valor + ", '"+ dataDaTransacao + "')";

        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(ResponseSaldoCarteiraDBDTO.class)
        );
    }

    @Override
    public ResponseSaldoCarteiraDBDTO sacarValor(String email, float valor, LocalDate dataDaTranscao) {
        String sql = "SELECT public.sacar_valor('" + email + "', " + valor + ", '"+ dataDaTranscao + "')";

        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(ResponseSaldoCarteiraDBDTO.class)
        );
    }

    @Override
    public List<MovimentacaoResponseDBDto> buscarMovimentacoes(String emailValidado) {
        String sql = "SELECT * FROM public.consultar_movimentacoes('" + emailValidado + "')";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(MovimentacaoResponseDBDto.class)
        );
    }
}
