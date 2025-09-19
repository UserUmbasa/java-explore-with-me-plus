package ru.practicum.mainservice.category.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.mainservice.category.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM categories ORDER BY id LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Category> findWithOffsetAndLimit(@Param("offset") long offset, @Param("limit") int limit);

    boolean existsByName(String name);

    Category findByName(String name);
}