package com.owoez.springsecurity.repository;

import com.owoez.springsecurity.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tobilobaowolabi on 17/08/2021
 * Author: tobilobaowolabi
 * Date: 17/08/2021
 * Project: spring-security
 * IDE: IntelliJ IDEA
 **/
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByName(String name);
}
