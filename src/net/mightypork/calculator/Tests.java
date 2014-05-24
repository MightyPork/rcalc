package net.mightypork.calculator;


import net.mightypork.rcalc.RCalc;


/**
 * RCalc unit tests
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Tests implements Runnable {

	/**
	 * Run the tests
	 */
	@Override
	public void run() {

		System.out.println("--- RCalc Unit Tests ---");
		RCalc rc = new RCalc();
		rc.setDebug(true);

		//@formatter:off
		String[] testCases = new String[] {
				// Parser unit test
				"57**3 + -7^2 + (-7)^2", // power
				"(13*7)(99^2) * 4(12/7) + (17-8/2)13 + (4/3)*5", // implicit multiplication
				"(1-1)(1--1)(1---1)(1----1)(1--+-+-+++--+-+1)(1--+-+-+-+-----1)(5*-1)", // minus, plus
				"-(15/2)+(72*43-2)-(12+1)", // minus with parentheses
				"+34-(--8+2*+13)", // + at beginning of scope
				"1000!", // big factorial
				"24/4/3", // chained division must go LTR
				"5-4-1", // subtraction must go LTR
				"5(10+1)(4!3)", // implicit multiplication
				"(1/2)^-2", // inverting a fraction
				"(1/2)/(3/4)", // compound fraction
				"(1/2)*(3/4)", // fraction multiplication
		};
		//@formatter:on

		for (String expr : testCases) {
			System.out.println("\n\n# Test Case #");

			try {
				System.out.println("IN: " + expr);
				System.out.println("OUT: " + rc.evaluate(expr));
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}

		System.out.println("--- Unit Tests End ---");

	}
}
