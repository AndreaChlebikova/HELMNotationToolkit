package org.helm.notation.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class with methods for returning subsets of interest based on indices.
 * 
 * @author Andrea Chlebikova
 *
 */

public class MatchingTools {

	/**
	 * Returns subset with HELM strings of complex polymers of interest.
	 * 
	 * @param indicesOfInterest
	 *            {@link Set} of {@link Integer}s denoting indices of complex
	 *            polymers of interest (e.g. containing specific subsequence) in
	 *            notationList
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link Set} of HELM {@link String}s, subset of complex polymers -
	 *         those of interest
	 */

	public static Set<String> matchNotation(Set<Integer> indicesOfInterest,
			List<String> notationList) {
		Set<String> notationsOfInterest = new HashSet<String>();
		for (Integer index : indicesOfInterest) {
			notationsOfInterest.add(notationList.get(index));
		}
		return notationsOfInterest;
	}

	/**
	 * Returns sublist with HELM strings of complex polymers of interest.
	 * 
	 * @param indicesOfInterest
	 *            {@link List} of {@link Integer}s denoting indices of complex
	 *            polymers of interest (e.g. containing specific subsequence) in
	 *            notationList
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of HELM {@link String}s, subset of complex polymers
	 *         - those of interest
	 */

	public static List<String> matchNotation(List<Integer> indicesOfInterest,
			List<String> notationList) {
		List<String> notationsOfInterest = new ArrayList<String>();
		for (Integer index : indicesOfInterest) {
			notationsOfInterest.add(notationList.get(index));
		}
		return notationsOfInterest;
	}

	/**
	 * Returns sublist of interest of sets of sequences.
	 * 
	 * @param indicesOfInterest
	 *            {@link List} of {@link Integer}s denoting indices of interest
	 *            in sequenceList
	 * @param sequenceList
	 *            {@link List} of {@link Set}s of {@link Sequence}s
	 * @return {@link List} of {@link Set}s of {@link Sequence}s of interest
	 */

	public static List<Set<Sequence>> matchSequences(
			List<Integer> indicesOfInterest, List<Set<Sequence>> sequenceList) {
		List<Set<Sequence>> sequencesOfInterest = new ArrayList<Set<Sequence>>();
		for (Integer index : indicesOfInterest) {
			sequencesOfInterest.add(sequenceList.get(index));
		}
		return sequencesOfInterest;
	}

	/**
	 * Returns sublist of interest of sets of SMILES strings.
	 * 
	 * @param indicesOfInterest
	 *            {@link List} of {@link Integer}s denoting indices of complex
	 *            polymers of interest (e.g. containing specific subsequence) in
	 *            notationList
	 * @param smilesList
	 *            {@link List} of {@link Set}s of SMILES {@link String}s
	 * @return {@link List} of {@link Set}s of SMILES {@link String}s of
	 *         interest
	 */

	public static List<Set<String>> matchSmiles(
			List<Integer> indicesOfInterest, List<Set<String>> smilesList) {
		List<Set<String>> smilesOfInterest = new ArrayList<Set<String>>();
		for (Integer index : indicesOfInterest) {
			smilesOfInterest.add(smilesList.get(index));
		}
		return smilesOfInterest;
	}
}
