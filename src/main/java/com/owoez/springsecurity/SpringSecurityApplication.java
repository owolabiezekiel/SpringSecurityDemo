package com.owoez.springsecurity;

import com.owoez.springsecurity.domain.AppUser;
import com.owoez.springsecurity.domain.Role;
import com.owoez.springsecurity.service.interfaces.RoleService;
import com.owoez.springsecurity.service.interfaces.UserService;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class SpringSecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityApplication.class, args);
  }

  @Bean
  CommandLineRunner run(UserService userService, RoleService roleService){
    return args -> {
      roleService.saveRole(new Role(null, "ROLE_USER"));
      roleService.saveRole(new Role(null, "ROLE_MANAGER"));
      roleService.saveRole(new Role(null, "ROLE_ADMIN"));
      roleService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

      userService.saveUser(new AppUser(null, "Tobiloba Owolabi", "owoezekiel", "intheend1", new ArrayList<>()));
      userService.saveUser(new AppUser(null, "Boluwatife Owolabi", "bolue", "tailor", new ArrayList<>()));
      userService.saveUser(new AppUser(null, "Daramola Dolapo", "deehair", "scholar", new ArrayList<>()));
      userService.saveUser(new AppUser(null, "Isaiah Owolabi", "isaiah", "hacey", new ArrayList<>()));

      userService.addRoleToUser("owoezekiel", "ROLE_MANAGER");
      userService.addRoleToUser("owoezekiel", "ROLE_USER");
      userService.addRoleToUser("owoezekiel", "ROLE_SUPER_ADMIN");
      userService.addRoleToUser("bolue", "ROLE_MANAGER");
      userService.addRoleToUser("deehair", "ROLE_USER");
      userService.addRoleToUser("isaiah", "ROLE_ADMIN");
    };
  }

}
