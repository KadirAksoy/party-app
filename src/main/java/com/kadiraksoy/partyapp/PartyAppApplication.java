package com.kadiraksoy.partyapp;


import com.kadiraksoy.partyapp.model.user.Role;
import com.kadiraksoy.partyapp.model.user.User;
import com.kadiraksoy.partyapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.kadiraksoy.partyapp.repository")
public class PartyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartyAppApplication.class, args);
	}


//	@Bean
//	CommandLineRunner runner(UserService userService, PasswordEncoder passwordEncoder){
//		return args -> {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			Date birthdayDate = dateFormat.parse("1990-05-20");
//
//
//			User user = User
//					.builder()
//					.firstName("user")
//					.lastName("user")
//					.email("user@user.com")
//					.password(passwordEncoder.encode("password"))
//					.birthdayDate(birthdayDate)
//					.role(Role.ROLE_USER)
//					.active(true)
//					.build();
//
//			User admin = User
//					.builder()
//					.firstName("admin")
//					.lastName("admin")
//					.email("admin@admin.com")
//					.password(passwordEncoder.encode("password"))
//					.birthdayDate(birthdayDate)
//					.role(Role.ROLE_ADMIN)
//					.active(true)
//					.build();
//
//			User superAdmin = User
//					.builder()
//					.firstName("super admin")
//					.lastName("super admin")
//					.email("superadmin@admin.com")
//					.password(passwordEncoder.encode("password"))
//					.birthdayDate(birthdayDate)
//					.role(Role.ROLE_SUPERADMIN)
//					.active(true)
//					.build();
//
//			userService.save(user);
//			userService.save(admin);
//			userService.save(superAdmin);
//
//
//		};
//	}



}
