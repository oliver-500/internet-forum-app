package org.unibl.etf.forum_authentication_controller.model.requests;

import lombok.Data;

@Data
public class UserPermissionCheckRequest {
   private String methodName;
   private int userId;
   private int roomId;
}
