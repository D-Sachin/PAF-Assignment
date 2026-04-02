package com.smartcampus.hub.controller;

import com.smartcampus.hub.dto.MemberOneResourceRequestDTO;
import com.smartcampus.hub.dto.MemberOneResourceResponseDTO;
import com.smartcampus.hub.enums.MemberOneResourceType;
import com.smartcampus.hub.enums.MemberOneResourceStatus;
import com.smartcampus.hub.service.MemberOneResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/member1/resources")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MemberOneResourceController {

    private final MemberOneResourceService resourceService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> createResource(@Valid @RequestBody MemberOneResourceRequestDTO requestDTO) {
        try {
            MemberOneResourceResponseDTO response = resourceService.createResource(requestDTO);
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Resource created successfully");
            result.put("data", response);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllResources(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberOneResourceResponseDTO> page = resourceService.getAllResources(pageable);
        Map<String, Object> paginationMap = new HashMap<>();
        paginationMap.put("currentPage", page.getNumber());
        paginationMap.put("totalPages", page.getTotalPages());
        paginationMap.put("totalElements", page.getTotalElements());
        paginationMap.put("pageSize", page.getSize());
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Resources retrieved successfully");
        result.put("data", page.getContent());
        result.put("pagination", paginationMap);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getResourceById(@PathVariable Long id) {
        try {
            MemberOneResourceResponseDTO response = resourceService.getResourceById(id);
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Resource retrieved successfully");
            result.put("data", response);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getActiveResources() {
        List<MemberOneResourceResponseDTO> resources = resourceService.getActiveResources();
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Active resources retrieved successfully");
        result.put("data", resources);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchResources(
            @RequestParam String term,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberOneResourceResponseDTO> page = resourceService.searchResources(term, pageable);
        Map<String, Object> paginationMap = new HashMap<>();
        paginationMap.put("currentPage", page.getNumber());
        paginationMap.put("totalPages", page.getTotalPages());
        paginationMap.put("totalElements", page.getTotalElements());
        paginationMap.put("pageSize", page.getSize());
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Search completed successfully");
        result.put("data", page.getContent());
        result.put("pagination", paginationMap);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/advanced-search")
    public ResponseEntity<Map<String, Object>> advancedSearch(
            @RequestParam(required = false) MemberOneResourceType type,
            @RequestParam(required = false) MemberOneResourceStatus status,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity,
            @RequestParam(required = false) String term,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberOneResourceResponseDTO> page = resourceService.advancedSearch(
                type, status, location, minCapacity, maxCapacity, term, pageable);
        Map<String, Object> paginationMap = new HashMap<>();
        paginationMap.put("currentPage", page.getNumber());
        paginationMap.put("totalPages", page.getTotalPages());
        paginationMap.put("totalElements", page.getTotalElements());
        paginationMap.put("pageSize", page.getSize());
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Advanced search completed successfully");
        result.put("data", page.getContent());
        result.put("pagination", paginationMap);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter/by-type")
    public ResponseEntity<Map<String, Object>> filterByType(
            @RequestParam MemberOneResourceType type,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberOneResourceResponseDTO> page = resourceService.filterByType(type, pageable);
        Map<String, Object> paginationMap = new HashMap<>();
        paginationMap.put("currentPage", page.getNumber());
        paginationMap.put("totalPages", page.getTotalPages());
        paginationMap.put("totalElements", page.getTotalElements());
        paginationMap.put("pageSize", page.getSize());
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Filtered by type successfully");
        result.put("data", page.getContent());
        result.put("pagination", paginationMap);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter/by-location")
    public ResponseEntity<Map<String, Object>> filterByLocation(
            @RequestParam String location,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberOneResourceResponseDTO> page = resourceService.filterByLocation(location, pageable);
        Map<String, Object> paginationMap = new HashMap<>();
        paginationMap.put("currentPage", page.getNumber());
        paginationMap.put("totalPages", page.getTotalPages());
        paginationMap.put("totalElements", page.getTotalElements());
        paginationMap.put("pageSize", page.getSize());
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Filtered by location successfully");
        result.put("data", page.getContent());
        result.put("pagination", paginationMap);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter/by-capacity")
    public ResponseEntity<Map<String, Object>> filterByCapacity(
            @RequestParam Integer capacity,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberOneResourceResponseDTO> page = resourceService.filterByCapacity(capacity, pageable);
        Map<String, Object> paginationMap = new HashMap<>();
        paginationMap.put("currentPage", page.getNumber());
        paginationMap.put("totalPages", page.getTotalPages());
        paginationMap.put("totalElements", page.getTotalElements());
        paginationMap.put("pageSize", page.getSize());
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Filtered by capacity successfully");
        result.put("data", page.getContent());
        result.put("pagination", paginationMap);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter/by-status")
    public ResponseEntity<Map<String, Object>> filterByStatus(
            @RequestParam MemberOneResourceStatus status,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberOneResourceResponseDTO> page = resourceService.filterByStatus(status, pageable);
        Map<String, Object> paginationMap = new HashMap<>();
        paginationMap.put("currentPage", page.getNumber());
        paginationMap.put("totalPages", page.getTotalPages());
        paginationMap.put("totalElements", page.getTotalElements());
        paginationMap.put("pageSize", page.getSize());
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Filtered by status successfully");
        result.put("data", page.getContent());
        result.put("pagination", paginationMap);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/suggestions/locations")
    public ResponseEntity<Map<String, Object>> getLocationSuggestions(@RequestParam String prefix) {
        List<String> suggestions = resourceService.getLocationSuggestions(prefix);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Location suggestions retrieved successfully");
        result.put("data", suggestions);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> updateResource(
            @PathVariable Long id,
            @Valid @RequestBody MemberOneResourceRequestDTO requestDTO) {
        try {
            MemberOneResourceResponseDTO response = resourceService.updateResource(id, requestDTO);
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Resource updated successfully");
            result.put("data", response);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> updateResourceStatus(
            @PathVariable Long id,
            @RequestParam MemberOneResourceStatus status) {
        try {
            MemberOneResourceResponseDTO response = resourceService.updateResourceStatus(id, status);
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Resource status updated successfully");
            result.put("data", response);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteResource(@PathVariable Long id) {
        try {
            resourceService.deleteResource(id);
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Resource deleted successfully");
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
