package net.mightypork.rcalc.operations;


import net.mightypork.rcalc.IEvaluable;
import net.mightypork.rcalc.numbers.Fraction;


/**
 * Addition
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class OperationAdd extends BinaryOperation {

	/**
	 * Create addition
	 * 
	 * @param left left operand
	 * @param right right operand
	 */
	public OperationAdd(IEvaluable left, IEvaluable right) {

		super(left, right);
	}


	@Override
	public Fraction evaluate() {

		return left.evaluate().add(right.evaluate());
	}


	@Override
	public String toString() {

		return "ADD{" + left + "," + right + "}";
	}
}
