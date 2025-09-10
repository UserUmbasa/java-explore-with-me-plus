package ru.practicum.dto;

import lombok.Data;

@Data
public class ViewStatsDTO {

    private String app;

    private String uri;

    private Long hits;
}
