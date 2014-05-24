package net.mightypork.rcalc.operations;


import net.mightypork.rcalc.IEvaluable;
import net.mightypork.rcalc.numbers.Fraction;


/**
 * Multiplication
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class OperationMultiply extends BinaryOperation {

	/**
	 * Create multiplication
	 * 
	 * @param left left operand
	 * @param right right operand
	 */
	public OperationMultiply(IEvaluable left, IEvaluable right) {

		super(left, right);
	}


	@Override
	public Fraction evaluate() {

		return left.evaluate().multiply(right.evaluate());
	}


	@Override
	public String toString() {

		return "MUL{" + left + "," + right + "}";
	}

}
