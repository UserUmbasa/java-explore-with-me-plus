package ru.practicum.mainservice.events.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventSearchParams {
    private String text;
    private List<Long> categories;
    private Boolean paid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeEnd;

    private Boolean onlyAvailable;
    private String sort;

    @Min(0)
    private Integer from = 0;

    @Min(1)
    private Integer size = 10;

    @AssertTrue(message = "rangeStart должен быть раньше rangeEnd")
    private boolean isValidRange() {
        if (rangeStart != null && rangeEnd != null) {
            return !rangeStart.isAfter(rangeEnd);
        }
        return true;
    }
}
