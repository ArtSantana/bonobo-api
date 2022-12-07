package com.api.bonobo;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.api.bonobo.model.Category;
import com.api.bonobo.model.Role;
import com.api.bonobo.model.User;
import com.api.bonobo.service.CategoryService;
import com.api.bonobo.service.UserService;

@SpringBootApplication
public class BonoboApplication {

	public static void main(String[] args) {
		SpringApplication.run(BonoboApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService, CategoryService categoryService) {
		return args -> {
			userService.saveRole(new Role(null, "USER"));
			userService.saveRole(new Role(null, "ADMIN"));
			userService.saveUser(new User("root", "root", "root", "root", new ArrayList<>()));
			userService.addRoleToUser("root", "ADMIN");
			categoryService.create(new Category("GAMES"));
			categoryService.create(new Category("3D MODELS"));
			categoryService.create(new Category("ART"));
			categoryService.create(new Category("NFT"));
			categoryService.create(new Category("OTHERS"));
		};
	}
}
