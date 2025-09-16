package ru.practicum.mainservice.category.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "event_example", schema = "public")
public class EventExample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "name")
    private String name;

}
