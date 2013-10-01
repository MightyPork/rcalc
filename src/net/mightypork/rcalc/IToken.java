package net.mightypork.rcalc;


/**
 * Token interface
 * 
 * @author Ondrej Hruska
 */
public interface IToken {
	/**
	 * Wrap this token in a tokenList (return itself if already instance of a TokenList)
	 * @return the wrapping TokenList
	 */
	public TokenList wrapInTokenList();
}
