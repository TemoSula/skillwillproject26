package com.example.skillwillproject26.Filter;

import com.example.skillwillproject26.Models.UserModel;
import com.example.skillwillproject26.Services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Qualifier("customAuth")
public class CustomAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String Authheader = request.getHeader("Authorization");
        String token = "";
        if(Authheader==null)
        {
            filterChain.doFilter(request,response);
            return;
        }
        token = Authheader;
        if(Authheader.startsWith("Bearer ")) {
            token = Authheader.substring(7);
        }
        if(userService.isExpired(token))
        {
            filterChain.doFilter(request,response);
        }
         Authentication auth = userService.authentication(token);
        //if(SecurityContextHolder.getContext().getAuthentication() == null)
        //{
            SecurityContextHolder.getContext().setAuthentication(auth);
        //}

     filterChain.doFilter(request,response);
    }
}
