package com.romanov.rksp.museum;

import com.romanov.rksp.museum.model.*;
import com.romanov.rksp.museum.service.AppUserService;
import com.romanov.rksp.museum.service.ExhibitService;
import com.romanov.rksp.museum.service.HallService;
import com.romanov.rksp.museum.service.ShowpieceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
	CommandLineRunner run(AppUserService appUserService,
						  ExhibitService exhibitService,
						  HallService hallService,
						  ShowpieceService showpieceService) {
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
			appUserService.addRoleToUser("Drive@Lorry", "MANAGER_ROLE");
			Exhibit exh = new Exhibit(
					null,
					"test",
					LocalDate.now(),
					LocalDate.now().plusYears(4),
					"test",
					"test_long",
					"/img/exh/shoes.webp",
					new ArrayList<>()
			);
			exhibitService.saveExhibit(exh);
			Hall hall = new Hall(
					null,
					1,
					"First test hall",
					"Lorem ipsum dolor sit amet, verear ocurreret democritum nam id, " +
							"lucilius moderatius eu vim. Probo malis accusamus te per, " +
							"falli altera tamquam eos ne. Quo eu melius corpora postulant, " +
							"eam ne feugiat legimus tacimates, pri in gubergren splendide. " +
							"Id dignissim tincidunt maiestatis has, oblique lobortis nec id, te sale iusto mediocrem sit. " +
							"Id duo debet viris.\n " +
							"Ius dicta voluptatibus eu, quem lobortis ea cum, unum viderer scriptorem at eam. " +
							"In nisl oporteat sea, et error sanctus concludaturque mel. " +
							"Ad meis saperet appetere sed, eum eruditi honestatis no, vis quem affert expetenda an. " +
							"Id soleat aliquip blandit pri, est ad essent.",
					"asdasdfasdfasdfasdfasdfasdfasdfasdfsa",
					new ArrayList<>(),
					null);
			hallService.saveHall(hall);
			hallService.saveHall(new Hall(
					null,
					2,
					"Second test hall",
					"Lorem ipsum dolor sit amet, id mel vocent inermis. Ut facer efficiantur mea, in albucius argumentum sed, eum ei suas solum complectitur. Quo integre nusquam ad, mea utinam bonorum propriae ei, vivendum persequeris eum eu. Error lobortis definitiones ad vis, in has facer scriptorem. Has utroque principes accommodare no.\n" +
							"\n" +
							"Eros.",
					"asdasdfasdfasdfasdfasdfasdfasdfasdfsa",
					new ArrayList<>(),
					null
			));
			hallService.saveHall(new Hall(
					null,
					3,
					"Third test hall",
					"Lorem ipsum dolor sit amet, verear ocurreret democritum nam id, " +
							"lucilius moderatius eu vim. Probo malis accusamus te per, " +
							"falli altera tamquam eos ne. Quo eu melius corpora postulant, " +
							"eam ne feugiat legimus tacimates, pri in gubergren splendide. " +
							"Id dignissim tincidunt maiestatis has, oblique lobortis nec id, te sale iusto mediocrem sit. " +
							"Id duo debet viris.\n " +
							"Ius dicta voluptatibus eu, quem lobortis ea cum, unum viderer scriptorem at eam. " +
							"In nisl oporteat sea, et error sanctus concludaturque mel. " +
							"Ad meis saperet appetere sed, eum eruditi honestatis no, vis quem affert expetenda an. " +
							"Id soleat aliquip blandit pri, est ad essent.",
					"asdasdfasdfasdfasdfasdfasdfasdfasdfsa",
					new ArrayList<>(),
					exh
			));
			showpieceService.saveShowpiece(new Showpiece(
					null,
					"Мумия древнего Египтянина",
					"Крайне интересная мумия, крайне древняя и крайне интересная. " +
							"Крайне интересная мумия, крайне древняя и крайне интересная.",
					"Длинное описание мумии",
					null,
					hall
			));
			showpieceService.saveShowpiece(new Showpiece(
					null,
					"Мумия древнего Самаритянина",
					"Крайне интересная мумия, крайне древняя и крайне интересная. " +
							"Крайне интересная мумия, крайне древняя и крайне интересная.",
					"Длинное описание мумии",
					null,
					null
			));
			showpieceService.saveShowpiece(new Showpiece(
					null,
					"Картина сумасшедшего художника",
					"Крайне интересная картина, крайне древняя и крайне интересная. " +
							"Крайне интересная Картина, крайне древняя и крайне интересная.",
					"Длинное описание Картины",
					null,
					null
			));
		};
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
