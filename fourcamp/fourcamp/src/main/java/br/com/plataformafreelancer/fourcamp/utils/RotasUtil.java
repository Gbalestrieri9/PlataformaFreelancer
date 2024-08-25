package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.PathsAndRoles;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Component
public class RotasUtil {

//    public static Map<String, List<TipoUsuario>> getRotas(ResourceLoader resourceLoader){
//
//        Map<String, List<TipoUsuario>> rotas = new HashMap<>();
//
//        rotas.put("/v1/enviar-proposta" , Arrays.asList(TipoUsuario.FREELANCER));
//        rotas.put("/v1/avaliar-empresa", Arrays.asList(TipoUsuario.FREELANCER));
//        rotas.put("/v1/listar-empresas", Arrays.asList(TipoUsuario.FREELANCER));
//        rotas.put("/v1/listar-projetos", Arrays.asList(TipoUsuario.FREELANCER));
//        rotas.put("/v1/exibir-detalhes-empresa/{id}", Arrays.asList(TipoUsuario.FREELANCER));
//        rotas.put("/v1/buscar-projeto-compativel/{id}", Arrays.asList(TipoUsuario.FREELANCER));
//        rotas.put("/v1/atualizar-freelancer", Arrays.asList(TipoUsuario.FREELANCER));
//
//        rotas.put("/v1/buscar-saldo", Arrays.asList(TipoUsuario.FREELANCER,TipoUsuario.ADMINISTRADOR,TipoUsuario.EMPRESA));
//        rotas.put("/v1/buscar-movimentacoes", Arrays.asList(TipoUsuario.FREELANCER,TipoUsuario.ADMINISTRADOR,TipoUsuario.EMPRESA));
//        rotas.put("/v1/depositar-valor", Arrays.asList(TipoUsuario.EMPRESA));
//        rotas.put("/v1/sacar-valor", Arrays.asList(TipoUsuario.FREELANCER, TipoUsuario.EMPRESA));
//
//        rotas.put("/v1/cadastrar-projeto", Arrays.asList(TipoUsuario.EMPRESA));
//        rotas.put("/v1/analisar-proposta", Arrays.asList(TipoUsuario.EMPRESA));
//        rotas.put("/v1/avaliar-freelancer", Arrays.asList(TipoUsuario.EMPRESA));
//        rotas.put("/v1/listar-freelancers", Arrays.asList(TipoUsuario.EMPRESA));
//        rotas.put("/v1/exibir-detalhes-freelancer/{id}", Arrays.asList(TipoUsuario.EMPRESA));
//        rotas.put("/v1/buscar-propostas-por-projeto/{id}", Arrays.asList(TipoUsuario.EMPRESA));
//        rotas.put("/v1/atualizar-empresa", Arrays.asList(TipoUsuario.EMPRESA));
//        rotas.put("/v1/atualizar-projeto", Arrays.asList(TipoUsuario.EMPRESA));
//        rotas.put("/v1/excluir-projeto/{id}", Arrays.asList(TipoUsuario.EMPRESA));
//
//        rotas.put("/v1/cadastrar-administrador", Arrays.asList(TipoUsuario.ADMINISTRADOR));
//
//        return rotas;
//    }

    public static HashMap<String, List<TipoUsuario>> getRotas(ResourceLoader resourceLoader) {
        HashMap<String, List<TipoUsuario>> pathsAndRoles = new HashMap<>();
        Resource resource = resourceLoader.getResource("classpath:rotas.json");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<PathsAndRoles> rolePathList = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<PathsAndRoles>>() {
            });

            for (PathsAndRoles rolePath : rolePathList) {
                pathsAndRoles.put(rolePath.getPath(), rolePath.getRoles());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathsAndRoles;
    }
}
