package br.com.plataformafreelancer.fourcamp.dao;

import br.com.plataformafreelancer.fourcamp.model.Administrador;

import java.util.Optional;

public interface IAdministradorJdbcTemplateDao {
    Optional<Administrador> findById(Integer idAdministrador);
    public void salvarAdministrador(Administrador administrador);

}
