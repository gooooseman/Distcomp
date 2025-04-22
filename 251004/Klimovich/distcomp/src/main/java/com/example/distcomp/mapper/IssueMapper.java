package com.example.distcomp.mapper;

import com.example.distcomp.dto.IssueRequestTo;
import com.example.distcomp.dto.IssueResponseTo;
import com.example.distcomp.model.Issue;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IssueMapper {

    Issue toEntity(IssueRequestTo request);
    @Mapping(target = "writerId", source = "entity.writer.id")
    IssueResponseTo toResponse(Issue entity);

    List<Issue> listToEntity(List<IssueRequestTo> dtoList);

    List<IssueResponseTo> listToResponse(List<Issue> entityList);

}
