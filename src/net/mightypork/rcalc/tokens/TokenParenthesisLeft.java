package net.mightypork.rcalc.tokens;


import net.mightypork.rcalc.IToken;


/**
 * Left parenthesis token
 * 
 * @author Ondrej Hruska
 */
public class TokenParenthesisLeft implements IToken {

	@Override
	public String toString() {

		return "(";
	}

}
