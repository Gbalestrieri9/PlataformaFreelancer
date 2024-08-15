package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.CarteiraJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.DepositarValorRequestDTO;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.SacarValorRequestDTO;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseSaldoCarteiraDBDTO;
import br.com.plataformafreelancer.fourcamp.utils.ValidadorDeEmail;
import br.com.plataformafreelancer.fourcamp.utils.ValidadorDeValoresNegativos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarteiraService {

    @Autowired
    CarteiraJdbcTemplateDaoImpl carteiraJdbcTemplateDao;

    public ResponseSaldoCarteiraDBDTO visualizarSaldo(String email)  {
        String emailValidado = ValidadorDeEmail.validarEmail(email);
        return carteiraJdbcTemplateDao.visualizarSaldo(emailValidado);
    }

    public ResponseSaldoCarteiraDBDTO depositarValor(DepositarValorRequestDTO depositarValorRequestDTO) {
        String email = ValidadorDeEmail
                .validarEmail(depositarValorRequestDTO.getEmail()
                );
        float valor = ValidadorDeValoresNegativos
                .validarValorFloat(depositarValorRequestDTO.getValor()
                );

        return carteiraJdbcTemplateDao.depositarValor(email, valor);
    }

    public ResponseSaldoCarteiraDBDTO sacarValor(SacarValorRequestDTO sacarValorRequestDTO) {
        String email = ValidadorDeEmail
                .validarEmail(sacarValorRequestDTO.getEmail()
                );
        float valor = ValidadorDeValoresNegativos
                .validarValorFloat(sacarValorRequestDTO.getValor()
                );

        return carteiraJdbcTemplateDao.sacarValor(email, valor);
    }
}
