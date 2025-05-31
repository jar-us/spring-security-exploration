package jar.us.onceperrequestfilterdemo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class MyOncePerRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        log.info("MyOncePerRequestFilter - Before processing request for: {}", request.getRequestURI());
        
        // You can add custom logic here, such as:
        // - Request validation
        // - Authentication/Authorization checks
        // - Request modification
        // - Logging
        
        // Continue the filter chain
        filterChain.doFilter(request, response);
        
        log.info("MyOncePerRequestFilter - After processing request for: {}", request.getRequestURI());
        
        // You can add post-processing logic here, such as:
        // - Response modification
        // - Metrics collection
        // - Logging
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // This method determines whether this filter should not be applied to a specific request
        // Return true to skip this filter for certain requests
        
        // Example: Skip this filter for static resources
        String path = request.getRequestURI();
        return path.startsWith("/public");
    }
}