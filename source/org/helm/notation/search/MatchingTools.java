package org.helm.notation.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Andrea Chlebikova
 *
 */


public class MatchingTools {
	
	/**
	 * Returns sublist with HELM strings of complex polymers of interest.
	 * 
	 * @param indicesOfInterest List of {@link Integer}s denoting indices of complex polymers of interest (e.g. containing specific subsequence) in notationList
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of HELM {@link String}s, subset of complex polymers - those of interest
	 */
	
	public static List<String> matchNotation(List<Integer> indicesOfInterest, List<String> notationList) {
		List<String> notationsOfInterest = new ArrayList();
		for (Integer index : indicesOfInterest) {
			notationsOfInterest.add(notationList.get(index));
		}
		return notationsOfInterest;
	}	
}
