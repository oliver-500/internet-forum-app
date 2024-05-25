package org.unibl.etf.forum.forum_waf.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRoomAccessDTO {
    @NotNull(message = "The number field is required.")
    @Min(value = 1, message = "The number must be greater than or equal to 1.")
    private int userId;
    @NotNull(message = "The number field is required.")
    @Min(value = 1, message = "The number must be greater than or equal to 1.")
    private int roomId;
}
