package net.mightypork.rcalc;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.mightypork.rcalc.numbers.Fraction;
import net.mightypork.rcalc.tokens.*;


/**
 * Utility for converting an expression from String to a list of tokens
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Tokenizer implements IDebugable {

	/** Debug mode flag */
	private boolean debug = false;

	/** Deep debug mode flag */
	private boolean deepDebug = false;


	@Override
	public boolean isDebug() {

		return debug;
	}


	@Override
	public void setDebug(boolean debug) {

		this.debug = debug;
	}


	/**
	 * Get if deep debug mode is enabled
	 * 
	 * @return is in deep debug mode
	 */
	public boolean isDeepDebug() {

		return deepDebug && debug;
	}


	/**
	 * Enable / disable deep debug mode
	 * 
	 * @param debug
	 */
	public void setDeepDebug(boolean debug) {

		this.deepDebug = this.debug = debug;
	}


	/**
	 * Parse a string to a list of expression tokens
	 * 
	 * @param input string with expression to parse
	 * @return token list
	 */
	public TokenList tokenize(String input) {

		// discard whitespace
		input = input.replaceAll("\\s", "");

		// check parentheses
		int level = 0;
		for (char c : input.toCharArray()) {
			if (c == '(') level++;
			if (c == ')') level--;

			if (level < 0) throw new ParseError("Unbalanced parentheses.");
		}
		if (level != 0) throw new ParseError("Unbalanced parentheses.");

		// translate ** to ^
		input = input.replaceAll("[*]{2}", "^");
		if (debug && deepDebug) System.out.println("\nTranslate '**' to '^'\n" + input);

		// enclose ^ left operand in parentheses
		input = input.replaceAll("([0-9.]+[!]?)\\^", "($1)^");
		if (debug && deepDebug) System.out.println("\nWrap left numeric operand of '^' with parentheses\n" + input);

		// add multiplication operator for implicit multiplication
		input = input.replaceAll("(?<=[0-9)!])(?=[(])", "*"); // )( -> )*(
		input = input.replaceAll("(?<=[)!])(?=[0-9(])", "*");
		if (debug && deepDebug) System.out.println("\nApply implicit multiplication\n" + input);

		// normalize series of '+' and '-'
		StringBuffer outputBuffer = new StringBuffer();
		Matcher matcher = Pattern.compile("([+\\-]{2,})").matcher(input);
		while (matcher.find()) {
			int minus = 0;
			for (char c : matcher.group().toCharArray()) {
				if (c == '-') minus++;
			}
			minus = minus % 2;
			matcher.appendReplacement(outputBuffer, minus == 0 ? "+" : "-");
		}
		matcher.appendTail(outputBuffer);
		input = outputBuffer.toString();
		if (debug && deepDebug) System.out.println("\nNormalize sequences of '+' and '-'\n" + input);

		// turn '-' to '+-' between numbers
		input = input.replaceAll("(?<=[0-9)!])-(?=[0-9.])", "+-");
		if (debug && deepDebug) System.out.println("\n'-' to '+-' between numbers\n" + input);

		// convert '-' to '+-1*' in front of non-numbers
		input = input.replaceAll("-(?=[^0-9.])", "+-1*");
		if (debug && deepDebug) System.out.println("\n'-' to '+-1*' in front of non-numbers\n" + input);

		// discard useless + signs
		input = input.replaceAll("(?<=[^)0-9!]|^)\\+", "");
		if (debug && deepDebug) System.out.println("\nDiscard '+' at beginning of scope\n" + input);

		if (debug) System.out.println("\nParsing tokens...");

		TokenList list = new TokenList();

		String buffer = "";

		boolean parsingNumber = false;

		for (Character c : input.toCharArray()) {
			if (c.toString().matches("[\\-0-9.]")) {
				buffer += c;
				parsingNumber = true;
			} else {

				if (parsingNumber) {
					list.add(new Fraction(buffer));
					buffer = "";
					parsingNumber = false;
				}

				switch (c) {
					case '(':
						list.add(new TokenParenthesisLeft());
						break;
					case ')':
						list.add(new TokenParenthesisRight());
						break;
					case '*':
						list.add(new TokenOperatorMultiply());
						break;
					case '/':
						list.add(new TokenOperatorDivide());
						break;
					case '+':
						list.add(new TokenOperatorAdd());
						break;
					case '!':
						list.add(new TokenOperatorFactorial());
						break;
					case '%':
						list.add(new TokenOperatorModulo());
						break;
					case '^':
						list.add(new TokenOperatorPower());
						break;
					default:
						throw new ParseError("Unknown token " + c);
				}

			}
		}

		// include possible last number
		if (parsingNumber) {
			list.add(new Fraction(buffer));
		}

		if (debug) System.out.println("\nToken list:\n" + list);


		return list;
	}
}
