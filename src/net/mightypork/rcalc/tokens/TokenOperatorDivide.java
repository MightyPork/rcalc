package net.mightypork.rcalc.tokens;


import net.mightypork.rcalc.TokenList;
import net.mightypork.rcalc.operations.Operation;
import net.mightypork.rcalc.operations.OperationDivide;


/**
 * Division token
 * 
 * @author Ondrej Hruska
 */
public class TokenOperatorDivide extends TokenBinaryOperator {

	@Override
	public String toString() {

		return "/";
	}


	@Override
	public Operation toOperation(TokenList left, TokenList right) {

		return new OperationDivide(left.parse(), right.parse());
	}

}
