package net.mightypork.calculator;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import net.mightypork.rcalc.ParseError;
import net.mightypork.rcalc.RCalcSession;
import net.mightypork.rcalc.numbers.Fraction;


/**
 * Batch calculator (reads input from a file)
 * 
 * @author Ondrej Hruska
 */
public class CalculatorBatch implements Runnable {

	/** RCalc Session used by the calculator */
	private RCalcSession session = new RCalcSession();

	/** File to load the expressions from */
	private File file;

	/** Show results as fractions */
	private boolean fractionalMode = true;

	/** Flag that this calculator has been called from interactive mode */
	private boolean fromInteractive;

	//@formatter:off
	private static final String introMessage = 
			   "\nRational Calculator - file input mode\n"
			 + "--------------------------------------\n"
			 + "(c) Ondrej Hruska 2013\n"
			 + "\n";
	//@formatter:on

	/**
	 * Create a batch calculator
	 * 
	 * @param fileToLoad file to be executed
	 */
	public CalculatorBatch(File fileToLoad) {

		this.file = fileToLoad;
		this.fromInteractive = false;
	}


	/**
	 * Create a batch calculator
	 * 
	 * @param fileToLoad file to be executed
	 * @param calledFromInteractive called from the interactive calculator
	 */
	public CalculatorBatch(File fileToLoad, boolean calledFromInteractive) {

		this.file = fileToLoad;
		this.fromInteractive = calledFromInteractive;
	}


	/**
	 * Set symbol table (share variables with other session)
	 * 
	 * @param symbolTable symbol table
	 */
	public void setSymbolTable(HashMap<String, Fraction> symbolTable) {

		session.setSymbolTable(symbolTable);
	}


	@Override
	public void run() {

		if (!fromInteractive) {
			System.out.println(introMessage);
		}

		System.out.println("\nFILE = " + file.getAbsolutePath() + "\n");

		// input scanner
		Scanner sc;
		try {
			sc = new Scanner(new FileReader(file));
		} catch (IOException e) {
			System.out.println("ERROR:\n\tFile not found.");
			return;
		}

		System.out.println("--- File begin ---");

		while (sc.hasNextLine()) {

			String expr = sc.nextLine().trim();
			System.out.println("\n> " + expr);

			if (expr.equalsIgnoreCase("fractional") || expr.equalsIgnoreCase("decimal")) {

				if (fractionalMode) {
					System.out.println("[FRACTIONAL MODE OFF]");
					fractionalMode = false;
				} else {
					System.out.println("[FRACTIONAL MODE ON]");
					fractionalMode = true;
				}

			} else {
				try {
					Fraction result = session.evaluate(expr);
					System.out.println("= " + (fractionalMode ? result.toString() : result.getDoubleValue()));
				} catch (ParseError pe) {
					System.out.println("PARSE ERROR:\n\t" + pe.getMessage());
					System.out.println("\n--- Aborted ---");
					return;
				} catch (ArithmeticException ae) {
					System.out.println("MATH ERROR:\n\t" + ae.getMessage());
					System.out.println("\n--- Aborted ---");
					return;
				}
			}
		}

		System.out.println("\n--- File end ---");
	}
}
