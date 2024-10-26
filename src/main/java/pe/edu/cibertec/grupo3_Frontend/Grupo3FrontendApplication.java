package pe.edu.cibertec.grupo3_Frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Grupo3FrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(Grupo3FrontendApplication.class, args);
	}

}
