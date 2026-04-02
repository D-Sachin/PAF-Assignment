package com.smartcampus.hub.dto;

import com.smartcampus.hub.enums.MemberOneResourceStatus;
import com.smartcampus.hub.enums.MemberOneResourceType;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Response DTO for Resource - Member 1 Module
 */
public class MemberOneResourceResponseDTO {
    
    private Long id;
    private String name;
    private MemberOneResourceType type;
    private Integer capacity;
    private String location;
    private String description;
    private MemberOneResourceStatus status;
    private LocalTime availableFrom;
    private LocalTime availableTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MemberOneResourceResponseDTO() {}

    public MemberOneResourceResponseDTO(Long id, String name, MemberOneResourceType type, Integer capacity,
                                       String location, String description, MemberOneResourceStatus status,
                                       LocalTime availableFrom, LocalTime availableTo,
                                       LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.location = location;
        this.description = description;
        this.status = status;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public MemberOneResourceType getType() { return type; }
    public Integer getCapacity() { return capacity; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public MemberOneResourceStatus getStatus() { return status; }
    public LocalTime getAvailableFrom() { return availableFrom; }
    public LocalTime getAvailableTo() { return availableTo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(MemberOneResourceType type) { this.type = type; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public void setLocation(String location) { this.location = location; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(MemberOneResourceStatus status) { this.status = status; }
    public void setAvailableFrom(LocalTime availableFrom) { this.availableFrom = availableFrom; }
    public void setAvailableTo(LocalTime availableTo) { this.availableTo = availableTo; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getResourceTypeDisplay() {
        return type != null ? type.getDisplayName() : "";
    }

    public String getResourceStatusDisplay() {
        return status != null ? status.getDisplayName() : "";
    }

    public String getAvailabilityWindow() {
        if (availableFrom != null && availableTo != null) {
            return availableFrom + " - " + availableTo;
        }
        return "Not specified";
    }
}

