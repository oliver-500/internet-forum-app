package org.unibl.etf.forum.forum_access_controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ForumAccessControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForumAccessControllerApplication.class, args);
    }

}
