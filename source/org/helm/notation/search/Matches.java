package org.helm.notation.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * The Matches class represents the search results for exact search queries.
 * 
 * @author Andrea Chlebikova
 *
 */
public class Matches {
	/** {@link Set} of indices of polymers which match the query. */
	public Set<Integer> indicesOfInterest = new HashSet<Integer>();
	/**
	 * {@link boolean} denoting whether some SMILES strings failed to be
	 * generated
	 */
	public boolean smilesWarningFlag = false;
	/** {@link boolean} denoting whether the search was interrupted */
	public boolean timeoutWarningFlag = false;
	/**
	 * {@link List} of HELM {@link String}s denoting complex polymers on which
	 * search was performed
	 */
	public List<String> notationList = new ArrayList<String>();
	
	/**
	 * Method that generates text description of any warnings that apply to the
	 * search results.
	 * 
	 * @return {@link String}, the warnings description
	 */

	public String text() {
		String text = "";
		if (timeoutWarningFlag) {
			text = "The search was interrupted early, so no results can be displayed.\n\n";
		} else if (smilesWarningFlag) {
			text = "Warning: The SMILES strings were not generated for all compounds to be searched, so some results may be missing.\n\n";
		}
		return text;
	}

	/**
	 * Method that generates text containing the indices of matches.
	 * 
	 * @return {@link String} with search result indices
	 */

	public String indices() {
		String indices = "";
		Set<Integer> sorted = new TreeSet<Integer>(indicesOfInterest);
		if (timeoutWarningFlag) {
			indices = "No results available";
		} else {
			for (Integer index : sorted) {
				indices = indices + index + "\n";
			}
		}
		return indices;
	}

	/**
	 * Method that generates text containing the HELM notations of matches.
	 * 
	 * @return {@link String} with search result HELM notations
	 */

	public String helms() {
		String helms = "";
		Set<Integer> sorted = new TreeSet<Integer>(indicesOfInterest);
		Set<String> sortedHelms = MatchingTools.matchNotation(sorted,
				notationList);
		if (timeoutWarningFlag) {
			helms = "No results available";
		} else {
			for (String helm : sortedHelms) {
				helms = helms + helm + "\n";
			}
		}
		return helms;
	}

	/**
	 * Method that generates text containing the indices and HELM notations of
	 * matches.
	 * 
	 * @return {@link String} with search result indices and HELM notations
	 */

	public String indicesAndHelms() {
		String indicesAndHelms = "";
		List<Integer> sorted = new ArrayList<Integer>(new TreeSet<Integer>(
				indicesOfInterest));
		List<String> sortedHelms = MatchingTools.matchNotation(sorted,
				notationList);
		if (timeoutWarningFlag) {
			indicesAndHelms = "No results available";
		} else {
			for (int i = 0; i < sorted.size(); i++) {
				indicesAndHelms = indicesAndHelms + sorted.get(i) + ", "
						+ sortedHelms.get(i) + "\n";
			}
		}
		return indicesAndHelms;
	}
}