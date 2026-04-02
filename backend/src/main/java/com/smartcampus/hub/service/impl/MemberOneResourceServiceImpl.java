package com.smartcampus.hub.service.impl;

import com.smartcampus.hub.dto.MemberOneResourceRequestDTO;
import com.smartcampus.hub.dto.MemberOneResourceResponseDTO;
import com.smartcampus.hub.enums.MemberOneResourceType;
import com.smartcampus.hub.enums.MemberOneResourceStatus;
import com.smartcampus.hub.model.MemberOneResource;
import com.smartcampus.hub.repository.MemberOneResourceRepository;
import com.smartcampus.hub.service.MemberOneResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberOneResourceServiceImpl implements MemberOneResourceService {

    private final MemberOneResourceRepository resourceRepository;

    @Override
    public MemberOneResourceResponseDTO createResource(MemberOneResourceRequestDTO requestDTO) {
        if (resourceRepository.existsByNameIgnoreCase(requestDTO.getName())) {
            throw new IllegalArgumentException("Resource with name already exists");
        }
        
        if (requestDTO.getAvailableFrom() != null && requestDTO.getAvailableTo() != null) {
            if (requestDTO.getAvailableFrom().isAfter(requestDTO.getAvailableTo())) {
                throw new IllegalArgumentException("Start time must be before end time");
            }
        }
        
        MemberOneResource resource = new MemberOneResource();
        resource.setName(requestDTO.getName());
        resource.setType(requestDTO.getType());
        resource.setCapacity(requestDTO.getCapacity());
        resource.setLocation(requestDTO.getLocation());
        resource.setDescription(requestDTO.getDescription());
        resource.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : MemberOneResourceStatus.ACTIVE);
        resource.setAvailableFrom(requestDTO.getAvailableFrom());
        resource.setAvailableTo(requestDTO.getAvailableTo());
        
        return mapToResponseDTO(resourceRepository.save(resource));
    }

    @Override
    @Transactional(readOnly = true)
    public MemberOneResourceResponseDTO getResourceById(Long id) {
        MemberOneResource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
        return mapToResponseDTO(resource);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberOneResourceResponseDTO> getAllResources(Pageable pageable) {
        return resourceRepository.findAll(pageable).map(this::mapToResponseDTO);
    }

    @Override
    public MemberOneResourceResponseDTO updateResource(Long id, MemberOneResourceRequestDTO requestDTO) {
        MemberOneResource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
        resource.setName(requestDTO.getName());
        resource.setType(requestDTO.getType());
        resource.setCapacity(requestDTO.getCapacity());
        resource.setLocation(requestDTO.getLocation());
        resource.setDescription(requestDTO.getDescription());
        resource.setStatus(requestDTO.getStatus());
        resource.setAvailableFrom(requestDTO.getAvailableFrom());
        resource.setAvailableTo(requestDTO.getAvailableTo());
        return mapToResponseDTO(resourceRepository.save(resource));
    }

    @Override
    public void deleteResource(Long id) {
        if (!resourceRepository.existsById(id)) {
            throw new RuntimeException("Resource not found");
        }
        resourceRepository.deleteById(id);
    }

    @Override
    public MemberOneResourceResponseDTO updateResourceStatus(Long id, MemberOneResourceStatus status) {
        MemberOneResource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
        resource.setStatus(status);
        return mapToResponseDTO(resourceRepository.save(resource));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberOneResourceResponseDTO> getActiveResources() {
        return resourceRepository.findByStatus(MemberOneResourceStatus.ACTIVE).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberOneResourceResponseDTO> searchResources(String term, Pageable pageable) {
        return resourceRepository.searchByName(term, pageable).map(this::mapToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberOneResourceResponseDTO> filterByType(MemberOneResourceType type, Pageable pageable) {
        return resourceRepository.findByType(type, pageable).map(this::mapToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberOneResourceResponseDTO> filterByLocation(String location, Pageable pageable) {
        return resourceRepository.findByLocationIgnoreCase(location, pageable).map(this::mapToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberOneResourceResponseDTO> filterByCapacity(Integer capacity, Pageable pageable) {
        return resourceRepository.findByCapacityGreaterThanEqual(capacity, pageable).map(this::mapToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberOneResourceResponseDTO> filterByStatus(MemberOneResourceStatus status, Pageable pageable) {
        return resourceRepository.findByStatus(status, pageable).map(this::mapToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberOneResourceResponseDTO> advancedSearch(
            MemberOneResourceType type,
            MemberOneResourceStatus status,
            String location,
            Integer minCapacity,
            Integer maxCapacity,
            String term,
            Pageable pageable) {
        return resourceRepository.advancedSearch(type, status, location, minCapacity, maxCapacity, term, pageable)
                .map(this::mapToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getLocationSuggestions(String prefix) {
        return resourceRepository.findLocationsByPrefix(prefix);
    }

    private MemberOneResourceResponseDTO mapToResponseDTO(MemberOneResource resource) {
        MemberOneResourceResponseDTO dto = new MemberOneResourceResponseDTO();
        dto.setId(resource.getId());
        dto.setName(resource.getName());
        dto.setType(resource.getType());
        dto.setCapacity(resource.getCapacity());
        dto.setLocation(resource.getLocation());
        dto.setDescription(resource.getDescription());
        dto.setStatus(resource.getStatus());
        dto.setAvailableFrom(resource.getAvailableFrom());
        dto.setAvailableTo(resource.getAvailableTo());
        dto.setCreatedAt(resource.getCreatedAt());
        dto.setUpdatedAt(resource.getUpdatedAt());
        return dto;
    }
}
