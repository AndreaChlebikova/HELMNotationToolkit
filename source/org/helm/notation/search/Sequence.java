package org.helm.notation.search;

/**
 * The Sequence class holds sequence information in the form of a string, and also information about whether the structure is backbone-cyclic.
 * 
 * @author Andrea Chlebikova
 *
 */
public class Sequence {
	
	/**
	 * Sequence as a {@link String} of characters
	 */
	public String sequence;
	/**
	 * Denotes whether sequence is backbone-cyclic in the structure
	 */
	public Boolean cyclic = false;
	
	public Sequence(String s) {
		sequence = s;
	}

	public Sequence(String s, Boolean c) {
		sequence = s;
		cyclic = c;
	}
}
