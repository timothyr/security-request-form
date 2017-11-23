package ca.sfu.delta.config;

import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.cas.web.authentication.ServiceAuthenticationDetails;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public ServiceProperties serviceProperties() {
		ServiceProperties serviceProperties = new ServiceProperties();
		serviceProperties.setService("http://localhost:8080/cas/j_spring_cas_security_check");
//		serviceProperties.setService("http://localhost:8080/");
//		serviceProperties.setService("https://cas.sfu.ca/cas/serviceValidate");
		serviceProperties.setSendRenew(false);
		return serviceProperties;
	}

	@Bean
	public CasAuthenticationProvider casAuthenticationProvider() {
		CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
		casAuthenticationProvider.setAuthenticationUserDetailsService(authenticationUserDetailsService());
		casAuthenticationProvider.setServiceProperties(serviceProperties());
		casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
		casAuthenticationProvider.setKey("an_id_for_this_auth_provider_only");
		return casAuthenticationProvider;
	}

	@Bean
	public AuthenticationUserDetailsService authenticationUserDetailsService() {
		return new TestCasAuthenticationUserDetailsService();
	}

	@Bean
	public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
//		return new Cas20ServiceTicketValidator("http://localhost:8080/cas");
		return new Cas20ServiceTicketValidator("https://cas.sfu.ca/cas/serviceValidate");
	}

	@Bean
	public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
		CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
		casAuthenticationFilter.setAuthenticationManager(authenticationManager());
		casAuthenticationFilter.setAuthenticationDetailsSource(dynamicServiceResolver());
		return casAuthenticationFilter;
	}

	@Bean
	public AuthenticationDetailsSource<HttpServletRequest,ServiceAuthenticationDetails> dynamicServiceResolver() {
		return new AuthenticationDetailsSource<HttpServletRequest, ServiceAuthenticationDetails>() {
			@Override
			public ServiceAuthenticationDetails buildDetails(HttpServletRequest context) {
				System.out.println("HELLO");
//				final String url = makeDynamicUrlFromRequest(serviceProperties());
				return new ServiceAuthenticationDetails() {
					@Override
					public String getServiceUrl() {
						return context.getRequestURI();
					}
				};
			}
		};
	}

	@Bean
	public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
		CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
//		casAuthenticationEntryPoint.setLoginUrl("http://localhost:8080/cas/login");
		casAuthenticationEntryPoint.setLoginUrl("https://cas.sfu.ca/cas/login");
		casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
		return casAuthenticationEntryPoint;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.addFilter(casAuthenticationFilter());
		http
				.exceptionHandling()
				.authenticationEntryPoint(casAuthenticationEntryPoint());

		http
				.authenticationProvider(casAuthenticationProvider())
				.authorizeRequests()
				.antMatchers("/css/**", "/").permitAll()
				.antMatchers("/security/**").authenticated()
				.and()
				.formLogin().loginPage("/my-app/login");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.authenticationProvider(casAuthenticationProvider());
	}
}