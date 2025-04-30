package com.example.distcomp_2.controller;

import com.example.distcomp_2.dto.MarkerCreateDto;
import com.example.distcomp_2.dto.MarkerResponseDto;
import com.example.distcomp_2.mapper.MarkerMapper;
import com.example.distcomp_2.model.Marker;
import com.example.distcomp_2.repository.MarkerRepository;
import com.example.distcomp_2.service.MarkerService;
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
public class MarkerController {

    private final MarkerMapper markerMapper;
    private final MarkerRepository markerRepository;
    private final MarkerService markerService;

    @Autowired
    public MarkerController(MarkerMapper markerMapper, MarkerRepository markerRepository, MarkerService markerService) {
        this.markerMapper = markerMapper;
        this.markerRepository = markerRepository;
        this.markerService = markerService;
    }
    @GetMapping("/markers")
    @ResponseStatus(HttpStatus.OK)
    public List<MarkerResponseDto> getMarkers() {
        return markerService.findAll().stream().map(markerMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/markers")
    @ResponseStatus(HttpStatus.CREATED)
    public MarkerResponseDto createMarker(@RequestBody @Valid MarkerCreateDto inputDto) {
        Marker entity = markerMapper.toEntity(inputDto);
        Marker saved = markerService.save(entity);
        return markerMapper.toDto(saved);
    }

    @GetMapping("/markers/{id}")
    public MarkerResponseDto getMarkerById(@PathVariable Long id) {
        return markerMapper.toDto(markerService.findById(id));
    }

    @PutMapping("/markers")
    public ResponseEntity<MarkerResponseDto> updateMarker(@RequestBody @Valid MarkerCreateDto in) {
        Marker marker = markerService.findById(in.getId());
        if (marker == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Marker newAuthor = markerMapper.toEntity(in);
            Marker updated = markerService.update(newAuthor);
            return ResponseEntity.ok(markerMapper.toDto(updated));
        }
    }

    @DeleteMapping("/markers/{id}")
    public ResponseEntity<Void> deleteMarker(@PathVariable Long id) {
        if (markerService.findById(id) != null) {
            markerService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
