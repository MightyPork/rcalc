package net.mightypork.rcalc.operations;


import net.mightypork.rcalc.IEvaluable;
import net.mightypork.rcalc.numbers.Fraction;


/**
 * Factorial
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class OperationFactorial extends UnaryOperation {

	/**
	 * Create factorial
	 * 
	 * @param operand operand
	 */
	public OperationFactorial(IEvaluable operand) {

		super(operand);
	}


	@Override
	public Fraction evaluate() {

		return operand.evaluate().factorial();
	}


	@Override
	public String toString() {

		return "FCT{" + operand + "}";
	}
}
