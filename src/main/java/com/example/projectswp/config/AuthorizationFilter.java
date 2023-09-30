package com.example.projectswp.config;

import com.example.projectswp.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    UserAccountRepository userAccountRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            int role = userAccountRepository.getUserAccountRole(authentication.getPrincipal().toString());
                if (role == 1) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                } else if (role == 2){
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }
            Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authorities);
            SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
        }
        filterChain.doFilter(request, response);
    }
}
