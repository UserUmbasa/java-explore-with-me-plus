package ru.practicum.mainservice.events.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.user.model.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "events", schema = "public")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category categoryId;
    private Long confirmedRequests = 0L;

    @JoinColumn(name = "created_on")
    private LocalDateTime createdOn;

    private String description;

    @JoinColumn(name = "event_date")
    private LocalDateTime eventDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    private User initiatorId;

    @JoinColumn(name = "location_id")
    private Long locationId;

    private Boolean paid;

    @JoinColumn(name = "participant_limit")
    private Long participantLimit;

    @JoinColumn(name = "published_on")
    private LocalDateTime publishedOn;

    @JoinColumn(name = "request_moderation")
    private Boolean requestModeration;

    private State state;

    private String title;

    private Long views;

}
