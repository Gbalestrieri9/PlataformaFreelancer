package br.com.plataformafreelancer.fourcamp.model;

import br.com.plataformafreelancer.fourcamp.enuns.StatusValidacaoEntrega;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {
    private int idProjeto;
    private String titulo;
    private String descricao;
    private String orcamento;
    private String prazo;
    private StatusValidacaoEntrega statusValidacaoEntrega;
    private String dataCriacao;
    private Integer empresaId;
    private Integer freelancerId;
    private List<String> habilidades;
}



