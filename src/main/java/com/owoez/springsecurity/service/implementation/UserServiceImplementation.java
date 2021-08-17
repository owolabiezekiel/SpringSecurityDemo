package com.owoez.springsecurity.service.implementation;

import com.owoez.springsecurity.domain.AppUser;
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

  @Override
  public AppUser saveUser(AppUser appUser) {
    return null;
  }

  @Override
  public AppUser getUser(String username) {
    return null;
  }

  @Override
  public void addRoleToUser(String username, String roleName) {

  }

  @Override
  public List<AppUser> getUsers() {
    return null;
  }
}
