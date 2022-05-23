package com.vas.travelapp.config.security;

import com.vas.travelapp.domain.user.Role;
import com.vas.travelapp.domain.user.User;
import com.vas.travelapp.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.user:user}")
    private String user;
    @Value("${security.admin:admin}")
    private String admin;

    private final JwtTokenFilter jwtTokenFilter;
    private final UserRepository userRepository;

    @Override
    @SuppressWarnings({"java:S1117", "java:S5344"})
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(username -> {
            if (userRepository.findAll().isEmpty()) {
                User user = new User(
                        this.user,
                        passwordEncoder().encode(this.user),
                        Set.of(new Role(Role.ROLE_USER)),
                        true
                );
                User admin = new User(
                        this.admin,
                        passwordEncoder().encode(this.admin),
                        Set.of(new Role(Role.ROLE_ADMIN)),
                        true
                );
                userRepository.saveAll(List.of(user, admin));
            }
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("User: %s, not found", username)));
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage())
                )
                .and();

        http.authorizeRequests()
                .antMatchers("/api/public/**").permitAll();

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
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
        config.addAllowedOrigin("*");
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
