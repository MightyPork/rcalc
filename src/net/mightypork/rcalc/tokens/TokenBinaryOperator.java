package net.mightypork.rcalc.tokens;


import net.mightypork.rcalc.TokenList;
import net.mightypork.rcalc.operations.Operation;


/**
 * Abstract operation token with two operands
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class TokenBinaryOperator extends TokenOperator {

	/**
	 * Convert to operation
	 * 
	 * @param left left operand
	 * @param right right operand
	 * @return operation
	 */
	public abstract Operation toOperation(TokenList left, TokenList right);
}
