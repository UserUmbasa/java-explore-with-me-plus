package ru.practicum.mainservice.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.mainservice.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u ORDER BY u.id")
    List<User> findAllWithOffset(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.id IN :ids ORDER BY u.id")
    List<User> findByIdIn(List<Long> ids, Pageable pageable);
}