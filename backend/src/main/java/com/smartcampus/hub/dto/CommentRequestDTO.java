package com.smartcampus.hub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDTO {
    @NotBlank(message = "Comment content cannot be blank")
    private String content;
}
