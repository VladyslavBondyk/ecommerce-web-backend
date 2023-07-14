package com.ecommercebackend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import io.github.cdimascio.dotenv.Dotenv;
//import io.github.cdimascio.dotenv.DotenvBuilder;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@RestController
public class EcommerceBackendApplication {

	@GetMapping("/")
	public String home () {
		return "Welcome to AWS";
	}
	public static void main(String[] args) {
		SpringApplication.run(EcommerceBackendApplication.class, args);
	}

//	Dotenv dotenv = Dotenv.configure().load();
//	String dbUsername = dotenv.get("dbUsername");
//	String dbPassword = dotenv.get("dbPassword");
//	String dbName = dotenv.get("dbName");

}
