package com.example.distcomp_2.repository;

import com.example.distcomp_2.model.Marker;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, Long> {
    //List<Marker> getAll();
    Marker getMarkerById(@Positive Long id);
    void deleteMarkerById(@Positive Long id);
}
