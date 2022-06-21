package com.vadmack.authserver.security;

import com.vadmack.authserver.domain.entity.Role;
import com.vadmack.authserver.domain.entity.User;
import com.vadmack.authserver.domain.entity.UserType;
import com.vadmack.authserver.repository.UserRepository;
import com.vadmack.authserver.util.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilter jwtTokenFilter;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();

        http.authorizeRequests()
                .antMatchers("/api/public/auth").permitAll()
                .antMatchers("/api/public/refresh-token").permitAll()
                .antMatchers("/api/public/registration").permitAll()
                .antMatchers("/api/public/registration-confirm").permitAll()
                .antMatchers("/api/public/reset-password").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/**").hasAuthority(Role.ROLE_ADMIN)
                .antMatchers(HttpMethod.POST, "/api/users/**").hasAuthority(Role.ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority(Role.ROLE_ADMIN)
                .antMatchers(HttpMethod.PUT, "/api/users/**")
                .hasAnyAuthority(Role.ROLE_ADMIN, Role.ROLE_USER)
                .antMatchers("/api/public/oauth2/github").authenticated()
                .and()
                .oauth2Login();
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint());

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest>
    authorizationRequestRepository() {

        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.userDetailsService(username -> {
            if (userRepository.findAll().isEmpty()) {
                User user = new User(
                        sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME),
                        "user",
                        passwordEncoder().encode("user"),
                        null,
                        Set.of(new Role(Role.ROLE_USER)),
                        true,
                        UserType.PASSWORD
                );
                User admin = new User(
                        sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME),
                        "admin",
                        passwordEncoder().encode("admin"),
                        null,
                        Set.of(new Role(Role.ROLE_ADMIN)),
                        true,
                        UserType.PASSWORD
                );
                userRepository.saveAll(List.of(user, admin));
            }
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("User: %s, not found", username)));
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
