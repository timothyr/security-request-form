package ca.sfu.delta;

import ca.sfu.delta.data.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "ca.sfu.delta.ui",
        "ca.sfu.delta.data",
        "ca.sfu.delta.auth",
})
public class Application {

	public static void main(String[] args) {
	    SpringApplication.run(Application.class, args);
	}

}
