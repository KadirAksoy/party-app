package com.kadiraksoy.partyapp;


import com.kadiraksoy.partyapp.model.user.Role;
import com.kadiraksoy.partyapp.model.user.User;
import com.kadiraksoy.partyapp.repository.UserRepository;
import com.kadiraksoy.partyapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.kadiraksoy.partyapp.repository")
public class PartyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartyAppApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("*")
						.allowCredentials(true)
						.allowedOriginPatterns("*");

			}
		};
	}



//	@Bean
//	CommandLineRunner runner(UserRepository userRepository, PasswordEncoder passwordEncoder){
//		return args -> {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			Date birthdayDate = dateFormat.parse("1990-05-20");
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
//					.email("super@admin.com")
//					.password(passwordEncoder.encode("password"))
//					.birthdayDate(birthdayDate)
//					.role(Role.ROLE_SUPERADMIN)
//					.active(true)
//					.build();
//
//			userRepository.save(user);
//			userRepository.save(admin);
////			userRepository.save(superAdmin);
//		};
//	}

}
