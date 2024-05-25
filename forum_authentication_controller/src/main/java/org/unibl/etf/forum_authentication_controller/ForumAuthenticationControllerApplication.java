package org.unibl.etf.forum_authentication_controller;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.UrlResource;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})

public class ForumAuthenticationControllerApplication {



    public static void main(String[] args) throws IOException {

        SpringApplication.run(ForumAuthenticationControllerApplication.class, args);
        //System.out.println(System.getProperty("javax.net.ssl.trustStore"));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @PostConstruct
//    void postConstruct() {
//        //System.setProperty("javax.net.ssl.trustStore", trustStore);
//        InputStream inputStream = ForumAuthenticationControllerApplication.class.getClassLoader().getResourceAsStream(trustStore);
//
//        try{
//            if (inputStream != null) {
//                // Load the trustStore property
//                Properties properties = System.getProperties();
//                properties.load(inputStream);
//                System.setProperties(properties);
//                System.out.println("aaaaaaaaaaa");
//            }
//            else System.out.println("nullaa");
//        }
//        catch(IOException ex){
//            ex.printStackTrace();
//        }
//
//        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
//        System.setProperty("javax.net.ssl.trustStoreType", trustStoreType);
//
//    }



}
