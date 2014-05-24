package net.mightypork.rcalc.tokens;


import net.mightypork.rcalc.TokenList;
import net.mightypork.rcalc.operations.Operation;
import net.mightypork.rcalc.operations.OperationAdd;


/**
 * Addition token
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TokenOperatorAdd extends TokenBinaryOperator {

	@Override
	public String toString() {

		return "+";
	}


	@Override
	public Operation toOperation(TokenList left, TokenList right) {

		return new OperationAdd(left.parse(), right.parse());
	}

}
