package org.helm.notation.search;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * This class provides methods which integrate the sequence and substructure
 * search elements, using a {@link Query} object as input.
 * 
 * @author Andrea Chlebikova
 *
 */
public class CombinedSearch {

	/**
	 * Performs search starting from list of HELM strings.
	 * 
	 * @param q
	 *            Search {@link Query}
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link Matches} to search query
	 */

	public static Matches performSearch(Query q, List<String> notationList) {
		Matches matches = new Matches();
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		if (q.sequence) {
			indicesOfInterest = SequenceSearch.findMatchingCompounds(q.peptide,
					q.queryString, notationList);
			if (q.negation) {
				Set<Integer> actualIndicesOfInterest = new HashSet<Integer>();
				for (int i = 0; i < notationList.size(); i++) {
					actualIndicesOfInterest.add(i);
				}
				actualIndicesOfInterest.removeAll(indicesOfInterest);
				matches.indicesOfInterest = actualIndicesOfInterest;
				return matches;
			}
			matches.indicesOfInterest = indicesOfInterest;
			return matches;
		} else {
			switch (q.smilesLevel) {
			case 'c':
				List<String> smilesList = ChemSearch
						.generateSmilesStrings(notationList);
				if (smilesList.contains("*")) { // Will be better to build this
					// into the above function
					matches.smilesWarningFlag = true;
				}
				indicesOfInterest = ChemSearch.findMatchingCompounds(
						q.queryString, smilesList);
				break;
			case 's':
				List<Set<String>> allSimpleList = ChemSearch
						.isolateAllSimpleSmiles(notationList);
				for (Set<String> set : allSimpleList) {
					if (set.contains("*")) {
						matches.smilesWarningFlag = true;
					}
				}
				indicesOfInterest = ChemSearch.findMatchingCompounds2(
						q.queryString, allSimpleList);
				break;
			case 'm':
				List<Set<String>> allMonomerList = ChemSearch
						.isolateAllMonomerSmiles(notationList);
				for (Set<String> set : allMonomerList) {
					if (set.contains("*")) {
						matches.smilesWarningFlag = true;
					}
				}
				indicesOfInterest = ChemSearch.findMatchingCompounds2(
						q.queryString, allMonomerList);
				break;
			case 'h':
				List<Set<String>> chemList = ChemSearch
						.isolateChemSmiles(notationList);
				for (Set<String> set : chemList) {
					if (set.contains("*")) {
						matches.smilesWarningFlag = true;
					}
				}
				indicesOfInterest = ChemSearch.findMatchingCompounds2(
						q.queryString, chemList);
				break;
			case 'p':
				List<Set<String>> peptideList = ChemSearch
						.isolatePeptideSmiles(notationList);
				for (Set<String> set : peptideList) {
					if (set.contains("*")) {
						matches.smilesWarningFlag = true;
					}
				}
				indicesOfInterest = ChemSearch.findMatchingCompounds2(
						q.queryString, peptideList);
				break;
			case 'r':
				List<Set<String>> rnaList = ChemSearch
						.isolateRnaSmiles(notationList);
				for (Set<String> set : rnaList) {
					if (set.contains("*")) {
						matches.smilesWarningFlag = true;
					}
				}
				indicesOfInterest = ChemSearch.findMatchingCompounds2(
						q.queryString, rnaList);
				break;
			case 'a':
				List<Set<String>> aaList = ChemSearch
						.isolateAminoAcidSmiles(notationList);
				for (Set<String> set : aaList) {
					if (set.contains("*")) {
						matches.smilesWarningFlag = true;
					}
				}
				indicesOfInterest = ChemSearch.findMatchingCompounds2(
						q.queryString, aaList);
				break;
			case 'n':
				List<Set<String>> nucleotideList = ChemSearch
						.isolateNucleotideSmiles(notationList);
				for (Set<String> set : nucleotideList) {
					if (set.contains("*")) {
						matches.smilesWarningFlag = true;
					}
				}
				indicesOfInterest = ChemSearch.findMatchingCompounds2(
						q.queryString, nucleotideList);
				break;
			case 'b':
				List<Set<String>> rnaMonomerList = ChemSearch
						.isolateRnaMonomerSmiles(notationList);
				for (Set<String> set : rnaMonomerList) {
					if (set.contains("*")) {
						matches.smilesWarningFlag = true;
					}
				}
				indicesOfInterest = ChemSearch.findMatchingCompounds2(
						q.queryString, rnaMonomerList);
				break;
			}
			if (q.negation) {
				Set<Integer> actualIndicesOfInterest = new HashSet<Integer>();
				for (int i = 0; i < notationList.size(); i++) {
					actualIndicesOfInterest.add(i);
				}
				actualIndicesOfInterest.removeAll(indicesOfInterest);
				matches.indicesOfInterest = actualIndicesOfInterest;
				return matches;
			}
			matches.indicesOfInterest = indicesOfInterest;
			return matches;
		}
	}

	/**
	 * Performs search starting from list of SMILES strings of complex polymers.
	 * 
	 * @param q
	 *            Search {@link Query}
	 * @param relevantList
	 *            {@link List} of SMILES {@link String}s denoting complex
	 *            polymers
	 * @return {@link Matches} to search query
	 */

	public static Matches performPartialSearch(Query q,
			List<String> relevantList) {
		Matches matches = new Matches();
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		if (q.sequence) {
			return matches;
		} else {
			switch (q.smilesLevel) {
			case 'c':
				indicesOfInterest = ChemSearch.findMatchingCompounds(
						q.queryString, relevantList);
				break;
			default:
				break;
			}
			if (q.negation) {
				Set<Integer> actualIndicesOfInterest = new HashSet<Integer>();
				for (int i = 0; i < relevantList.size(); i++) {
					actualIndicesOfInterest.add(i);
				}
				actualIndicesOfInterest.removeAll(indicesOfInterest);
				matches.indicesOfInterest = actualIndicesOfInterest;
				return matches;
			}
			matches.indicesOfInterest = indicesOfInterest;
			if (relevantList.contains("*")) {
				matches.smilesWarningFlag = true;
			}
			return matches;
		}
	}

	/**
	 * Performs search starting from list of sets of SMILES strings of the
	 * relevant parts.
	 * 
	 * @param q
	 *            Search {@link Query}
	 * @param relevantList
	 *            {@link List} of {@link Set}s of SMILES {@link String}s
	 *            denoting the relevant parts of the complex polymers
	 * @return {@link Matches} to search query
	 */

	public static Matches performPartialSearch2(Query q,
			List<Set<String>> relevantList) {
		Matches matches = new Matches();
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		if (q.sequence) {
			return matches;
		} else {
			switch (q.smilesLevel) {
			case 's':
			case 'm':
			case 'h':
			case 'p':
			case 'r':
			case 'a':
			case 'n':
			case 'b':
				indicesOfInterest = ChemSearch.findMatchingCompounds2(
						q.queryString, relevantList);
				for (Set<String> set : relevantList) {
					if (set.contains("*")) {
						matches.smilesWarningFlag = true;
					}
				}
				break;
			default:
				break;
			}
			if (q.negation) {
				Set<Integer> actualIndicesOfInterest = new HashSet<Integer>();
				for (int i = 0; i < relevantList.size(); i++) {
					actualIndicesOfInterest.add(i);
				}
				actualIndicesOfInterest.removeAll(indicesOfInterest);
				matches.indicesOfInterest = actualIndicesOfInterest;
				return matches;
			}
			matches.indicesOfInterest = indicesOfInterest;
			return matches;
		}
	}

	/**
	 * Performs search starting from list of sets of the relevant kind of
	 * sequences.
	 * 
	 * @param q
	 *            Search {@link Query}
	 * @param relevantList
	 *            {@link List} of {@link Set}s of the relevant kind of
	 *            {@link Sequence}s
	 * @return {@link Matches} to search query
	 */

	public static Matches performPartialSearch3(Query q,
			List<Set<Sequence>> relevantList) {
		Matches matches = new Matches();
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		if (q.sequence) {
			if (q.peptide) {
				Pattern regex = SequenceSearch
						.peptideStringToRegex(q.queryString);
				indicesOfInterest = SequenceSearch
						.findPeptideMatchingCompounds(regex, relevantList);
			} else {
				Pattern regex = SequenceSearch.rnaStringToRegex(q.queryString);
				indicesOfInterest = SequenceSearch.findRnaMatchingCompounds(
						regex, relevantList);
			}
			if (q.negation) {
				Set<Integer> actualIndicesOfInterest = new HashSet<Integer>();
				for (int i = 0; i < relevantList.size(); i++) {
					actualIndicesOfInterest.add(i);
				}
				actualIndicesOfInterest.removeAll(indicesOfInterest);
				matches.indicesOfInterest = actualIndicesOfInterest;
				return matches;
			}
			matches.indicesOfInterest = indicesOfInterest;
			return matches;
		} else {
			return matches;
		}
	}

	/**
	 * Method to combine the {@link Matches} from two separate queries, either
	 * by intersection or union.
	 * 
	 * @param m1
	 *            {@link Matches} from first query
	 * @param m2
	 *            {@link Matches} from second query
	 * @param and
	 *            {@link Boolean} - true if combine by intersection, false if
	 *            combine by union
	 * @return {@link Matches} for combination query
	 */

	public static Matches combineSearches(Matches m1, Matches m2, Boolean and) {
		Matches matches = new Matches();
		matches.smilesWarningFlag = m1.smilesWarningFlag
				|| m2.smilesWarningFlag;
		matches.timeoutWarningFlag = m1.timeoutWarningFlag
				|| m2.timeoutWarningFlag;
		if (and) { // intersection
			matches.indicesOfInterest = new HashSet<Integer>(
					m1.indicesOfInterest);
			matches.indicesOfInterest.retainAll(m2.indicesOfInterest);

		} else { // union
			matches.indicesOfInterest = new HashSet<Integer>(
					m1.indicesOfInterest);
			matches.indicesOfInterest.addAll(m2.indicesOfInterest);
		}
		return matches;
	}
}
