package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.CarteiraJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseSaldoCarteiraDBDTO;
import br.com.plataformafreelancer.fourcamp.exceptions.PlataformaFreelancerDBException;
import br.com.plataformafreelancer.fourcamp.model.Carteira;
import br.com.plataformafreelancer.fourcamp.utils.ValidadorDeCnpj;
import br.com.plataformafreelancer.fourcamp.utils.ValidadorDeCpf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarteiraService {

    @Autowired
    CarteiraJdbcTemplateDaoImpl carteiraJdbcTemplateDao;

    public ResponseSaldoCarteiraDBDTO visualizarSaldo(String documento)  {

        if(!ValidadorDeCpf.validarCpf(documento)){
            ValidadorDeCnpj.validarCnpj(documento);
        }

        return carteiraJdbcTemplateDao.visualizarSaldo(documento);
    }
}
