package ru.practicum.mainservice.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDtoOut {
    private Long id;
    private String name;
    private String email;
}