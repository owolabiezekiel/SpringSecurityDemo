package com.owoez.springsecurity.service.implementation;

import com.owoez.springsecurity.domain.Role;
import com.owoez.springsecurity.repository.RoleRepository;
import com.owoez.springsecurity.service.interfaces.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tobilobaowolabi on 17/08/2021
 * Author: tobilobaowolabi
 * Date: 17/08/2021
 * Project: spring-security
 * IDE: IntelliJ IDEA
 **/

@Service @AllArgsConstructor @Transactional @Slf4j
public class RoleServiceImplementation implements RoleService {
  private final RoleRepository roleRepository;

  @Override
  public Role saveRole(Role role) {
    log.info("Saving new role {} to database", role.getName());
    return roleRepository.save(role);
  }
}
