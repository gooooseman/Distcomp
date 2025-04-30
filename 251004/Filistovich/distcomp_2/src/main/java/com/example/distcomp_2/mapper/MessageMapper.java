package com.example.distcomp_2.mapper;

import com.example.distcomp_2.dto.AuthorCreateDto;
import com.example.distcomp_2.dto.AuthorResponseDto;
import com.example.distcomp_2.dto.MessageCreateDto;
import com.example.distcomp_2.dto.MessageResponseDto;
import com.example.distcomp_2.model.Author;
import com.example.distcomp_2.model.Message;
import com.example.distcomp_2.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "content", source = "dto.content")
    Message toEntity(MessageCreateDto dto, News news);
    @Mapping(target = "newsId", source = "entity.news.id")
    MessageResponseDto toDto(Message entity);
}
