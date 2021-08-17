package com.owoez.springsecurity.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owoez.springsecurity.domain.AppUser;
import jdk.jfr.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tobilobaowolabi on 17/08/2021
 * Author: tobilobaowolabi
 * Date: 17/08/2021
 * Project: spring-security
 * IDE: IntelliJ IDEA
 **/

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    log.info("Username is: {} and password is: {}", username, password);
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
    User appUser = (User) authentication.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
    String access_token = JWT.create()
        .withSubject(appUser.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 10 * 1000))
        .withIssuer(request.getRequestURL().toString())
        .withClaim("roles", appUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .sign(algorithm);

    String refresh_token = JWT.create()
        .withSubject(appUser.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 10 * 1000))
        .withIssuer(request.getRequestURL().toString())
        .sign(algorithm);

    response.setHeader("access_token", access_token);
    response.setHeader("refresh_token", refresh_token);

    Map<String, String> tokens = new HashMap<>();
    tokens.put("access_token", access_token);
    tokens.put("refresh_token", refresh_token);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
  }
}
