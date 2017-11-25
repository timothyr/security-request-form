package ca.sfu.delta.security;

import ca.sfu.delta.models.AuthorizedUser;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	Spring Security wants you to use port 8443, with https.

	@Value("${spring.security.enabled}")
	private boolean isSecurityEnabled;

	@Value("${server.baseUrl}")
	private String baseUrl;

	@Bean
	public ServiceProperties serviceProperties() {
		ServiceProperties serviceProperties = new ServiceProperties();
		serviceProperties.setService("https://"+baseUrl+"/j_spring_cas_security_check");
		serviceProperties.setSendRenew(false);
		serviceProperties.setAuthenticateAllArtifacts(true);
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
		return new Cas20ServiceTicketValidator("https://cas.sfu.ca/cas");
	}

	@Bean
	public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
		CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
		casAuthenticationFilter.setAuthenticationManager(authenticationManager());
		return casAuthenticationFilter;
	}

	@Bean
	public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
		CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
		casAuthenticationEntryPoint.setLoginUrl("https://cas.sfu.ca/cas/login");
		casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
		return casAuthenticationEntryPoint;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (!isSecurityEnabled) {
			return;
		}

		http.addFilter(casAuthenticationFilter());

		http.exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint());

		http.authenticationProvider(casAuthenticationProvider())
				.authorizeRequests().antMatchers(
						"/css/**",
						"/fonts/**",
						"/img/**",
						"/js/**",
						"/api/**",
						"/WEB-INF/form.html",
						"/"
				).permitAll().and()

				.authorizeRequests().antMatchers(
						"/security/**",
						"/api/authuser/**"
				).hasAnyAuthority(
					AuthorizedUser.Privilege.ADMIN.toString(),
					AuthorizedUser.Privilege.SECURITY.toString()
				).anyRequest().authenticated();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.authenticationProvider(casAuthenticationProvider());
	}
}