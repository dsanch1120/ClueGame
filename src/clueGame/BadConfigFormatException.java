/*
 * Created by Daniel Sanchez and Trent Douglas
 */
package clueGame;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		super("Invalid Rows and/or Columns");
	}
	
	public BadConfigFormatException(String s){
		super(s);
	}

}
