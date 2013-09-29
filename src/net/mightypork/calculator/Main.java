package net.mightypork.calculator;


import java.io.File;


/**
 * Application main class
 * 
 * @author Ondrej Hruska
 */
public class Main {

	/**
	 * Program entry point
	 * 
	 * @param args command line arguemnts
	 */
	public static void main(String[] args) {

		if (args.length == 0 || (args.length == 1 && args[0].equals("-i"))) {

			Runnable task = new CalculatorInteractive();
			task.run();

		} else if (args.length == 1) {

			Runnable task = new CalculatorBatch(new File(args[0]));
			task.run();

		}

	}


}
