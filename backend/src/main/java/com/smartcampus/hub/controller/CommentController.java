package com.smartcampus.hub.controller;

import com.smartcampus.hub.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final TicketService ticketService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        ticketService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
