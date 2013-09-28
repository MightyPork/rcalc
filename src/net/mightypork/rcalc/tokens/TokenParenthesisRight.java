package net.mightypork.rcalc.tokens;


import net.mightypork.rcalc.IToken;


/**
 * Right parenthesis token
 * 
 * @author Ondrej Hruska
 */
public class TokenParenthesisRight implements IToken {

	@Override
	public String toString() {

		return ")";
	}

}
