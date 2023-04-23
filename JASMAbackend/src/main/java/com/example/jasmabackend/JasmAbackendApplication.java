package com.example.jasmabackend;

import com.example.jasmabackend.entities.User;
import com.example.jasmabackend.repositories.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class JasmAbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JasmAbackendApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(UserRepository userRepository) {
//		return args -> {
//			Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
//				User user = new User(name, name.toLowerCase() + "@domain.com");
//				try {
//					userRepository.save(user);
//				} catch (Exception e) {
//					System.out.println("Can't add users because of constraints");
//				}
//			});
//			userRepository.findAll().forEach(System.out::println);
//		};
//	}
}
