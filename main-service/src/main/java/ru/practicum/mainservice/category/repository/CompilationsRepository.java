package ru.practicum.mainservice.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.category.model.Compilations;

public interface CompilationsRepository extends JpaRepository<Compilations, Long> {

}
