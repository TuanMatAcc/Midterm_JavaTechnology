package com.example.SpringWeb.Security;

import com.example.SpringWeb.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("user1")
                .password(passwordEncoder().encode("user1Pass"))
                .roles("USER")
                .build();
        UserDetails user2 = User.withUsername("user2")
                .password(passwordEncoder().encode("user2Pass"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("adminPass"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, user2, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (for simplicity; enable in production)
                .csrf(csrf -> csrf.disable())

                // Define authorization rules for different endpoints
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/register", "/login").permitAll()  // Allow registration and login pages for everyone
                        .requestMatchers(
                                "/products/management/**",
                                "/category/management/**",
                                "/brand/management/**",
                                "/order/management/**",
                                "/admin"
                        ).hasAuthority("ADMIN")  // Only ADMIN role can access these URLs
                        .anyRequest().permitAll()  // All other requests require authentication (permit all for now)
                )

                // Configure form-based login
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")  // Custom login page
                        .loginProcessingUrl("/perform_login")  // URL to process the login
                        .successHandler((request, response, authentication) -> {
                            // Role-based redirection logic
                            if (authentication.getAuthorities().stream()
                                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
                                response.sendRedirect("/admin");  // Redirect to /admin for ADMIN role
                            } else {
                                System.out.println("User role: " + authentication.getAuthorities());
                                response.sendRedirect("/");  // Redirect to home page for others
                            }
                        })
                        .permitAll()  // Allow all users to access the login page
                )

                // Configure logout functionality
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL to trigger logout
                        .logoutSuccessUrl("/login?logout")  // Redirect to login page after logout
                        .permitAll()  // Allow all users to access the logout URL
                )

                // Set custom authentication provider
                .authenticationProvider(authenticationProvider());

        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
