package net.mightypork.rcalc.numbers;


import java.math.BigInteger;

import net.mightypork.rcalc.IEvaluableToken;
import net.mightypork.rcalc.ParseError;
import net.mightypork.rcalc.TokenList;


/**
 * A fractional number with methods for basic arithmetics
 * 
 * @author Ondrej Hruska
 */
public class Fraction implements IEvaluableToken {

	/** Zero fraction (0/1) */
	public static final Fraction ZERO = new Fraction(BigInteger.ZERO);
	/** One fraction (1/1) */
	public static final Fraction ONE = new Fraction(BigInteger.ZERO);


	private BigInteger numerator = BigInteger.ZERO;
	private BigInteger denominator = BigInteger.ONE;


	/**
	 * Create fraction from numerator and 1 as denominator
	 * 
	 * @param numerator numerator
	 */
	public Fraction(long numerator) {

		this.numerator = BigInteger.valueOf(numerator);
		this.denominator = BigInteger.ONE;
	}


	/**
	 * Create fraction from numerator and 1 as denominator
	 * 
	 * @param numerator numerator
	 */
	public Fraction(BigInteger numerator) {

		this.numerator = numerator;
		this.denominator = BigInteger.ONE;
	}


	/**
	 * Create fraction from numerator and denominator
	 * 
	 * @param numerator numerator
	 * @param denominator denominator
	 */
	public Fraction(long numerator, long denominator) {

		if (denominator == 0) {
			throw new ArithmeticException("Division by zero.");
		}

		this.numerator = BigInteger.valueOf(numerator);
		this.denominator = BigInteger.valueOf(denominator);
	}


	/**
	 * Create fraction from numerator and denominator
	 * 
	 * @param numerator numerator
	 * @param denominator denominator
	 */
	public Fraction(BigInteger numerator, BigInteger denominator) {

		if (denominator.equals(BigInteger.ZERO)) {
			throw new ArithmeticException("Division by zero.");
		}

		this.numerator = numerator;
		this.denominator = denominator;
	}


	/**
	 * Create a fraction as a copy of another
	 * 
	 * @param other other fraction to copy
	 */
	public Fraction(Fraction other) {

		this.numerator = other.numerator;
		this.denominator = other.denominator;
	}


	/**
	 * Create a fraction with numerator parsed from a string, and 1 as
	 * denominator.
	 * 
	 * @param number numerator as string
	 */
	public Fraction(String number) {

		if (number.matches("[.][0-9]+")) {
			number = "0" + number;
		}

		if (number.matches("-[.][0-9]+")) {
			number = "-0" + number.substring(1);
		}

		if (number.matches("-?[0-9]+[.][0-9]+")) {
			String[] parts = number.split("[.]");
			try {
				this.numerator = new BigInteger(parts[0] + parts[1]);
				this.denominator = BigInteger.valueOf(10).pow(parts[1].length());
				this.simplify_ip();
			} catch (Exception e) {
				throw new ParseError("Invalid number format.");
			}
		} else {
			this.numerator = new BigInteger(number);
			this.denominator = BigInteger.ONE;
		}
	}


	/**
	 * Get numerator number
	 * 
	 * @return numerator
	 */
	public BigInteger getNumerator() {

		return numerator;
	}


	/**
	 * Get denominator number
	 * 
	 * @return numerator
	 */
	public BigInteger getDenominator() {

		return numerator;
	}


	/**
	 * Add a number
	 * 
	 * @param operand addend
	 * @return this + addend
	 */
	public Fraction add(Fraction operand) {

		Fraction a = this.getCopy();
		Fraction b = operand.getCopy();

		a.numerator = a.numerator.multiply(b.denominator);
		b.numerator = b.numerator.multiply(a.denominator);
		a.denominator = a.denominator.multiply(b.denominator);

		a.numerator = a.numerator.add(b.numerator);

		return a.simplify();
	}


	/**
	 * Subtract a number
	 * 
	 * @param operand subtrahend
	 * @return this - subtrahend
	 */
	public Fraction subtract(Fraction operand) {

		Fraction negated = new Fraction(operand.numerator.negate(), operand.denominator);
		return this.add(negated);
	}


	/**
	 * Multiply by a number
	 * 
	 * @param operand multiplier
	 * @return this * multiplier
	 */
	public Fraction multiply(Fraction operand) {

		BigInteger n = numerator.multiply(operand.numerator);
		BigInteger d = denominator.multiply(operand.denominator);
		return (new Fraction(n, d)).simplify();
	}


	/**
	 * Divide by a number
	 * 
	 * @param operand divisor
	 * @return this / divisor
	 */
	public Fraction divide(Fraction operand) {

		Fraction flipped = new Fraction(operand.denominator, operand.numerator);
		return this.multiply(flipped);
	}


	/**
	 * Modulo with a number
	 * 
	 * @param operand divisor
	 * @return this % divisor
	 */
	public Fraction modulo(Fraction operand) {

		if (isFractional() || operand.isFractional()) {
			throw new ArithmeticException("Modulo is not defined for fractional operands.");
		}

		BigInteger i = this.getBigIntegerValue();
		BigInteger o = operand.getBigIntegerValue();

		return new Fraction(i.mod(o));
	}


	/**
	 * Get an n-th power of the number
	 * 
	 * @param operand power
	 * @return this ^ power
	 */
	public Fraction power(Fraction operand) {

		if (operand.isFractional()) {
			throw new ArithmeticException("Can't calculate fractional power.");
		}

		int power = operand.getIntegerValue();

		boolean swap = power < 0;

		power = Math.abs(power);

		BigInteger n = numerator.pow(power);
		BigInteger d = denominator.pow(power);

		Fraction out = (new Fraction(n, d)).simplify();

		if (swap) out = out.invert();

		return out;
	}


	/**
	 * Get a fraction with swapped numerator and denominator.
	 * 
	 * @return inverted fraction
	 */
	public Fraction invert() {

		if (numerator.equals(BigInteger.ZERO)) {
			throw new ArithmeticException("Division by zero.");
		}

		return new Fraction(denominator, numerator);
	}


	/**
	 * Simplify the fraction
	 * 
	 * @return simplified fraction
	 */
	public Fraction simplify() {

		BigInteger gcd = numerator.gcd(denominator);

		return new Fraction(numerator.divide(gcd), denominator.divide(gcd));
	}


	/**
	 * Simplify the fraction (in place)
	 */
	private void simplify_ip() {

		BigInteger gcd = numerator.gcd(denominator);

		numerator = numerator.divide(gcd);
		denominator = denominator.divide(gcd);
	}


	/**
	 * Check if the number is fractional
	 * 
	 * @return is fractional
	 */
	public boolean isFractional() {

		return !(numerator.mod(denominator)).equals(BigInteger.ZERO);
	}


	/**
	 * Get an integer numeric value
	 * 
	 * @return value
	 * @throws UnsupportedOperationException for fractional numbers
	 */
	public int getIntegerValue() {

		if (isFractional()) {
			throw new ArithmeticException("Can't take integer value of a fractional number.");
		}

		BigInteger a = numerator.divide(denominator);
		if (a.longValue() > Integer.MAX_VALUE) {
			throw new ArithmeticException("Can't convert to integer, number is too big.");
		}

		return a.intValue();
	}


	/**
	 * Get a BigInteger numeric value
	 * 
	 * @return value
	 * @throws UnsupportedOperationException for fractional numbers
	 */
	public BigInteger getBigIntegerValue() {

		if (this.isFractional()) {
			throw new ArithmeticException("Can't take BigInteger value of a fractional number.");
		}

		return numerator.divide(denominator);
	}


	/**
	 * Get a double-precision numeric value
	 * 
	 * @return value
	 */
	public double getDoubleValue() {

		double a = numerator.doubleValue();
		double b = denominator.doubleValue();

		if (Double.isInfinite(a) || Double.isInfinite(b)) {
			throw new ArithmeticException("Can't convert BigInteger to double, number is too big.");
		}

		return a / b;
	}


	/**
	 * Get a identical copy of this fraction
	 * 
	 * @return copy
	 */
	private Fraction getCopy() {

		return new Fraction(this);
	}


	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((denominator == null) ? 0 : denominator.hashCode());
		result = prime * result + ((numerator == null) ? 0 : numerator.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {

		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Fraction other = (Fraction) obj;

		if (denominator == null || other.denominator == null) return false;
		if (numerator == null || other.numerator == null) return false;

		Fraction thisSimplified = this.simplify();
		Fraction otherSimplified = other.simplify();

		return thisSimplified.numerator.equals(otherSimplified.numerator) && thisSimplified.denominator.equals(otherSimplified.denominator);
	}


	/**
	 * Check if the toString() of this fraction equals to the given string
	 * 
	 * @param str compared string
	 * @return equals
	 */
	public boolean equalsToString(String str) {

		return this.toString().equals(str);
	}


	/**
	 * Get a string representation of this fraction in the format
	 * "numerator/denominator", alternatively "numerator" if denominator is
	 * equal to 1.
	 */
	@Override
	public String toString() {

		if (!isFractional()) return numerator.divide(denominator).toString();

		return numerator + "/" + denominator;
	}


	@Override
	public Fraction evaluate() {

		return this;
	}


	/**
	 * Take a factorial of this number, provided it is not fractional.
	 * 
	 * @return the factorial
	 */
	public Fraction factorial() {

		if (this.isFractional()) {
			throw new ArithmeticException("Can't get factorial of a fractional number.");
		}

		BigInteger val = getBigIntegerValue();

		if (val.compareTo(BigInteger.ZERO) < 0) {
			throw new ArithmeticException("Can't get factorial of a negative number.");
		}

		BigInteger result = BigInteger.ONE;
		for (BigInteger cnt = BigInteger.ONE; cnt.compareTo(val) <= 0; cnt = cnt.add(BigInteger.ONE)) {
			result = result.multiply(cnt);
		}

		return new Fraction(result, BigInteger.ONE);
	}


	@Override
	public TokenList wrapInTokenList() {
		return new TokenList(this);
	}

}
