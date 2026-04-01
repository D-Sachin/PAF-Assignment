package com.smartcampus.hub.dto;

import com.smartcampus.hub.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateDTO {
    @NotNull(message = "Status is required")
    private TicketStatus status;
}
