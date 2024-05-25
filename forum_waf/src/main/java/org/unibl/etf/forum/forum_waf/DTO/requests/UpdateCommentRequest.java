package org.unibl.etf.forum.forum_waf.DTO.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCommentRequest {
    @NotEmpty(message = "Name is required")
    @Size(min = 2, max = 500, message = "Comment content length")
    private String content;
    @NotNull(message = "The number field is required.")
    @Min(value = 1, message = "The number must be greater than or equal to 1.")
    private int id;
}
