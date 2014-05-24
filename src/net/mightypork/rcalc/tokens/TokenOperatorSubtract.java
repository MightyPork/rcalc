package net.mightypork.rcalc.tokens;


import net.mightypork.rcalc.TokenList;
import net.mightypork.rcalc.operations.Operation;
import net.mightypork.rcalc.operations.OperationSubtract;


/**
 * Subtraction token
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TokenOperatorSubtract extends TokenBinaryOperator {

	@Override
	public String toString() {

		return "-";
	}


	@Override
	public Operation toOperation(TokenList left, TokenList right) {

		return new OperationSubtract(left.parse(), right.parse());
	}

}
