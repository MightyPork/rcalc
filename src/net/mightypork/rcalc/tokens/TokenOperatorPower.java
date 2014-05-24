package net.mightypork.rcalc.tokens;


import net.mightypork.rcalc.TokenList;
import net.mightypork.rcalc.operations.Operation;
import net.mightypork.rcalc.operations.OperationPower;


/**
 * Exponentiation token
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TokenOperatorPower extends TokenBinaryOperator {

	@Override
	public String toString() {

		return "^";
	}


	@Override
	public Operation toOperation(TokenList left, TokenList right) {

		return new OperationPower(left.parse(), right.parse());
	}

}
