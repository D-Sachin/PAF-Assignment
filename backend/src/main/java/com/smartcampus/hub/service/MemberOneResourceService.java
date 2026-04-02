package com.smartcampus.hub.service;

import com.smartcampus.hub.dto.MemberOneResourceRequestDTO;
import com.smartcampus.hub.dto.MemberOneResourceResponseDTO;
import com.smartcampus.hub.enums.MemberOneResourceType;
import com.smartcampus.hub.enums.MemberOneResourceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for Resource Management - Member 1 Module
 */
public interface MemberOneResourceService {

    // Create
    MemberOneResourceResponseDTO createResource(MemberOneResourceRequestDTO requestDTO);

    // Read
    MemberOneResourceResponseDTO getResourceById(Long id);

    Page<MemberOneResourceResponseDTO> getAllResources(Pageable pageable);

    // Update
    MemberOneResourceResponseDTO updateResource(Long id, MemberOneResourceRequestDTO requestDTO);

    // Delete
    void deleteResource(Long id);

    // Status Update
    MemberOneResourceResponseDTO updateResourceStatus(Long id, MemberOneResourceStatus status);

    // Filtering & Search
    List<MemberOneResourceResponseDTO> getActiveResources();

    Page<MemberOneResourceResponseDTO> searchResources(String term, Pageable pageable);

    Page<MemberOneResourceResponseDTO> filterByType(MemberOneResourceType type, Pageable pageable);

    Page<MemberOneResourceResponseDTO> filterByLocation(String location, Pageable pageable);

    Page<MemberOneResourceResponseDTO> filterByCapacity(Integer capacity, Pageable pageable);

    Page<MemberOneResourceResponseDTO> filterByStatus(MemberOneResourceStatus status, Pageable pageable);

    Page<MemberOneResourceResponseDTO> advancedSearch(
            MemberOneResourceType type,
            MemberOneResourceStatus status,
            String location,
            Integer minCapacity,
            Integer maxCapacity,
            String term,
            Pageable pageable
    );

    List<String> getLocationSuggestions(String prefix);
}
