package net.mightypork.rcalc.operations;


import net.mightypork.rcalc.IEvaluable;
import net.mightypork.rcalc.numbers.Fraction;


/**
 * Abstract operation with two operands
 * 
 * @author Ondrej Hruska
 */
public abstract class BinaryOperation extends Operation {

	/** Left operand */
	protected IEvaluable left = null;
	/** Right operand */
	protected IEvaluable right = null;


	/**
	 * Create a binary operation
	 * 
	 * @param left left operand
	 * @param right right operand
	 */
	public BinaryOperation(IEvaluable left, IEvaluable right) {

		this.left = left;
		this.right = right;
	}


	@Override
	public abstract Fraction evaluate();

}
