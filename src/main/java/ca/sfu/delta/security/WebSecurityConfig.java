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
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	Spring Security wants you to use port 8443, with https.

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
		http.addFilter(casAuthenticationFilter());

		http.exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint());

		http.csrf().disable();

		http.logout().permitAll().logoutSuccessUrl("https://cas.sfu.ca/cas/logout");

		http.authenticationProvider(casAuthenticationProvider())
				.authorizeRequests()

				// Administrative pages
				.antMatchers(
					"/api/admin/**",
					"/api/authuser/**"
				).hasAuthority(
					AuthorizedUser.Privilege.ADMIN.toString()
				)

				// Secure pages
				.antMatchers(
					"/security/**",
					"/api/authuser/**",
					"/api/dist/**",
					"/api/user/**",
					"/api/form/getByRequestID/**",
					"/api/form/get/**",
					"/api/form/search",
					"/api/form/saveSecurity",
					"/api/csv/**",
					"/api/invoice/**"
				).hasAnyAuthority(
					AuthorizedUser.Privilege.ADMIN.toString(),
					AuthorizedUser.Privilege.SECURITY.toString()
				)

				.antMatchers("/login").authenticated()

				// Everything else is publicly accessible
				.antMatchers("/**").permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.authenticationProvider(casAuthenticationProvider());
	}
}