package jar.us.onceperrequestfilterdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import java.util.List;

@Configuration
@Slf4j
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

        // Note: We don't need to explicitly add the MyOncePerRequestFilter here
        // because it's already registered as a @Component and will be automatically
        // added to the filter chain by Spring Boot.
        
        return http.build();
    }

    @Bean
    public CommandLineRunner filterChainLogger(FilterChainProxy filterChainProxy) {
        return args -> {
            log.info("--- Spring Security Filter Chain Order ---");
            filterChainProxy.getFilterChains().forEach(chain -> {
                log.info("  Filter Chain for Path: {}", chain.getFilters().toString());
                List<Filter> filters = chain.getFilters();
                for (int i = 0; i < filters.size(); i++) {
                    Filter filter = filters.get(i);
                    String filterName = filter.getClass().getSimpleName();
                    
                    // Highlight OncePerRequestFilter implementations
                    if (filter instanceof OncePerRequestFilter) {
                        log.info("    {}. {} (OncePerRequestFilter)", (i + 1), filterName);
                    } else {
                        log.info("    {}. {}", (i + 1), filterName);
                    }
                }
            });
            log.info("------------------------------------------");
        };
    }
}