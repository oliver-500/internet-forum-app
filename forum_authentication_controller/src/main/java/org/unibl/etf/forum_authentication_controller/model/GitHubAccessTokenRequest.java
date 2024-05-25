package org.unibl.etf.forum_authentication_controller.model;

import lombok.Data;

@Data
public class GitHubAccessTokenRequest {
    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
}
