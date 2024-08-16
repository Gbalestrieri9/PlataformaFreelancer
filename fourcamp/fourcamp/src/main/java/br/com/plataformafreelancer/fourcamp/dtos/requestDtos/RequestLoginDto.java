package br.com.plataformafreelancer.fourcamp.dtos.requestDtos;

import lombok.Data;

@Data
public class RequestLoginDto {
    private String email;
    private String senha;
}

