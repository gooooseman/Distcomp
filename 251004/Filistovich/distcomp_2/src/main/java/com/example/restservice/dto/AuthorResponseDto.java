package com.example.restservice.dto;

import lombok.Data;

@Data
public class AuthorResponseDto {
    private Long id;
    private String login;
    private String firstname;
    private String lastname;
}
