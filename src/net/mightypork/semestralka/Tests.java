package net.mightypork.semestralka;


import net.mightypork.rcalc.RCalc;


/**
 * RCalc unit tests
 * 
 * @author Ondrej Hruska
 */
public class Tests {

	/**
	 * Run the tests
	 */
	public static void run() {

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
				// misc
				"23%(6/5)",
				"53/(5-4-1)",
				"100!",
				"24/4/3",
				"(10+1)(15-3)",
				"13(55-3/12)^2",
				"(1/2)^-2",
				"13^(1/2)", // should fail
				"(1/2)*(3/4)",
				"(1/2)/(3/4)", // compound fraction
		};
		//@formatter:on

		for (String expr : testCases) {
			System.out.println("\n\n###### test case begin ######");

			try {
				System.out.println("IN: " + expr);
				System.out.println("OUT: " + rc.evaluate(expr));
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}

	}
}
