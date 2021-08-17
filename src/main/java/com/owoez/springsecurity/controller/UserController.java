package com.owoez.springsecurity.controller;

import com.owoez.springsecurity.domain.AppUser;
import com.owoez.springsecurity.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by tobilobaowolabi on 17/08/2021
 * Author: tobilobaowolabi
 * Date: 17/08/2021
 * Project: spring-security
 * IDE: IntelliJ IDEA
 **/
@RestController @RequestMapping("/user") @RequiredArgsConstructor
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
}

@Data
class RoleToUser{
  private String username;
  private String roleName;
}
