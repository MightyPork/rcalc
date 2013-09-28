package net.mightypork.rcalc.tokens;


import net.mightypork.rcalc.TokenList;
import net.mightypork.rcalc.operations.Operation;


/**
 * Abstract operator token with one operand on right
 * 
 * @author Ondrej Hruska
 */
public abstract class TokenUnaryOperatorRight implements IOperatorToken {

	/**
	 * Convert to operation
	 * 
	 * @param operand operand
	 * @return operation
	 */
	public abstract Operation toOperation(TokenList operand);
}
