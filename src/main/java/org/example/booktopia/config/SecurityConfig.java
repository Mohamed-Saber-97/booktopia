package org.example.booktopia.config;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.service.AdminService;
import org.example.booktopia.service.BuyerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;
    private final BuyerService buyerService;
    private final AdminCustomAuthenticationSuccessHandler adminCustomAuthenticationSuccessHandler;
    private final BuyerCustomAuthenticationSuccessHandler buyerCustomAuthenticationSuccessHandler;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/admins/**")
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> requests.requestMatchers("/admins/add-category")
                                                       .hasRole("ADMIN")
                                                       .anyRequest()
                                                       .permitAll())
            .formLogin(form -> form
                    .loginPage("/admins/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/admins/login")
                    .defaultSuccessUrl("/", true)
                    .successHandler(adminCustomAuthenticationSuccessHandler)
                    .failureUrl("/admins/login?error=true") // Include failure URL
                    .permitAll())
            .logout(logout -> logout.logoutUrl("/logout")
                                    .logoutSuccessUrl("/")
                                    .invalidateHttpSession(true)
                                    .deleteCookies("JSESSIONID"))
            .authenticationProvider(adminAuthenticationProvider())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public SecurityFilterChain buyerSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/buyers/**")
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> requests.requestMatchers("/buyers/checkout")
                                                       .hasRole("BUYER")
                                                       .anyRequest()
                                                       .permitAll())
            .formLogin(form -> form
                    .loginPage("/buyers/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/buyers/login")
                    .defaultSuccessUrl("/", true)
                    .successHandler(buyerCustomAuthenticationSuccessHandler)
                    .failureUrl("/buyers/login?error=true") // Include failure URL
                    .permitAll())
            .logout(logout -> logout.logoutUrl("/logout")
                                    .logoutSuccessUrl("/")
                                    .invalidateHttpSession(true)
                                    .deleteCookies("JSESSIONID"))
            .authenticationProvider(buyerAuthenticationProvider())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider buyerAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(buyerService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}
