package org.unibl.etf.forum.forum_waf.DTO.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.unibl.etf.forum.forum_waf.DTO.UserAccessDTO;


@Data
public class UserActivationRequest {

    private UserAccessDTO userAccess;


    @NotNull(message = "The flag field is required.")
    private boolean activated;
    @NotNull(message = "The number field is required.")
    @Min(value = 0, message = "The number must be greater than or equal to 0.")
    private int userType;

    @NotNull(message = "The number field is required.")
    @Min(value = 1, message = "The number must be greater than or equal to 1.")
    private int userId;
}
