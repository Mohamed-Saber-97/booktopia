package org.example.booktopia.config;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.service.AdminService;
import org.example.booktopia.service.BuyerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;
    private final BuyerService buyerService;
    private final AdminCustomAuthenticationSuccessHandler adminCustomAuthenticationSuccessHandler;
    private final AdminCustomAuthenticationFailureHandler adminCustomAuthenticationFailureHandler;
    private final BuyerCustomAuthenticationSuccessHandler buyerCustomAuthenticationSuccessHandler;
    private final BuyerCustomAuthenticationFailureHandler buyerCustomAuthenticationFailureHandler;

    @Bean
    public AuthenticationManager authenticationManager(
            List<AuthenticationProvider> myAuthenticationProviders) throws Exception {
        return new ProviderManager(List.of(adminAuthenticationProvider(), buyerAuthenticationProvider()));
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/admins/**")
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> requests
                                           .requestMatchers("/admins/**")
                                           .hasRole("ADMIN")
                                           .anyRequest()
                                           .permitAll()
                                  )
            .formLogin(form -> form
                    .loginPage("/admins/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/admins/login")
                    .failureHandler(adminCustomAuthenticationFailureHandler)
                    .successHandler(adminCustomAuthenticationSuccessHandler)
                    .permitAll())
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID"))
            .authenticationProvider(adminAuthenticationProvider());
        return http.build();
    }

    @Bean
    public SecurityFilterChain buyerSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/buyers/**")
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> requests.requestMatchers("/buyers/signup").permitAll().requestMatchers("/buyers/**")
                                                       .hasRole("BUYER")
                                                       .anyRequest()
                                                       .permitAll())
            .formLogin(form -> form
                    .loginPage("/buyers/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/buyers/login")
                    .failureHandler(buyerCustomAuthenticationFailureHandler)
                    .successHandler(buyerCustomAuthenticationSuccessHandler)
//                    .failureUrl("/buyers/login?error=true") // Include failure URL
                    .permitAll())
            .logout(logout -> logout.logoutUrl("/logout")
                                    .logoutSuccessUrl("/")
                                    .invalidateHttpSession(true)
                                    .deleteCookies("JSESSIONID"))
            .authenticationProvider(buyerAuthenticationProvider());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminService);
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider buyerAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(buyerService);
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }
}
