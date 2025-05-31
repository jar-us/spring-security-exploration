package jar.us.securityorderingdemo;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // Mark as a Spring component to be picked up by Spring Boot
@Order(2) // Assign an order value (lower values have higher precedence)
@Slf4j
public class MyOrderedFilterA implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.info("MyOrderedFilterA (Order 2) - Before processing request for: {}", httpRequest.getRequestURI());
        chain.doFilter(request, response);
        log.info("MyOrderedFilterA (Order 2) - After processing request for: {}", httpRequest.getRequestURI());
    }
}