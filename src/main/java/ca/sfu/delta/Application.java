package ca.sfu.delta;

import ca.sfu.delta.models.AuthorizedUser;
import ca.sfu.delta.repository.AuthorizedUserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;

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
    /* COMMENT OUT THIS FOR DEBUGGING (SHOW WHITELABEL PAGES) */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
	    return (container -> {
	        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
	        container.addErrorPages(error404Page);
        });
    }
}

