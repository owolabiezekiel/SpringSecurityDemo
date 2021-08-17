package com.owoez.springsecurity.service.implementation;

import com.owoez.springsecurity.domain.AppUser;
import com.owoez.springsecurity.domain.Role;
import com.owoez.springsecurity.repository.RoleRepository;
import com.owoez.springsecurity.repository.UserRepository;
import com.owoez.springsecurity.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tobilobaowolabi on 17/08/2021
 * Author: tobilobaowolabi
 * Date: 17/08/2021
 * Project: spring-security
 * IDE: IntelliJ IDEA
 **/
@Service @AllArgsConstructor @Transactional @Slf4j
public class UserServiceImplementation implements UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public AppUser saveUser(AppUser appUser) {
    log.info("Saving new user {} to database", appUser.getName());
    return userRepository.save(appUser);
  }

  @Override
  public AppUser getUser(String username) {
    log.info("Getting new user {} from database", username);
    return userRepository.findByUsername(username);
  }

  @Override
  public void addRoleToUser(String username, String roleName) {
    log.info("Adding new role {} to user", roleName, username);
    AppUser user = userRepository.findByUsername(username);
    Role role = roleRepository.findByName(roleName);
    user.getRoles().add(role);
  }

  @Override
  public List<AppUser> getUsers() {
    log.info("Getting all users from the database");
    return userRepository.findAll();
  }
}
