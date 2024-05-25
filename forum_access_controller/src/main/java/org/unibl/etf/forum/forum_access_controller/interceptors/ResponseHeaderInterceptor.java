package org.unibl.etf.forum.forum_access_controller.interceptors;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class ResponseHeaderInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        printResponseHeaders(response);
        return response;
    }

    private void printResponseHeaders(ClientHttpResponse response) throws IOException {
        System.out.println("Response Headers:");
        response.getHeaders().forEach((header, values) ->
                values.forEach(value -> System.out.println(header + ": " + value)));
    }
}
