package net.mightypork.rcalc;


import java.util.ArrayList;
import java.util.Stack;

import net.mightypork.rcalc.operations.Operation;
import net.mightypork.rcalc.tokens.*;


/**
 * A list of tokens which can be parsed into a single IEvaluable
 * 
 * @author Ondrej Hruska
 */
public class TokenList extends ArrayList<IToken> implements IToken {

	/**
	 * Create a TokenList with defined initial size
	 * 
	 * @param i initial size
	 */
	public TokenList(int i) {

		super(i);
	}


	/**
	 * Create a blank TokenList
	 */
	public TokenList() {

		super();
	}


	/**
	 * Parse the token list.<br>
	 * After running this method, the list is typically reduced to a single
	 * token
	 * 
	 * @return the remaining token
	 */
	public IEvaluableToken parse() {

		try {
			// 1. Extract all parentheses into TokenLists
			extractParentheses();

			// 2. Convert operator tokens to operators, including their arguments
			//    in the correct order ^ * / + -
			extractOperator(TokenOperatorFactorial.class);
			extractOperator(TokenOperatorPower.class);
			extractOperator(TokenOperatorMultiply.class);
			extractOperator(TokenOperatorDivide.class);
			extractOperator(TokenOperatorModulo.class);
			extractOperator(TokenOperatorAdd.class);
			extractOperator(TokenOperatorSubtract.class);

			// 3. Check for leftovers, throw error if any
			if (this.size() > 1) {
				throw new ParseError("TokenList didn't parse entirely, probably a syntax error: " + this);
			}

			// 4. If last token is a TokenList, use its content instead
			if (this.get(0) instanceof TokenList) {
				TokenList theList = (TokenList) this.get(0);
				if (theList.size() == 1 && (theList.get(0) instanceof IEvaluableToken)) {
					IEvaluableToken eval = (IEvaluableToken) theList.get(0);
					this.clear();
					this.add(eval);
				}
			}

			// 5. Throw error if last token remaining is not evaluable
			if (!(this.get(0) instanceof IEvaluableToken)) {
				throw new ParseError("Last token in a TokenList is not evaluable: " + this.get(0));
			}

		} catch (ParseError e) {
			throw e;
		} catch (IndexOutOfBoundsException e) {
			throw new ParseError("Missing operand(s).");
		} catch (Exception e) {
			throw new ParseError(e);
		}

		return (IEvaluableToken) this.get(0); // checked earlier
	}


	/**
	 * Find tokens of given operator class, and convert them to the operations
	 * including their operands.
	 * 
	 * @param operatorClass class of the operator to find
	 */
	private void extractOperator(Class<? extends IOperatorToken> operatorClass) {

		Stack<Integer> positions = new Stack<Integer>();

		// find operator positions
		for (int i = size() - 1; i >= 0; i--) {
			if (operatorClass.isInstance(get(i))) {
				positions.push(i);
			}
		}

		// offset, used when positions are updated while replacing tokens
		int offset = 0;

		while (!positions.isEmpty()) {

			// get position of the next token
			int pos = positions.pop() + offset;

			IToken operatorToken = get(pos);

			if (operatorToken instanceof TokenBinaryOperator) {

				TokenBinaryOperator opToken = (TokenBinaryOperator) operatorToken;

				// variables for tokenLists of the operands
				TokenList leftTL, rightTL;

				// get left operand
				IToken leftArg = get(pos - 1);
				if (leftArg instanceof TokenList) {
					leftTL = (TokenList) leftArg;
				} else {
					leftTL = new TokenList(1);
					leftTL.add(leftArg);
				}

				// get right operand
				IToken rightArg = get(pos + 1);
				if (rightArg instanceof TokenList) {
					rightTL = (TokenList) rightArg;
				} else {
					rightTL = new TokenList(1);
					rightTL.add(rightArg);
				}

				// build an operation
				Operation op = opToken.toOperation(leftTL, rightTL);

				// discard used tokens
				subList(pos - 1, pos + 2).clear();
				// put back the operation
				add(pos - 1, op);
				// shift offset
				offset -= 2;

			} else if (operatorToken instanceof TokenUnaryOperatorLeft) {

				TokenUnaryOperatorLeft opToken = (TokenUnaryOperatorLeft) operatorToken;

				// variable for left operand
				TokenList leftTL;

				// get left operand (the only one)
				IToken leftArg = get(pos - 1);
				if (leftArg instanceof TokenList) {
					leftTL = (TokenList) leftArg;
				} else {
					leftTL = new TokenList(1);
					leftTL.add(leftArg);
				}

				// build an operation
				Operation op = opToken.toOperation(leftTL);

				// discard used tokens
				subList(pos - 1, pos + 1).clear();
				// put back the operation
				add(pos - 1, op);
				// shift offset
				offset -= 1;

			} else if (operatorToken instanceof TokenUnaryOperatorRight) {

				TokenUnaryOperatorRight opToken = (TokenUnaryOperatorRight) operatorToken;

				// variable for left operand
				TokenList rightTL;

				// get right operand (the only one)
				IToken rightArg = get(pos + 1);
				if (rightArg instanceof TokenList) {
					rightTL = (TokenList) rightArg;
				} else {
					rightTL = new TokenList(1);
					rightTL.add(rightArg);
				}

				// build an operation
				Operation op = opToken.toOperation(rightTL);

				// discard used tokens
				subList(pos, pos + 2).clear();
				// put back the operation
				add(pos, op);
				// shift offset
				offset -= 1;
			}
		}
	}


	/**
	 * Convert outer parenthesized blocks to individual tokens
	 */
	private void extractParentheses() {

		int open = 0; // level of outer parentheses
		int openIgnored = 0; // level of inner parentheses, to be skipped

		Stack<Integer> lefts = new Stack<Integer>();
		Stack<Integer> rights = new Stack<Integer>();

		for (int i = 0; i < size(); i++) {

			if (get(i) instanceof TokenParenthesisLeft) {

				if (open == 0) {
					open++;
					lefts.push(i);
				} else {
					openIgnored++;
				}
			}

			if (get(i) instanceof TokenParenthesisRight) {

				if (openIgnored > 0) {
					openIgnored--;
				} else {
					open--;
					if (open == 0) rights.push(i);
				}
			}
		}

		if (open > 0) throw new ParseError("Unbalanced parentheses.");

		// extract token lists inside the outer parentheses, put them back as single tokens
		while (!lefts.isEmpty()) {
			int left = lefts.pop();
			int right = rights.pop();

			int innerLeft = left + 1;
			int innerRight = right - 1;

			// prepare tokenList for the inner tokens
			TokenList insides = new TokenList();
			// add the inner tokens
			insides.addAll(subList(innerLeft, innerRight + 1));
			// destroy the whole range including parentheses
			subList(left, right + 1).clear();
			// put replacement token back
			add(left, insides.parse());
		}

	}


}
