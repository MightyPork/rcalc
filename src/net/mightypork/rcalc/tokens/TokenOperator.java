package net.mightypork.rcalc.tokens;

import net.mightypork.rcalc.TokenList;

public class TokenOperator implements IOperatorToken {

	@Override
	public TokenList wrapInTokenList() {
		return new TokenList(this);
	}

}
