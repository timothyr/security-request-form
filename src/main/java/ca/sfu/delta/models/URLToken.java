package ca.sfu.delta.models;

import ca.sfu.delta.Utilities.RandomStringGenerator;

import javax.persistence.*;


@Entity
public class URLToken {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	//Used to keep track of the key for the FormData table
	@Column(nullable = false)
	private Long formDataID;

	@Column(nullable = false, unique = true)
	private String token;

	private static RandomStringGenerator randTokenGenerator = new RandomStringGenerator();

	public URLToken(Long initialFormDataID) {
		formDataID = initialFormDataID;
		token = generateToken();
	}

	public URLToken() {}

	public Long getFormDataID() {
		return formDataID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String newToken) {
		token = newToken;
	}

	public String generateToken() {
		return randTokenGenerator.generateToken();
	}
}
