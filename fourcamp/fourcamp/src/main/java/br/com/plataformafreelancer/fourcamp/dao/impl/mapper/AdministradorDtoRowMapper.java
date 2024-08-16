package br.com.plataformafreelancer.fourcamp.dao.impl.mapper;

import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseAdministradorDto;
import br.com.plataformafreelancer.fourcamp.enuns.StatusAdministrador;
import br.com.plataformafreelancer.fourcamp.enuns.StatusFreelancer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministradorDtoRowMapper implements RowMapper<ResponseAdministradorDto> {
    @Override
    public ResponseAdministradorDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ResponseAdministradorDto.builder()
                .idAdministrador(rs.getInt("idusuario"))
                .email(rs.getString("email"))
                .cpf(rs.getString("cpf"))
                .nome(rs.getString("nome"))
                .dataCriacao(rs.getString("dataCriacao"))
                .statusAdministrador(StatusAdministrador.valueOf(rs.getString("status").toUpperCase()))
                .build();
    }
}