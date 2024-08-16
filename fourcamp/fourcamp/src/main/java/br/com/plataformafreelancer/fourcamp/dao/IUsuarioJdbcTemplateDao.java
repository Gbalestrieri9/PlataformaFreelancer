package br.com.plataformafreelancer.fourcamp.dao;

import br.com.plataformafreelancer.fourcamp.model.Usuario;

public interface IUsuarioJdbcTemplateDao {

    Usuario buscarClientePorEmail(String email);
}
