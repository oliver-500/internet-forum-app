package org.unibl.etf.forum.forum_access_controller.config;

//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;

import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.unibl.etf.forum.forum_access_controller.converters.HtmlMessageConverter;
import org.unibl.etf.forum.forum_access_controller.interceptors.CookieAddingInterceptor;
import org.unibl.etf.forum.forum_access_controller.interceptors.ResponseHeaderInterceptor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.security.KeyStore;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class AppConfig {

    class CustomClientHttpRequestFactory extends SimpleClientHttpRequestFactory {
        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            super.prepareConnection(connection, httpMethod);
            connection.setInstanceFollowRedirects(false);
        }
    }


    @Bean
    public RestTemplate restTemplate(HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.getInterceptors().add(new ResponseHeaderInterceptor());

      restTemplate.setRequestFactory(new CustomClientHttpRequestFactory());

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new CookieAddingInterceptor(request));

        restTemplate.setInterceptors(interceptors);


       // List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
       // messageConverters.add(new HtmlMessageConverter());
       // restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }






}
