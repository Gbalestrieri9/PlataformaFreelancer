package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.UsuarioJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.model.Usuario;
import br.com.plataformafreelancer.fourcamp.utils.JwtConfig;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class UsuarioService {

    @Autowired
    UsuarioJdbcTemplateDaoImpl usuarioJdbcTemplateDao;

    public String login(String email, String senha) {
        Usuario usuario = usuarioJdbcTemplateDao.buscarClientePorEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {

            Key chaveSecreta = JwtConfig.getChaveSecreta();

            return Jwts.builder()
                    .claim("email", usuario.getEmail())
                    .claim("senha", usuario.getSenha())
                    .claim("tipoUsuario", usuario.getTipoUsuario())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                    .signWith(chaveSecreta)
                    .compact();

        } else {
            return null;
        }
    }
}
