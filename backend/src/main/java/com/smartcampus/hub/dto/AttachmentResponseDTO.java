package com.smartcampus.hub.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentResponseDTO {
    private Long id;
    private String fileName;
    private String fileUrl;
}
