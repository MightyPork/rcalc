package net.mightypork.rcalc.tokens;


import net.mightypork.rcalc.TokenList;
import net.mightypork.rcalc.operations.Operation;
import net.mightypork.rcalc.operations.OperationMultiply;


/**
 * Multiplication token
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TokenOperatorMultiply extends TokenBinaryOperator {

	@Override
	public String toString() {

		return "*";
	}


	@Override
	public Operation toOperation(TokenList left, TokenList right) {

		return new OperationMultiply(left.parse(), right.parse());
	}

}
