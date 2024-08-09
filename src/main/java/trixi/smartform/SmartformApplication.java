package trixi.smartform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulith;

@SpringBootApplication
@Modulith
public class SmartformApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartformApplication.class, args);
	}
}
