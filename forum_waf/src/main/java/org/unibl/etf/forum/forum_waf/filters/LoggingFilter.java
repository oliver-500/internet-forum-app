package org.unibl.etf.forum.forum_waf.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.unibl.etf.forum.forum_waf.DTO.UserRequestDTO;
import org.unibl.etf.forum.forum_waf.service.LoggingService;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@WebFilter("/*") // Specifies the URL pattern to which the filter should apply (in this case, all URLs)
public class LoggingFilter implements Filter {



    private final LoggingService loggingService;

    public LoggingFilter(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //filterChain.doFilter(servletRequest, servletResponse); // Continue the filter chain
        // Get the client's IP address
        String clientIpAddress = request.getRemoteAddr();




        UserRequestDTO requestBody = new UserRequestDTO();
        requestBody.setAddress(request.getRemoteAddr());
        requestBody.setUrl(request.getRequestURI());





        loggingService.logMessage(requestBody);



        filterChain.doFilter(servletRequest, servletResponse); // Continue the filter chain
    }



    @Override
    public void destroy() {
        // Cleanup code
    }
}
