package com.example.projectswp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConFig extends WebSecurityConfigurerAdapter {
    @Autowired
    private FirebaseAuthenticationFilter firebaseAuthenticationFilter;
    private static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    @Autowired
    private AuthorizationFilter authorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(firebaseAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/item/**", "/api/category/**", "/api/subCategory/**", "/api/blog/**", "/api/category-sub/**", "/api/blogcategory/**", "/api/comment/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/createUser").permitAll()
                .antMatchers(AUTH_WHITE_LIST).permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .cors();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Cho phép truy cập từ tất cả các nguồn
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Cho phép các phương thức
        configuration.setAllowedHeaders(Arrays.asList("*")); // Cho phép tất cả các header
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
