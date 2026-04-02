package com.smartcampus.hub.dto;

import com.smartcampus.hub.enums.MemberOneResourceStatus;
import com.smartcampus.hub.enums.MemberOneResourceType;
import jakarta.validation.constraints.*;
import java.time.LocalTime;

/**
 * Request DTO for Resource Creation/Update - Member 1 Module
 */
public class MemberOneResourceRequestDTO {
    
    @NotBlank(message = "Resource name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Resource type is required")
    private MemberOneResourceType type;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be greater than 0")
    @Max(value = 500, message = "Capacity cannot exceed 500")
    private Integer capacity;

    @NotBlank(message = "Location is required")
    @Size(min = 3, max = 100, message = "Location must be between 3 and 100 characters")
    private String location;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Status is required")
    private MemberOneResourceStatus status;

    private LocalTime availableFrom;
    private LocalTime availableTo;

    // Default Constructor
    public MemberOneResourceRequestDTO() {}

    // All Args Constructor
    public MemberOneResourceRequestDTO(String name, MemberOneResourceType type, Integer capacity, 
                                      String location, String description, MemberOneResourceStatus status,
                                      LocalTime availableFrom, LocalTime availableTo) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.location = location;
        this.description = description;
        this.status = status;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    // Getters
    public String getName() { return name; }
    public MemberOneResourceType getType() { return type; }
    public Integer getCapacity() { return capacity; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public MemberOneResourceStatus getStatus() { return status; }
    public LocalTime getAvailableFrom() { return availableFrom; }
    public LocalTime getAvailableTo() { return availableTo; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setType(MemberOneResourceType type) { this.type = type; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public void setLocation(String location) { this.location = location; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(MemberOneResourceStatus status) { this.status = status; }
    public void setAvailableFrom(LocalTime availableFrom) { this.availableFrom = availableFrom; }
    public void setAvailableTo(LocalTime availableTo) { this.availableTo = availableTo; }
}

