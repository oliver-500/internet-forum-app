package org.unibl.etf.forum.forum_waf.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PermissionDTO {

    private int id;
    @NotEmpty(message = "Name is required")
    @Size(min = 2, max = 20, message = "Name length 2-20")
    private String name;

}
