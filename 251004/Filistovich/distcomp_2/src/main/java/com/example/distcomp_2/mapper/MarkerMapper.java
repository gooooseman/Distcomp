package com.example.distcomp_2.mapper;

import com.example.distcomp_2.dto.AuthorCreateDto;
import com.example.distcomp_2.dto.AuthorResponseDto;
import com.example.distcomp_2.dto.MarkerCreateDto;
import com.example.distcomp_2.dto.MarkerResponseDto;
import com.example.distcomp_2.model.Author;
import com.example.distcomp_2.model.Marker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarkerMapper {
    Marker toEntity(MarkerCreateDto dto);
    MarkerResponseDto toDto(Marker entity);
}
