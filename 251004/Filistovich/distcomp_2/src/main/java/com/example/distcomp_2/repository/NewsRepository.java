package com.example.distcomp_2.repository;

import com.example.distcomp_2.model.News;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    //List<News> getAll();
    News getNewsById(@Positive Long id);
    void deleteNewsById(@Positive Long id);

    boolean existsByTitle(@Size(min=2, max=64) String title);
}
