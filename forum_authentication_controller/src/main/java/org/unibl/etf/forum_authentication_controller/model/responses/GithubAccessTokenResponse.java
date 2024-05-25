package org.unibl.etf.forum_authentication_controller.model.responses;

import lombok.Data;

@Data
public class GithubAccessTokenResponse {
    private String error;
    private String access_token;
}
