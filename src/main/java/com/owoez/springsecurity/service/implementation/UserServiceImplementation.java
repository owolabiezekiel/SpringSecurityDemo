package com.owoez.springsecurity.service.implementation;

import com.owoez.springsecurity.domain.AppUser;
import com.owoez.springsecurity.domain.Role;
import com.owoez.springsecurity.repository.RoleRepository;
import com.owoez.springsecurity.repository.UserRepository;
import com.owoez.springsecurity.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by tobilobaowolabi on 17/08/2021
 * Author: tobilobaowolabi
 * Date: 17/08/2021
 * Project: spring-security
 * IDE: IntelliJ IDEA
 **/
@Service @AllArgsConstructor @Transactional @Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser = userRepository.findByUsername(username);
    if(appUser == null){
      log.error("User not found in the database");
      throw new UsernameNotFoundException("User not found in the database");
    }else{
      log.info("User found in the database: {}", username);
    }
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    appUser.getRoles().forEach(role -> {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    });
    return new User(appUser.getUsername(), appUser.getPassword(), authorities);
  }

  @Override
  public AppUser saveUser(AppUser appUser) {
    log.info("Saving new user {} to database", appUser.getName());
    appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
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
