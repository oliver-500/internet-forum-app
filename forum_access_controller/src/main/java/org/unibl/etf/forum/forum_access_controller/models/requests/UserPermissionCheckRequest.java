package org.unibl.etf.forum.forum_access_controller.models.requests;

import lombok.Data;

@Data
public class UserPermissionCheckRequest {
   private String methodName;
   private int userId;
   private int roomId;
}
