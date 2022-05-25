package com.romanov.rksp.museum;

import com.romanov.rksp.museum.model.AppUser;
import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Role;
import com.romanov.rksp.museum.service.AppUserService;
import com.romanov.rksp.museum.service.ExhibitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class MuseumApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuseumApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AppUserService appUserService, ExhibitService exhibitService) {
		return args -> {
			appUserService.saveRole(new Role(null, "USER_ROLE"));
			appUserService.saveRole(new Role(null, "MANAGER_ROLE"));
			appUserService.saveRole(new Role(null, "ADMIN_ROLE"));
			appUserService.saveUser(new AppUser(null, "Adam", "Smith", null, "Adam@Smith", "passwd",
					new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Granny", "Smith", null, "Apple@Green", "passwd",
					new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Lorry", "Driver", null, "Drive@Lorry", "passwd",
					new ArrayList<>()));
			appUserService.addRoleToUser("Adam@Smith", "USER_ROLE");
			appUserService.addRoleToUser("Apple@Green", "MANAGER_ROLE");
			appUserService.addRoleToUser("Drive@Lorry", "ADMIN_ROLE");
			exhibitService.saveExhibit(new Exhibit(
					null,
					"test",
					LocalDate.now(),
					null,
					"test",
					"test_long",
					"/content/images/exh/figurine.webp",
					new ArrayList<>()
					));
		};
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
