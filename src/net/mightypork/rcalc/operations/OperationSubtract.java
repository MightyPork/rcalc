package net.mightypork.rcalc.operations;


import net.mightypork.rcalc.IEvaluable;
import net.mightypork.rcalc.numbers.Fraction;


/**
 * Subtraction
 * 
 * @author Ondrej Hruska
 */
public class OperationSubtract extends BinaryOperation {

	/**
	 * Create subtraction
	 * 
	 * @param left left operand
	 * @param right right operand
	 */
	public OperationSubtract(IEvaluable left, IEvaluable right) {

		super(left, right);
	}


	@Override
	public Fraction evaluate() {

		return left.evaluate().subtract(right.evaluate());
	}


	@Override
	public String toString() {

		return "SUB{" + left + "," + right + "}";
	}

}
