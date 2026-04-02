package com.smartcampus.hub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResponseDTO {
    private Long id;
    private String fileName;
    private String fileUrl;
}
