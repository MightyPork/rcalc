package net.mightypork.rcalc.operations;


import net.mightypork.rcalc.IEvaluableToken;
import net.mightypork.rcalc.TokenList;
import net.mightypork.rcalc.numbers.Fraction;


/**
 * Abstract operation
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class Operation implements IEvaluableToken {

	@Override
	public abstract Fraction evaluate();
	
	@Override
	public TokenList wrapInTokenList() {
		return new TokenList(this);
	}
}
