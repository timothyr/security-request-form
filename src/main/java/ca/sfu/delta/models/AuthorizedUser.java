package ca.sfu.delta.models;

import javax.persistence.*;

@Entity
public class AuthorizedUser {

    public enum Privilege {
        ADMIN,
        SECURITY
    }

    @Id
    @GeneratedValue
    private Long id;
    private String email;

    @Enumerated(EnumType.STRING)
    private Privilege privilege;

    public AuthorizedUser() {
        //dummy constructor
    }

    //TODO REMOVE THIS IN PRODUCTION
    public AuthorizedUser(String email, Privilege privilege) {
        this.email = email;
        this.privilege = privilege;
    }

    public Privilege getPrivilege() {
        return privilege;
    }
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
