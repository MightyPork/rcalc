package net.mightypork.rcalc.tokens;


import net.mightypork.rcalc.TokenList;
import net.mightypork.rcalc.operations.Operation;
import net.mightypork.rcalc.operations.OperationFactorial;


/**
 * Factorial token
 * 
 * @author Ondrej Hruska
 */
public class TokenOperatorFactorial extends TokenUnaryOperatorLeft {

	@Override
	public String toString() {

		return "!";
	}


	@Override
	public Operation toOperation(TokenList argument) {

		return new OperationFactorial(argument.parse());
	}

}
