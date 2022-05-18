package com.romanov.rksp.museum;

import com.romanov.rksp.museum.model.AppUser;
import com.romanov.rksp.museum.model.Role;
import com.romanov.rksp.museum.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class MuseumApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuseumApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	//needed for the SecurityConfig class
	@Bean
	CommandLineRunner run(AppUserService appUserService) {
		return args -> {
			appUserService.saveRole(new Role(null, "USER_ROLE"));
			appUserService.saveRole(new Role(null, "MANAGER_ROLE"));
			appUserService.saveRole(new Role(null, "ADMIN_ROLE"));
			appUserService.saveUser(new AppUser(null, "Adam Smith", "Adam@Smith", "passwd",
					new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Granny Smith", "Apple@Green", "passwd",
					new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Lorry Driver", "Drive@Lorry", "passwd",
					new ArrayList<>()));
			appUserService.addRoleToUser("Adam@Smith", "USER_ROLE");
			appUserService.addRoleToUser("Apple@Green", "MANAGER_ROLE");
			appUserService.addRoleToUser("Drive@Lorry", "ADMIN_ROLE");
		};
	}
}
