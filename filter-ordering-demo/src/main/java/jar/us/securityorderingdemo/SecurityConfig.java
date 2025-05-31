package jar.us.securityorderingdemo;


import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@Slf4j // Add this annotation for logging
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll
                );

        // Filter 1: Placed before UsernamePasswordAuthenticationFilter (very early in the chain)
        http.addFilterBefore(new MyCustomFilter("Filter Before Auth"), UsernamePasswordAuthenticationFilter.class);

        // Filter 2: Placed at the position of AuthorizationFilter (after authentication, before resource access check)
        http.addFilterAt(new MyCustomFilter("Filter At AuthZ"), AuthorizationFilter.class);

        // Filter 3: Placed after AuthorizationFilter (very late in the chain, after authz)
        http.addFilterAfter(new MyCustomFilter("Filter After AuthZ"), AuthorizationFilter.class);

        return http.build();
    }

    // --- NEW CODE FOR LOGGING FILTERS ---
    @Bean
    public CommandLineRunner filterChainLogger(FilterChainProxy filterChainProxy) {
        return args -> {
            log.info("--- Spring Security Filter Chain Order ---");
            // Get the list of security filter chains (usually just one for most apps)
            filterChainProxy.getFilterChains().forEach(chain -> {
                log.info("  Filter Chain for Path: {}", chain.getFilters().toString()); // Or similar, depending on the matcher
                List<Filter> filters = chain.getFilters();
                for (int i = 0; i < filters.size(); i++) {
                    Filter filter = filters.get(i);
                    log.info("    {}. {}", (i + 1), filter.getClass().getSimpleName());
                }
            });
            log.info("------------------------------------------");
        };
    }
    // --- END NEW CODE ---

/*

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll
                );

        // Add our custom filter to the chain
        // By default, it will be added at the end of the chain if no order is specified
        // Or, Spring might try to guess its position if it implements Ordered, etc.
        // For simple Filter beans, it often goes at the very beginning or end
        // depending on how it's registered. Let's register it before a known filter.
        http.addFilterBefore(new MyCustomFilter("Defaultly Placed Filter"), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // We don't register it as a top-level bean here to control its placement within SecurityFilterChain
    // If you just declare @Bean MyCustomFilter(), Spring Boot might try to register it
    // globally which is not what we want for Spring Security filter chain ordering demo.
    // We want to add it explicitly to the SecurityFilterChain.

*/
}