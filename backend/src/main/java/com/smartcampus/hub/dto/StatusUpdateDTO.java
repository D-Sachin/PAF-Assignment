package com.smartcampus.hub.dto;

import com.smartcampus.hub.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateDTO {
    @NotNull(message = "Status is required")
    private TicketStatus status;
}
