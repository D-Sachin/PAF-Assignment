package com.smartcampus.hub.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignTechnicianDTO {
    @NotNull(message = "Technician ID is required")
    private Long technicianId;
}
