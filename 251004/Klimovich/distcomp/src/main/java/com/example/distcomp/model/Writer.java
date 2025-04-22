package com.example.distcomp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Writer {

    private long id;

    private String login;

    private String password;

    private String firstname;

    private String lastname;

}
