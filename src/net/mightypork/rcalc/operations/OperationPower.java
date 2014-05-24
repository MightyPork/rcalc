package net.mightypork.rcalc.operations;


import net.mightypork.rcalc.IEvaluable;
import net.mightypork.rcalc.numbers.Fraction;


/**
 * Exponentiation
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class OperationPower extends BinaryOperation {

	/**
	 * Create exponentiation
	 * 
	 * @param left left operand
	 * @param right right operand
	 */
	public OperationPower(IEvaluable left, IEvaluable right) {

		super(left, right);
	}


	@Override
	public Fraction evaluate() {

		return left.evaluate().power(right.evaluate());
	}


	@Override
	public String toString() {

		return "POW{" + left + "," + right + "}";
	}

}
