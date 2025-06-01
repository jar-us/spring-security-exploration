package jar.us.databaseexplorationdemo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Define how Spring Security handles HTTP requests
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/home").permitAll() // Allow access to home and root for everyone
                        .requestMatchers("/admin").hasRole("ADMIN") // Only users with 'ADMIN' role can access /admin
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page
                        .permitAll() // Allow everyone to access the login page
                )
                .logout(LogoutConfigurer::permitAll // Allow everyone to logout
                );
        return http.build();
    }

    // Define how to load user details from the database
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .disabled(!user.isEnabled()) // Use user.isEnabled() from our entity
//                    .roles(user.getAuthorities().stream().map(auth -> auth.replace("ROLE_", "")).collect(Collectors.joining(","))) // Extract just the role name (e.g., USER, ADMIN)
                    .roles(user.getAuthorities().stream()
                            .map(auth -> auth.replace("ROLE_", ""))
                            .toArray(String[]::new))
                    .build();
        };
    }

    // Define the password encoder (MUST be BCrypt to match our data.sql)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}