package com.example.projectswp.config;

import com.example.projectswp.repositories.UserAccountRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    UserAccountRepository userAccountRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            try {
//                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(authorizationHeader);
//                String uid = decodedToken.getUid();
//                UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
                UserRecord userRecord = FirebaseAuth.getInstance().getUser(authorizationHeader);
                if (userRecord != null) {
                    String userCode = userRecord.getUid();
                    int userId = userAccountRepository.getUserAccountId(userCode);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userCode, userId);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (FirebaseAuthException e) {

            }
        }
        filterChain.doFilter(request, response);
    }
}
