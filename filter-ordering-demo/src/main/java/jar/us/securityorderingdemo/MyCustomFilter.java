package jar.us.securityorderingdemo;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j; // Using Lombok for logging

import java.io.IOException;

@Slf4j // Lombok annotation for logger
public class MyCustomFilter implements Filter {

    private final String name;

    public MyCustomFilter(String name) {
        this.name = name;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.info("MyCustomFilter '{}' - Before processing request for: {}", name, httpRequest.getRequestURI());
        chain.doFilter(request, response); // Pass the request to the next filter in the chain
        log.info("MyCustomFilter '{}' - After processing request for: {}", name, httpRequest.getRequestURI());
    }
}