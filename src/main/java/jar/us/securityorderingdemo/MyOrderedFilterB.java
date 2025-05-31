package jar.us.securityorderingdemo;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MyOrderedFilterB implements Filter, Ordered { // Implement Ordered interface!

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.info("MyOrderedFilterB (Order 1) - Before processing request for: {}", httpRequest.getRequestURI());
        chain.doFilter(request, response);
        log.info("MyOrderedFilterB (Order 1) - After processing request for: {}", httpRequest.getRequestURI());
    }

    @Override
    public int getOrder() {
        return 1; // Return an order value (lower values have higher precedence)
    }
}