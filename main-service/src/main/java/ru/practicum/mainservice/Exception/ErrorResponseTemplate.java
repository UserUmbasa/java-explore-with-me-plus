package ru.practicum.mainservice.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseTemplate {
    private String error;
}
