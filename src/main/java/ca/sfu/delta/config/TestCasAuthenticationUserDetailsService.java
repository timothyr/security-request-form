package ca.sfu.delta.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TestCasAuthenticationUserDetailsService implements AuthenticationUserDetailsService {
    @Override
    public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
        System.out.println(token);
        return null;
    }
}
