package com.smartcampus.hub.model;

import com.smartcampus.hub.enums.Priority;
import com.smartcampus.hub.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String resourceLocation;
    private String preferredContactDetails;

    @Column(columnDefinition = "TEXT")
    private String resolutionNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    private User technician;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public Priority getPriority() { return priority; }
    public TicketStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getResourceLocation() { return resourceLocation; }
    public String getPreferredContactDetails() { return preferredContactDetails; }
    public String getResolutionNotes() { return resolutionNotes; }
    public User getUser() { return user; }
    public User getTechnician() { return technician; }
    public List<Comment> getComments() { return comments; }
    public List<Attachment> getAttachments() { return attachments; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public void setStatus(TicketStatus status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setResourceLocation(String resourceLocation) { this.resourceLocation = resourceLocation; }
    public void setPreferredContactDetails(String preferredContactDetails) { this.preferredContactDetails = preferredContactDetails; }
    public void setResolutionNotes(String resolutionNotes) { this.resolutionNotes = resolutionNotes; }
    public void setUser(User user) { this.user = user; }
    public void setTechnician(User technician) { this.technician = technician; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
    public void setAttachments(List<Attachment> attachments) { this.attachments = attachments; }
}
