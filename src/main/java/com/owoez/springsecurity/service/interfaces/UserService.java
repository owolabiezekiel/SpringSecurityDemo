package com.owoez.springsecurity.service.interfaces;

import com.owoez.springsecurity.domain.AppUser;

import java.util.List;

/**
 * Created by tobilobaowolabi on 17/08/2021
 * Author: tobilobaowolabi
 * Date: 17/08/2021
 * Project: spring-security
 * IDE: IntelliJ IDEA
 **/
public interface UserService {
  AppUser saveUser(AppUser appUser);
  AppUser getUser(String username);
  void addRoleToUser(String username, String roleName);
  List<AppUser> getUsers();
}
