package ca.sfu.delta.security;

import ca.sfu.delta.models.AuthorizedUser;
import ca.sfu.delta.repository.AuthorizedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class TestCasAuthenticationUserDetailsService implements AuthenticationUserDetailsService {

    @Autowired
    AuthorizedUserRepository userRepository;

    @Value("${admin.username}")
    String adminUsername;

    @Override
    public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
        String username = token.getName();

        if (adminUsername != null && username.equals(adminUsername)) {
            List<GrantedAuthority> authorities = new ArrayList();
            authorities.add((GrantedAuthority) () -> AuthorizedUser.Privilege.ADMIN.toString());
            return new User(username, "", authorities);
        }

        for (AuthorizedUser user : userRepository.findAllByUsername(username + "@sfu.ca")) {
            List<GrantedAuthority> authorities = new ArrayList();
            authorities.add((GrantedAuthority) () -> user.getPrivilege().toString());

            return new User(username, "", authorities);
        }

        return new User(username, "", new ArrayList());
    }
}
