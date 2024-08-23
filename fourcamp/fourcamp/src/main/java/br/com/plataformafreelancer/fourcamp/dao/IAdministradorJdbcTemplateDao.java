package br.com.plataformafreelancer.fourcamp.dao;

import br.com.plataformafreelancer.fourcamp.model.Administrador;
import br.com.plataformafreelancer.fourcamp.model.Projeto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IAdministradorJdbcTemplateDao {
    Optional<Administrador> findById(Integer idAdministrador);

    public void salvarAdministrador(Administrador administrador);

    void aprovarProjeto(int idValidado, LocalDate dataAtual);

    List<Projeto> listarProjetoPendente();
}
