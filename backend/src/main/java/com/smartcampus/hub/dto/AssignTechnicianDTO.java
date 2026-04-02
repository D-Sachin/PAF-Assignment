package com.smartcampus.hub.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignTechnicianDTO {
    @NotNull(message = "Technician ID is required")
    private Long technicianId;
}
