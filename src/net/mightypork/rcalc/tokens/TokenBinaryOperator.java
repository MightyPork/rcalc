package net.mightypork.rcalc.tokens;


import net.mightypork.rcalc.TokenList;
import net.mightypork.rcalc.operations.Operation;


/**
 * Abstract operation token with two operands
 * 
 * @author Ondrej Hruska
 */
public abstract class TokenBinaryOperator implements IOperatorToken {

	/**
	 * Convert to operation
	 * 
	 * @param left left operand
	 * @param right right operand
	 * @return operation
	 */
	public abstract Operation toOperation(TokenList left, TokenList right);
}
