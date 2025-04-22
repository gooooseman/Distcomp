package com.example.distcomp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Issue {
    private long id;

    private String title;

    private String content;

    private Timestamp created;

    private Timestamp modified;

    private long writerId;
}