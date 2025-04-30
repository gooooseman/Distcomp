package com.example.distcomp_2.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewsResponseDto {
    private Long id;
    private String title;
    private String content;
    private Date created;
    private Date modified;
    private Long authorId;
    private List<MarkerResponseDto> markers;
}
