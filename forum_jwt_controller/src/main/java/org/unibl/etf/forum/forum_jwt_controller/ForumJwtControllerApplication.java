package org.unibl.etf.forum.forum_jwt_controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ForumJwtControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForumJwtControllerApplication.class, args);
    }

}
