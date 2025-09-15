package ru.practicum.mainservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseTemplate {
    private String error;
}
