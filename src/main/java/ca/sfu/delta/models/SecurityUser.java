package ca.sfu.delta.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// @Entity tells Hibernate to make a table out of this class
@Entity
public class SecurityUser {

	public SecurityUser() {

	}

	public SecurityUser(String username) {
		this.username = username;
		this.role = Role.SECURITY;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Role role;

	private String username;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return this.role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public enum Role {
		ADMIN,
		SECURITY
	}
}
