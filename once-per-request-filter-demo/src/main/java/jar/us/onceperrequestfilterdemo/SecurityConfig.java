package jar.us.onceperrequestfilterdemo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/forwarded").permitAll() // Allow access to the forwarded page
                        .requestMatchers("/").permitAll() // Allow access to the home page
                        .anyRequest().authenticated() // All other requests need authentication (default Spring Security login)
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll // Allow everyone to see the login page
                );
        return http.build();
    }
}