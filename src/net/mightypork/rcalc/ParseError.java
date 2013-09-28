package net.mightypork.rcalc;


/**
 * Error thrown when a syntax error is encountered while parsing an expression
 * 
 * @author Ondrej Hruska
 */
public class ParseError extends RuntimeException {

	/**
	 * Create new parse error
	 * 
	 * @param message error message
	 */
	public ParseError(String message) {

		super(message);
	}


	/**
	 * Wrapper another exception in a parse error
	 * 
	 * @param e other exception
	 */
	public ParseError(Exception e) {

		super(e);
	}

}
