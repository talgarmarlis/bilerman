package com.artatech.bilerman;

import com.artatech.bilerman.Repositories.ArticleRepository;
import com.artatech.bilerman.AccountManager.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class BilermanApplication {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(BilermanApplication.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		 final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		 messageSource.setBasename("classpath:messages/messages");
		 messageSource.setUseCodeAsDefaultMessage(true);
		 messageSource.setDefaultEncoding("UTF-8");
		 messageSource.setCacheSeconds(0);
		 return messageSource;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
