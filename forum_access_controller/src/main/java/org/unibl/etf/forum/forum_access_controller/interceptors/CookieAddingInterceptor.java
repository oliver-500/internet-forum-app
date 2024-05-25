package org.unibl.etf.forum.forum_access_controller.interceptors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class CookieAddingInterceptor implements ClientHttpRequestInterceptor {

    private final HttpServletRequest httpServletRequest;

    public CookieAddingInterceptor(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // Extract cookies from HttpServletRequest
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                System.out.println("Prosljedjujem cookie pod nazivom: " + cookie.getName() + " sa vrijednoscu: " + cookie.getValue());
                request.getHeaders().add("Cookie", cookie.getName() + "=" + cookie.getValue());
            }
        }

        // Continue with the request
        return execution.execute(request, body);
    }
}
