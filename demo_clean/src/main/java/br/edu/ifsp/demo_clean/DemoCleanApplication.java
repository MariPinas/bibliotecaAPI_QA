package br.edu.ifsp.demo_clean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "${app.name}", version = "${app.version}", description = "${app.description}"))
public class DemoCleanApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoCleanApplication.class, args);
	}

}
