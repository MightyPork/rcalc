package net.mightypork.rcalc.operations;


import net.mightypork.rcalc.IEvaluable;
import net.mightypork.rcalc.numbers.Fraction;


/**
 * Modulo
 * 
 * @author Ondrej Hruska
 */
public class OperationModulo extends BinaryOperation {

	/**
	 * Create modulo
	 * 
	 * @param left left operand
	 * @param right right operand
	 */
	public OperationModulo(IEvaluable left, IEvaluable right) {

		super(left, right);
	}


	@Override
	public Fraction evaluate() {

		return left.evaluate().modulo(right.evaluate());
	}


	@Override
	public String toString() {

		return "MOD{" + left + "," + right + "}";
	}
}
