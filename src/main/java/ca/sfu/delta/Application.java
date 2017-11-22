package ca.sfu.delta;

import ca.sfu.delta.models.AuthorizedUser;
import ca.sfu.delta.repository.AuthorizedUserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {})
public class Application {

    //TODO remove this in production
    @Autowired
    private AuthorizedUserRepository authorizedUserRepository;

	public static void main(String[] args) {
	    SpringApplication.run(Application.class, args);
	}

    //TODO remove this in production
	@Bean
    InitializingBean addUsers() {
        return () -> {
            authorizedUserRepository.save(new AuthorizedUser("jeldridg@sfu.ca", AuthorizedUser.Privilege.SECURITY));
            authorizedUserRepository.save(new AuthorizedUser("jkrasuin@sfu.ca", AuthorizedUser.Privilege.SECURITY));
            authorizedUserRepository.save(new AuthorizedUser("jlawclav@sfu.ca", AuthorizedUser.Privilege.SECURITY));
            authorizedUserRepository.save(new AuthorizedUser("security@sfu.ca", AuthorizedUser.Privilege.SECURITY));
        };
    }
}

