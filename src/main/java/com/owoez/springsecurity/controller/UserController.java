package com.owoez.springsecurity.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owoez.springsecurity.domain.AppUser;
import com.owoez.springsecurity.domain.Role;
import com.owoez.springsecurity.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * Created by tobilobaowolabi on 17/08/2021
 * Author: tobilobaowolabi
 * Date: 17/08/2021
 * Project: spring-security
 * IDE: IntelliJ IDEA
 **/
@RestController @RequestMapping("/user") @RequiredArgsConstructor @Slf4j
public class UserController {
  private final UserService userService;

  @GetMapping("/get-users")
  public ResponseEntity<List<AppUser>> getUsers() {
    return ResponseEntity.ok().body(userService.getUsers());
  }

  @PostMapping("/save-user")
  public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save-user").toUriString());
    return ResponseEntity.created(uri).body(userService.saveUser(appUser));
  }

  @PostMapping("/add-role")
  public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUser roleToUser) {
    userService.addRoleToUser(roleToUser.getUsername(), roleToUser.getRoleName());
    return ResponseEntity.ok().build();
  }

  @GetMapping("/refresh-token")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String authorizationHeader = request.getHeader(AUTHORIZATION);
    if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
      try{
        String refresh_token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
        String username = decodedJWT.getSubject();
        AppUser user = userService.getUser(username);
        String access_token = JWT.create()
            .withSubject(user.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 10 * 1000))
            .withIssuer(request.getRequestURL().toString())
            .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
            .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
      }catch(Exception e){
        log.error("Error logging in: {}", e.getMessage());
        response.setHeader("error", e.getMessage());
        response.setStatus(FORBIDDEN.value());
        //response.sendError(FORBIDDEN.value());
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
      }
    }else{
      throw new RuntimeException("Refresh token is missing");
    }
  }
}

@Data
class RoleToUser{
  private String username;
  private String roleName;
}
