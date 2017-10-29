package ca.sfu.delta.Utilities;

import java.security.SecureRandom;

public class RandomStringGenerator {

	private String alphaNumericChars;
	private SecureRandom randNumGenerator;

	public RandomStringGenerator() {
		StringBuilder lowerAlphaCharsBuffer = new StringBuilder(26);
		for (char letter = 'a'; letter <= 'z'; letter++) {
			lowerAlphaCharsBuffer.append(letter);
		}
		String lowerAlphaChars = lowerAlphaCharsBuffer.toString();

		StringBuilder numericCharsBuffer = new StringBuilder(10);
		for (int number = 0; number <= 9; number++) {
			numericCharsBuffer.append(number);
		}

		// All the alphabetic an numeric characters possible to use for token creation
		alphaNumericChars = lowerAlphaChars + lowerAlphaChars.toUpperCase() + numericCharsBuffer.toString();
		randNumGenerator = new SecureRandom();
	}

	public String generateToken() {
		StringBuilder tokenBuilder = new StringBuilder(GlobalConstants.LENGTH_OF_TOKENS);
		// Pick LENGTH_OF_TOKENS random indices and use these indices to get random characters from the
		// list of possible characters (alphaNumericChars)
		for (int i = 0; i < GlobalConstants.LENGTH_OF_TOKENS; i++) {
			// get a random index
			int index = randNumGenerator.nextInt(alphaNumericChars.length());
			// get a random character
			tokenBuilder.append(alphaNumericChars.charAt(index));
		}

		return tokenBuilder.toString();
	}
}
