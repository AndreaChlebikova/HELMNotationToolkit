package org.helm.notation.search;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The Matches class represents the search results for exact search queries.
 * 
 * @author Andrea Chlebikova
 *
 */
public class Matches {
	/**
	 * {@link Set} of indices of polymers which match the query.
	 */
	public Set<Integer> indicesOfInterest = new HashSet<Integer>();
	/**
	 * {@link Boolean} denoting whether some SMILES strings failed to be
	 * generated
	 */
	public Boolean smilesWarningFlag = false;
	/**
	 * {@link Boolean} denoting whether the search was interrupted
	 */
	public Boolean timeoutWarningFlag = false;

	public void printout() {
		Set<Integer> sorted = new TreeSet<Integer>(indicesOfInterest);
		System.out.println("Sorted list of matches: " + sorted);
		if (smilesWarningFlag) {
			System.out
					.println("Warning: The SMILES strings were not generated for all compounds to be searched, so some results may be missing.");
		}
		if (timeoutWarningFlag) {
			System.out
					.println("Warning: The search was not completed, so some results may be missing.");
		}
	}
}