package com.owoez.springsecurity.controller;

import com.owoez.springsecurity.domain.AppUser;
import com.owoez.springsecurity.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
