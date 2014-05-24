package net.mightypork.rcalc;


import net.mightypork.rcalc.numbers.Fraction;


/**
 * Interface of an object that can be turned into a fractional value
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface IEvaluable {

	/**
	 * Get fractional value
	 * 
	 * @return fractional value
	 */
	public Fraction evaluate();
}
