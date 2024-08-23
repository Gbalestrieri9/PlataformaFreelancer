package br.com.plataformafreelancer.fourcamp.model;

import br.com.plataformafreelancer.fourcamp.enuns.StatusValidacaoEntrega;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidarEntregaProjeto {
    private int idEmpresa;
    private int idProjeto;
    private StatusValidacaoEntrega statusValidacaoEntrega;
    private String observacao;
    private LocalDate dataValidacao;
}
