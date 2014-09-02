/**
 * 
 */
package org.helm.notation.search;

/**
 * Exception thrown for erroneous boolean expression to be parsed.
 * 
 * @author Andrea Chlebikova
 *
 */
public class ExpressionException extends Exception {
	
	public ExpressionException(String message) {
		super(message);
	}
}
