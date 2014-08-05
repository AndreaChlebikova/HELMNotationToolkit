package org.helm.notation.search;

/**
 * @author Andrea Chlebikova
 *
 */
public class Sequence {
	public String sequence;
	public Boolean cyclic = false; // denotes whether sequence is
									// backbone-cyclic

	public Sequence(String s) {
		sequence = s;
	}

	public Sequence(String s, Boolean c) {
		sequence = s;
		cyclic = c;
	}
}
