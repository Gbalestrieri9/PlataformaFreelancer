package br.com.plataformafreelancer.fourcamp.model;

import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Avaliacao {
    private Integer idAvaliacao;
    private Integer empresaId;
    private Integer freelancerId;
    private Integer projetoId;
    private TipoUsuario avaliado;
    private Integer nota;
    private String comentario;
    private String dataAvaliacao;
}
