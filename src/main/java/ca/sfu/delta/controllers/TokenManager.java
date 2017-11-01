package ca.sfu.delta.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TokenManager {
	private static final int TOKEN_LENGTH = 8;
	private static final String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	private final List<TokenEntry> tokens = new ArrayList<TokenEntry>();

	public String createToken(String username) {
		TokenEntry tokenEntry = new TokenEntry(username);
		this.tokens.add(tokenEntry);
		return tokenEntry.token;
	}

	public boolean tokenExists(String token) {
		for (TokenEntry tokenEntry : this.tokens) {
			if (tokenEntry.token.equals(token)) {
				return true;
			}
		}

		return false;
	}

	public boolean usernameExists(String username) {
		for (TokenEntry tokenEntry : this.tokens) {
			if (tokenEntry.username.equals(username)) {
				return true;
			}
		}

		return false;
	}

	public String getToken(String username) {
		for (TokenEntry tokenEntry : this.tokens) {
			if (tokenEntry.username.equals(username)) {
				return tokenEntry.token;
			}
		}

		return null;
	}

	private class TokenEntry {
		String token;
		String username;

		public TokenEntry(String username) {
			this.username = username;
			this.token = generateToken();
			while (tokenExists(this.token)) {
				this.token = generateToken();
			}
		}

		public String generateToken() {
			StringBuilder sb = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < TOKEN_LENGTH; i++) {
				sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
			}
			return sb.toString();
		}
	}
}
