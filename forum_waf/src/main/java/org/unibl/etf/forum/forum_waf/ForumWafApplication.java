package org.unibl.etf.forum.forum_waf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ServletComponentScan
public class ForumWafApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForumWafApplication.class, args);
    }

}
