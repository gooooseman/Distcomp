package com.example.distcomp_2.mapper;

import com.example.distcomp_2.dto.AuthorCreateDto;
import com.example.distcomp_2.dto.AuthorResponseDto;
import com.example.distcomp_2.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(AuthorCreateDto dto);
    AuthorResponseDto toDto(Author entity);
}
