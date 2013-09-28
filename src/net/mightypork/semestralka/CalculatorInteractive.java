package net.mightypork.semestralka;


import java.io.File;
import java.util.*;
import java.util.Map.Entry;

import net.mightypork.rcalc.ParseError;
import net.mightypork.rcalc.RCalcSession;
import net.mightypork.rcalc.numbers.Fraction;


/**
 * Interactive calculator
 * 
 * @author Ondrej Hruska
 */
public class CalculatorInteractive implements Runnable {

	/** Rcalc Session used by the calculator */
	private RCalcSession session = new RCalcSession();

	/** Show results as fractions */
	private boolean fractionalMode = true;

	/** Command history */
	private ArrayList<String> history = new ArrayList<String>();


	//@formatter:off
	private static final String introMessage = 
		   "\nRational Calculator - interactive mode\n"
		 + "--------------------------------------\n"
		 + "(c) Ondrej Hruska 2013\n"
		 + "\n"
		 + "Type 'help' for more info.\n\n"
		 + "Enter expression, 'exit' to quit.";
	
	
	private static final String helpMessage = 
		   "\n### HELP ###\n\n"
		 + "Commands:\n"
		 + "\thelp - show this help\n"
		 + "\texit - quit the calculator\n"
		 + "\tdebug - toggle debug mode (default off)\n"
		 + "\tdecimal - toggle decimal/fractional output\n"
		 + "\tvars - print a list of variables\n"
		 + "\tload filename - execute commands from a file\n"
		 + "\t\t(Expressions from the file can use/change variables)\n"
		 + "\n"
		 + "Mathematical operations, syntax\n"
		 + "\tSupported: + - * / % ^ ! ( )\n"
		 + "\tMultiplication sign can be omitted where it makes sense.\n"
		 + "\tAlong with fractions, decimal-point format works too.\n"
		 + "\tUse 'a/b' syntax to express a fraction.\n"
		 + "\n"
		 + "Variables\n"
		 + "\tAssign a variable:\n"
		 + "\t> name = expression\n\n"
		 + "\tModify a variable:\n"
		 + "\t> name += expression\n"
		 + "\t> name -= expression\n"
		 + "\t> name *= expression\n"
		 + "\t> name /= expression\n"
		 + "\t> name %= expression\n"
		 + "\t> name ^= expression\n\n"
		 + "\tYou can also use variable names in expressions.";
	//@formatter:on


	/**
	 * Create an interactive calculator
	 */
	public CalculatorInteractive() {

	}


	@Override
	public void run() {

		System.out.println(introMessage);

		// input scanner
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("\n> ");

			// wait for input
			while (!sc.hasNextLine()) {}

			String expr = sc.nextLine().toLowerCase().trim();
			
			if (expr.equals("%")) { // repeat last expression
				if (history.size() > 0) {
					expr = history.get(history.size() - 1);
					System.out.println("| " + expr);
				} else {
					System.out.println("No history available.");
					continue;
				}
			}
			
			if (expr.equals("exit")) { // quit
				System.out.println("Exit.");
				return;

			} else if (expr.equals("help")) { // show help
				System.out.println(helpMessage);

			} else if (expr.equals("debug")) { // toggle debug mode

				if (session.isDebug()) {
					System.out.println("[DEBUG OFF]");
					session.setDebug(false);
				} else {
					System.out.println("[DEBUG ON]");
					session.setDebug(true);
				}

			} else if (expr.equals("decimal")) { // toggle decimal/fractional output mode

				if (fractionalMode) {
					System.out.println("[FRACTIONAL MODE OFF]");
					fractionalMode = false;
				} else {
					System.out.println("[FRACTIONAL MODE ON]");
					fractionalMode = true;
				}

			} else if (expr.startsWith("load ")) { // load and execute expressions from file
				expr = expr.replace("load ", "");

				CalculatorBatch cb = new CalculatorBatch(new File(expr), true);
				cb.setSymbolTable(session.getSymbolTable());

				cb.run();

			} else if (expr.equals("vars")) { // list variables

				HashMap<String, Fraction> vars = (session.getSymbolTable());

				Map<String, Fraction> varsSorted = sortByKeys(vars);

				System.out.println("\nVariables\n");

				for (Entry<String, Fraction> entry : varsSorted.entrySet()) {
					System.out.println("\t" + entry.getKey() + " = " + entry.getValue());
				}

			} else { // evaluate an expression

				try {
					Fraction result = session.evaluate(expr);
					if (result == null) {
						continue;
					}
					history.add(expr);
					System.out.println("= " + (fractionalMode ? result.toString() : result.getDoubleValue()));
				} catch (ParseError pe) {
					System.out.println("PARSE ERROR:\n\t" + pe.getMessage());
				} catch (ArithmeticException ae) {
					System.out.println("MATH ERROR:\n\t" + ae.getMessage());
				}
			}

		}
	}


	/**
	 * Sort a map by keys, maintaining key-value pairs.<br>
	 * Source: StackOverflow.com
	 * 
	 * @param map map to be sorted
	 * @return linked hash map with sorted entries
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <K extends Comparable, V> Map<K, V> sortByKeys(Map<K, V> map) {

		List<K> keys = new LinkedList<K>(map.keySet());
		Collections.sort(keys);

		Map<K, V> sortedMap = new LinkedHashMap<K, V>();
		for (K key : keys) {
			sortedMap.put(key, map.get(key));
		}

		return sortedMap;
	}
}
