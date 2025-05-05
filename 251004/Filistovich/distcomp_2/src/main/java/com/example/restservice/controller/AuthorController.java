package com.example.restservice.controller;

import com.example.restservice.dto.AuthorCreateDto;
import com.example.restservice.dto.AuthorResponseDto;
import com.example.restservice.mapper.AuthorMapper;
import com.example.restservice.model.Author;
import com.example.restservice.repository.AuthorRepository;
import com.example.restservice.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0")
public class AuthorController {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorMapper authorMapper, AuthorRepository authorRepository, AuthorService authorService) {
        this.authorMapper = authorMapper;
        this.authorRepository = authorRepository;
        this.authorService = authorService;
    }
    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorResponseDto> getAuthors() {
        return authorService.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody @Valid AuthorCreateDto inputDto) {
        Author entity = authorMapper.toEntity(inputDto);
        if (authorRepository.existsByLogin(entity.getLogin())) {
            return new ResponseEntity<>(authorMapper.toDto(entity), HttpStatus.FORBIDDEN);
        }
        Author saved = authorService.save(entity);
        return new ResponseEntity<>(authorMapper.toDto(saved), HttpStatus.CREATED);
    }

    @GetMapping("/authors/{id}")
    public AuthorResponseDto getAuthorById(@PathVariable Long id) {
        return authorMapper.toDto(authorService.findById(id));
    }

    @PutMapping("/authors")
    public ResponseEntity<AuthorResponseDto> updateAuthor(@RequestBody @Valid AuthorCreateDto in) {
        Author author = authorService.findById(in.getId());
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Author newAuthor = authorMapper.toEntity(in);
            Author updated = authorService.update(newAuthor);
            return ResponseEntity.ok(authorMapper.toDto(updated));
        }
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (authorService.findById(id) != null) {
            authorService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
