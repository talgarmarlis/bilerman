package com.artatech.bilerman;

import com.artatech.bilerman.Entities.Post;
import com.artatech.bilerman.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BilermanApplication {

	@Autowired
	private PostRepository postRepository;

	public static void main(String[] args) {
		SpringApplication.run(BilermanApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
//            Save demo data after start
			postRepository.save(new Post("Post", "USD", "first post body"));
			postRepository.save(new Post("Euro", "EUR", "body"));
		};
	}
}
