package net.mightypork.rcalc.tokens;


import net.mightypork.rcalc.TokenList;
import net.mightypork.rcalc.operations.Operation;
import net.mightypork.rcalc.operations.OperationModulo;


/**
 * Modulo token
 * 
 * @author Ondrej Hruska
 */
public class TokenOperatorModulo extends TokenBinaryOperator {

	@Override
	public String toString() {

		return "%";
	}


	@Override
	public Operation toOperation(TokenList left, TokenList right) {

		return new OperationModulo(left.parse(), right.parse());
	}

}
