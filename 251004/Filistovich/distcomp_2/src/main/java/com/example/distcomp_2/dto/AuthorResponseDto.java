package com.example.distcomp_2.dto;

import lombok.Data;

@Data
public class AuthorResponseDto {
    private Long id;
    private String login;
    private String firstname;
    private String lastname;
}
