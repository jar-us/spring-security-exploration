package jar.us.onceperrequestfilterdemo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Make it a Spring component so it gets detected and registered
public class MyCustomOncePerRequestFilter extends OncePerRequestFilter {

    private int executionCount = 0;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        executionCount++;
        System.out.println("MyCustomOncePerRequestFilter is executing for request URI: " + request.getRequestURI() +
                " (Execution Count: " + executionCount + ")");

        // This is where the filter's logic would go.
        // For demonstration, we just log.

        filterChain.doFilter(request, response); // Continue the filter chain
    }

    // You can optionally override shouldNotFilter if you want to exclude certain paths
    // For this example, we want it to apply to all relevant paths
    // @Override
    // protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    //     return request.getRequestURI().startsWith("/exclude");
    // }

    // This method is the key to OncePerRequestFilter's default behavior.
    // By default, it generates a unique attribute name based on the filter's class.
    // If an attribute with this name is present in the request, the filter won't run again.
    @Override
    protected String getAlreadyFilteredAttributeName() {
        // We'll explicitly return the default value for clarity.
        // You generally don't need to override this unless you have a very specific reason.
        return super.getAlreadyFilteredAttributeName();
    }
}