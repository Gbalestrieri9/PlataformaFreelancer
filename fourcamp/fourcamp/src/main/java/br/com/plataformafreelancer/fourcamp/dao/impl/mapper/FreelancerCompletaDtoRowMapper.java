package br.com.plataformafreelancer.fourcamp.dao.impl.mapper;

import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseFreelancerCompletaDto;
import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class FreelancerCompletaDtoRowMapper implements RowMapper<ResponseFreelancerCompletaDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public ResponseFreelancerCompletaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<String> habilidades = Collections.emptyList();String habilidadesJson = rs.getString("habilidades");
        if (habilidadesJson != null && !habilidadesJson.isEmpty()) {
            habilidades = objectMapper.readValue(habilidadesJson, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        }
        List<Avaliacao> avaliacoes = Collections.emptyList();String avaliacoesJson = rs.getString("avaliacoes");
        if (avaliacoesJson != null && !avaliacoesJson.isEmpty()) {
            avaliacoes = objectMapper.readValue(avaliacoesJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Avaliacao.class));
        }
        List<Projeto> projetos = Collections.emptyList();String projetosJson = rs.getString("projetos");
        if (projetosJson != null && !projetosJson.isEmpty()) {
            projetos = objectMapper.readValue(projetosJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Projeto.class));
        }      return ResponseFreelancerCompletaDto.builder()
                .idFreelancer(rs.getInt("id_freelancer"))
                .email(rs.getString("email"))
                .nome(rs.getString("nome"))
                .cpf(rs.getString("cpf"))
                .dataNascimento(rs.getString("data_nascimento"))
                .telefone(rs.getString("telefone"))
                .logradouro(rs.getString("logradouro"))
                .numero(rs.getString("numero"))
                .complemento(rs.getString("complemento"))
                .bairro(rs.getString("bairro"))
                .cidade(rs.getString("cidade"))
                .cep(rs.getString("cep"))
                .estado(rs.getString("estado"))
                .pais(rs.getString("pais"))
                .descricao(rs.getString("descricao"))
                .disponibilidade(rs.getString("disponibilidade"))
                .dataCriacao(rs.getString("data_criacao"))
                .statusFreelancer(rs.getString("status_freelancer"))
                .notaMedia(rs.getDouble("nota_media"))
                .habilidades(habilidades)
                .avaliacoes(avaliacoes)
                .projetos(projetos)
                .build();
    }
}
