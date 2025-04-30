package com.example.distcomp_2.mapper;

import com.example.distcomp_2.dto.AuthorCreateDto;
import com.example.distcomp_2.dto.AuthorResponseDto;
import com.example.distcomp_2.dto.MarkerCreateDto;
import com.example.distcomp_2.dto.NewsCreateDto;
import com.example.distcomp_2.dto.NewsResponseDto;
import com.example.distcomp_2.model.Author;
import com.example.distcomp_2.model.Marker;
import com.example.distcomp_2.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "markers", source = "markers")
    News toEntity(NewsCreateDto dto, Author author, List<Marker> markers);
    @Mapping(target = "authorId", source = "author.id")
    NewsResponseDto toDto(News entity);
}
