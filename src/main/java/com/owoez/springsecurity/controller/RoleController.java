package com.owoez.springsecurity.controller;

import com.owoez.springsecurity.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
