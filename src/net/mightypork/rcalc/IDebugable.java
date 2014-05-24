package net.mightypork.rcalc;


/**
 * Interface for classes with optional debug mode
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface IDebugable {

	/**
	 * Get if debug mode is enabled
	 * 
	 * @return debug enabled
	 */
	public boolean isDebug();


	/**
	 * Enable / disable debug mode
	 * 
	 * @param debug enable debug
	 */
	public void setDebug(boolean debug);

}
