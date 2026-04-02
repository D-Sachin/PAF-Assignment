package com.smartcampus.hub.repository;

import com.smartcampus.hub.model.MemberOneResource;
import com.smartcampus.hub.enums.MemberOneResourceType;
import com.smartcampus.hub.enums.MemberOneResourceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for MemberOneResource - Member 1 Module
 * Provides CRUD operations and custom queries for resource management
 */
@Repository
public interface MemberOneResourceRepository extends JpaRepository<MemberOneResource, Long> {

    // Check if resource exists by name
    boolean existsByNameIgnoreCase(String name);

    // Find by type with pagination
    Page<MemberOneResource> findByType(MemberOneResourceType type, Pageable pageable);

    // Find by status with pagination
    Page<MemberOneResource> findByStatus(MemberOneResourceStatus status, Pageable pageable);

    // Find by location with pagination
    Page<MemberOneResource> findByLocationIgnoreCase(String location, Pageable pageable);

    // Find by capacity greater than or equal
    Page<MemberOneResource> findByCapacityGreaterThanEqual(Integer capacity, Pageable pageable);

    // Find all active resources
    List<MemberOneResource> findByStatus(MemberOneResourceStatus status);

    // Search by name (contains)
    @Query("SELECT r FROM MemberOneResource r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :term, '%'))")
    Page<MemberOneResource> searchByName(@Param("term") String term, Pageable pageable);

    // Advanced search with multiple filters
    @Query("SELECT r FROM MemberOneResource r WHERE " +
            "(:type IS NULL OR r.type = :type) AND " +
            "(:status IS NULL OR r.status = :status) AND " +
            "(:location IS NULL OR LOWER(r.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:minCapacity IS NULL OR r.capacity >= :minCapacity) AND " +
            "(:maxCapacity IS NULL OR r.capacity <= :maxCapacity) AND " +
            "(:term IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :term, '%')))")
    Page<MemberOneResource> advancedSearch(
            @Param("type") MemberOneResourceType type,
            @Param("status") MemberOneResourceStatus status,
            @Param("location") String location,
            @Param("minCapacity") Integer minCapacity,
            @Param("maxCapacity") Integer maxCapacity,
            @Param("term") String term,
            Pageable pageable
    );

    // Find resources by location prefix (for location suggestions)
    @Query("SELECT DISTINCT r.location FROM MemberOneResource r WHERE LOWER(r.location) LIKE LOWER(CONCAT(:prefix, '%'))")
    List<String> findLocationsByPrefix(@Param("prefix") String prefix);
}
