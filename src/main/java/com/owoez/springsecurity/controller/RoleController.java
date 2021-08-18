package com.owoez.springsecurity.controller;

import com.owoez.springsecurity.domain.Role;
import com.owoez.springsecurity.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Created by tobilobaowolabi on 17/08/2021
 * Author: tobilobaowolabi
 * Date: 17/08/2021
 * Project: spring-security
 * IDE: IntelliJ IDEA
 **/
@RestController
@RequestMapping("/role") @RequiredArgsConstructor
public class RoleController {
  private final RoleService roleService;

  @PostMapping("/save-role")
  public ResponseEntity<Role> saveRole(@RequestBody Role role) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save-role").toUriString());
    return ResponseEntity.created(uri).body(roleService.saveRole(role));
  }
}
