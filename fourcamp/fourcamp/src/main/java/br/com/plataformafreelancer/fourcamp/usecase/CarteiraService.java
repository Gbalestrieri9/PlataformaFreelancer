package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.CarteiraJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.DepositarValorRequestDTO;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.JwtDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.SacarValorRequestDTO;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.MovimentacaoResponseDBDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseSaldoCarteiraDBDTO;
import br.com.plataformafreelancer.fourcamp.utils.validators.general.ValidadorDeEmail;
import br.com.plataformafreelancer.fourcamp.utils.validators.general.ValidadorDeValoresNegativos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarteiraService {

    @Autowired
    CarteiraJdbcTemplateDaoImpl carteiraJdbcTemplateDao;

    LocalDate dataDaTransacao = LocalDate.now();

    public ResponseSaldoCarteiraDBDTO visualizarSaldo(JwtDto jwtDto) {
         String email = jwtDto.getEmail();
        return carteiraJdbcTemplateDao.visualizarSaldo(email);
    }

    public ResponseSaldoCarteiraDBDTO depositarValor(DepositarValorRequestDTO depositarValorRequestDTO, JwtDto jwtDto) {
        String email = jwtDto.getEmail();
        float valor = ValidadorDeValoresNegativos
                .validarValorFloat(depositarValorRequestDTO.getValor()
                );

        return carteiraJdbcTemplateDao.depositarValor(email, valor, dataDaTransacao);
    }

    public ResponseSaldoCarteiraDBDTO sacarValor(SacarValorRequestDTO sacarValorRequestDTO, JwtDto jwtDto) {
        String email = jwtDto.getEmail();
        float valor = ValidadorDeValoresNegativos
                .validarValorFloat(sacarValorRequestDTO.getValor()
                );

        return carteiraJdbcTemplateDao.sacarValor(email, valor, dataDaTransacao);
    }

    public List<MovimentacaoResponseDBDto> buscarMovimentacoes(JwtDto jwtDto) {
        String email = jwtDto.getEmail();
        return carteiraJdbcTemplateDao.buscarMovimentacoes(email);
    }
}
