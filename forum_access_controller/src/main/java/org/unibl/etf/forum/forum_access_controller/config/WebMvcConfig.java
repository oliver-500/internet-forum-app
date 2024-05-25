package org.unibl.etf.forum.forum_access_controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.unibl.etf.forum.forum_access_controller.interceptors.AuthorizationInterceptor;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    //    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry
//                .addViewController("/{path1:[\\w]+}")
//                .setViewName("forward:/");
//        registry
//                .addViewController("/{path1}/{path2:[\\w]+}")
//                .setViewName("forward:/");
//        registry
//                .addViewController("/{path1}/{path2}/{path3:[\\w]+}")
//                .setViewName("forward:/");
//    }

    private final AuthorizationInterceptor authorizationInterceptor;

    public WebMvcConfig(AuthorizationInterceptor authorizationInterceptor) {
        this.authorizationInterceptor = authorizationInterceptor;
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:4200")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
//            }
//        };
//    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/api/v1/**") // Specify URL patterns to apply the interceptor
                .order(Ordered.HIGHEST_PRECEDENCE); // Set the order
    }


}
