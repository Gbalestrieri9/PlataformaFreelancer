package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.JwtDto;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.security.Key;

public class JwtUtil {
    public static JwtDto decodeToken(String token) {
        Key chaveSecreta = JwtConfig.getChaveSecreta();

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(chaveSecreta)
                    .build()
                    .parseClaimsJws(token);
            Claims claims = jws.getBody();

            JwtDto jwtDto = new JwtDto();
            jwtDto.setEmail(claims.get("email", String.class));
            jwtDto.setSenha(claims.get("senha", String.class));
            jwtDto.setTipoUsuario(TipoUsuario.valueOf(claims.get("tipoUsuario", String.class)));

            return jwtDto;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
