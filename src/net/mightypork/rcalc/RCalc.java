package net.mightypork.rcalc;


import net.mightypork.rcalc.numbers.Fraction;


/**
 * Rational calculator, expression parser returning fractional results instead
 * of imprecise decimal values.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class RCalc implements IDebugable {

	/** Debug mode flag */
	private boolean debug = false;
	/** Tokenizer instance */
	private Tokenizer tokenizer = new Tokenizer();


	/**
	 * Create new rational calculator
	 */
	public RCalc() {

	}


	@Override
	public boolean isDebug() {

		return debug;
	}


	@Override
	public void setDebug(boolean debug) {

		tokenizer.setDebug(debug);
		this.debug = debug;
	}


	/**
	 * Calculate a result of an expression.
	 * 
	 * @param expression input expression
	 * @return computed result
	 */
	public Fraction evaluate(String expression) {

		IEvaluable ev;


		try {
			TokenList tl = tokenizer.tokenize(expression);

			if (debug) System.out.println("\nBuilding syntax tree...");

			ev = tl.parse();
		} catch (ParseError e) {
			throw e;
		} catch (NumberFormatException e) {
			throw new ParseError("Invalid number format.");
		} catch (Exception e) {
			throw new ParseError(e);
		}

		if (debug) System.out.println("\nSyntax tree:\n" + ev + "\n");

		Fraction out = ev.evaluate();
		return out;
	}
}
