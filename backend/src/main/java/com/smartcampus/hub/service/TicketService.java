package com.smartcampus.hub.service;

import com.smartcampus.hub.dto.AttachmentResponseDTO;
import com.smartcampus.hub.dto.TicketRequestDTO;
import com.smartcampus.hub.dto.TicketResponseDTO;
import com.smartcampus.hub.enums.Priority;
import com.smartcampus.hub.enums.TicketStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TicketService {
    TicketResponseDTO createTicket(TicketRequestDTO dto);
    List<TicketResponseDTO> getAllTickets(TicketStatus status, Priority priority);
    List<TicketResponseDTO> getTicketsByUserId(Long userId, TicketStatus status, Priority priority);
    TicketResponseDTO updateTicketStatus(Long id, TicketStatus status);
    TicketResponseDTO assignTechnician(Long id, Long technicianId);
    AttachmentResponseDTO uploadAttachment(Long ticketId, MultipartFile file);
}
