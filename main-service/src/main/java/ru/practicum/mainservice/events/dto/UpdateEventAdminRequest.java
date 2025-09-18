package ru.practicum.mainservice.events.dto;

import lombok.Data;
import ru.practicum.mainservice.events.model.State;

@Data
public class UpdateEventAdminRequest {
    State stateAction;
}
