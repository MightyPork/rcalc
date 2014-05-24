package net.mightypork.rcalc.operations;


import net.mightypork.rcalc.IEvaluable;
import net.mightypork.rcalc.numbers.Fraction;


/**
 * Abstract unary operator
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class UnaryOperation extends Operation {

	/** Operand */
	protected IEvaluable operand = null;


	/**
	 * Create unary operation
	 * 
	 * @param operand operand
	 */
	public UnaryOperation(IEvaluable operand) {

		this.operand = operand;
	}


	@Override
	public abstract Fraction evaluate();

}
