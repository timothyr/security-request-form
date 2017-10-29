package ca.sfu.delta.Utilities;

public class GlobalConstants {
	// This is the maximum times to get a new value and query the DB to see if it exists
	// i.e. create a new token and check if it exists in the DB, repeat until the DB returns false
	// (found a unique token) or have checked MAX__REPEAT_CHECK_UNIQUE tokens
	public static final int MAX__REPEAT_CHECK_UNIQUE = 15;

	// The length of the tokens used for user request form retrieval URLs
	public static final int LENGTH_OF_TOKENS = 30;

	// The server's host address
	public static final String SERVER_HOST_ADDRESS = "http://cmpt373-1177d.cmpt.sfu.ca:8080";
}
