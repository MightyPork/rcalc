package net.mightypork.rcalc;


import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.mightypork.rcalc.numbers.Fraction;


/**
 * Rational Calculator Session - interactive RCalc with possibility to set and
 * use variables.
 * 
 * @author Ondrej Hruska
 */
public class RCalcSession implements IDebugable {

	/** Debug mode flag */
	private boolean debug = false;
	/** Calculator instance */
	private RCalc rcalc = new RCalc();
	/** Table of user defined variables */
	private HashMap<String, Fraction> symbolTable = new HashMap<String, Fraction>();


	@Override
	public boolean isDebug() {

		return debug;
	}


	@Override
	public void setDebug(boolean debug) {

		rcalc.setDebug(debug);
		this.debug = debug;
	}


	/**
	 * Get variable value
	 * 
	 * @param name variable name
	 * @return the value
	 */
	public Fraction getVariable(String name) {

		return symbolTable.get(name.toLowerCase());
	}


	/**
	 * Set a variable value
	 * 
	 * @param name variable name
	 * @param value value to set
	 */
	public void setVariable(String name, Fraction value) {

		symbolTable.put(name.trim(), value);
	}


	/**
	 * Evaluate an expression
	 * 
	 * @param expression expression
	 * @return result
	 */
	public Fraction evaluate(String expression) {

		expression = expression.trim().toLowerCase();

		if (expression.length() == 0) return null; // no operation

		expression = expression.replace(":=", "="); // normalize assignment sign

		String assignedVariable = null;
		String assignmentOperation = "";

		// parse name=value syntax for variables
		if (expression.matches("^[a-z]+\\s*[+\\-*/%^]?=\\s*(.+)$")) {
			Pattern pattern = Pattern.compile("^([a-z]+)\\s*([+\\-*/%^]?=)\\s*(.+)$");
			Matcher matcher = pattern.matcher(expression);

			if (!matcher.find() || matcher.groupCount() != 3) {
				throw new ParseError("Invalid expression format, check your syntax.");
			}

			assignedVariable = matcher.group(1).trim();
			assignmentOperation = matcher.group(2).trim();
			expression = matcher.group(3).trim();
		}

		// replace variable names with their values
		for (Entry<String, Fraction> entry : symbolTable.entrySet()) {
			String regex = "(?:(?<![a-z])|^)" + entry.getKey() + "(?:(?![a-z])|$)";
			String replacement = "(" + entry.getValue() + ")";
			expression = expression.replaceAll(regex, replacement);
		}

		// calculate result
		Fraction result = rcalc.evaluate(expression);

		// assign variable value if requested
		if (assignedVariable != null) {

			Fraction oldValue = symbolTable.get(assignedVariable);

			if (oldValue == null) oldValue = Fraction.ZERO;

			if (assignmentOperation.equals("=")) {
				// no-op

			} else if (assignmentOperation.equals("+=")) {

				result = oldValue.add(result);

			} else if (assignmentOperation.equals("-=")) {

				result = oldValue.subtract(result);

			} else if (assignmentOperation.equals("*=")) {

				result = oldValue.multiply(result);

			} else if (assignmentOperation.equals("/=")) {

				result = oldValue.divide(result);

			} else if (assignmentOperation.equals("%=")) {

				result = oldValue.modulo(result);

			} else if (assignmentOperation.equals("^=")) {

				result = oldValue.power(result);
			}

			symbolTable.put(assignedVariable, result);
		}

		return result;
	}


	/**
	 * Set symbol table (can be used to share it among multiple instances)
	 * 
	 * @param table the symbol table
	 */
	public void setSymbolTable(HashMap<String, Fraction> table) {

		this.symbolTable = table;
	}


	/**
	 * Get the current symbol table
	 * 
	 * @return symbol table
	 */
	public HashMap<String, Fraction> getSymbolTable() {

		return symbolTable;
	}


}
