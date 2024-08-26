package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.UsuarioJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.exceptions.SenhaInvalidaException;
import br.com.plataformafreelancer.fourcamp.model.Usuario;
import br.com.plataformafreelancer.fourcamp.utils.ConstantesPtBr;
import br.com.plataformafreelancer.fourcamp.utils.JwtConfig;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    UsuarioJdbcTemplateDaoImpl usuarioJdbcTemplateDao;

    public String login(String email, String senha) {
        Usuario usuario = usuarioJdbcTemplateDao.buscarClientePorEmail(email);

        if (usuario != null && usuario.getSenha().equals(senha)) {

            Key chaveSecreta = JwtConfig.getChaveSecreta();

            TipoUsuario tipoUsuario = usuario.getTipoUsuario();
            int id = usuario.getId();

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", id);
            claims.put("email", email);
            claims.put("tipoUsuario", tipoUsuario);

            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                    .signWith(chaveSecreta)
                    .compact();
        } else {
            //return null;
            throw new SenhaInvalidaException(ConstantesPtBr.USUARIO_OU_SENHA_INVALIDOS);
        }
    }
}
