package com.romanov.rksp.museum;

import com.romanov.rksp.museum.model.AppUser;
import com.romanov.rksp.museum.model.Role;
import com.romanov.rksp.museum.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
public class MuseumApplication {

	public static String IMAGE_DIR;
	public static String IMAGE_EXH_DIR;
	public static String IMAGE_SHWP_DIR;

	public static void main(String[] args) throws IOException {
		IMAGE_DIR = new File(".").getCanonicalPath() + File.separator + "img" + File.separator;
		IMAGE_EXH_DIR = IMAGE_DIR + "exh/";
		IMAGE_SHWP_DIR = IMAGE_DIR + "shwp/";
		File exhImgFile = new File(IMAGE_EXH_DIR), shwpImgFile = new File(IMAGE_SHWP_DIR);
		if (!exhImgFile.exists()) {
			exhImgFile.mkdirs();
		}
		if (!shwpImgFile.exists()) {
			shwpImgFile.mkdirs();
		}
		SpringApplication.run(MuseumApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AppUserService appUserService) {
		return args -> {
			appUserService.saveRole(new Role(1L, "USER_ROLE"));
			appUserService.saveRole(new Role(2L, "MANAGER_ROLE"));
			appUserService.saveRole(new Role(3L, "ADMIN_ROLE"));
			appUserService.saveUser(new AppUser(1L, "ADMIN", null, null, "Admin@Museum", "2051",
					new ArrayList<>()));
			appUserService.addRoleToUser("Admin@Museum", "ADMIN_ROLE");
		};
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
