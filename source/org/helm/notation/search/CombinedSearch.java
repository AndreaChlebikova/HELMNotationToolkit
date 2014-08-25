package org.helm.notation.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.helm.notation.search.Constants.*;

/**
 * This class provides methods which integrate the sequence and substructure
 * search elements, using a {@link Query} object as input.
 * 
 * @author Andrea Chlebikova
 *
 */

public class CombinedSearch {

	private static Matches performSequenceSearch(SequenceQuery query,
			List<String> notationList) {
		Matches matches = new Matches();
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		if (query.sequenceType == SequenceType.PEPTIDE) {
			List<Set<Sequence>> peptideList = SequenceSearch
					.isolatePeptideSequences(notationList);
			Pattern regex = SequenceSearch
					.peptideStringToRegex(query.queryString);
			indicesOfInterest = SequenceSearch.findPeptideMatchingCompounds(
					regex, peptideList);
		} else if (query.sequenceType == SequenceType.NUCLEOTIDE) {
			List<Set<Sequence>> rnaList = SequenceSearch
					.isolateRnaSequences(notationList);
			Pattern regex = SequenceSearch.rnaStringToRegex(query.queryString);
			indicesOfInterest = SequenceSearch.findRnaMatchingCompounds(regex,
					rnaList);
		}
		if (query.negation) {
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

	private static Matches performStructureSearch(StructureQuery query,
			List<String> notationList) {
		Matches matches = new Matches();
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		switch (query.smilesLevel) {
		case COMPLEX:
			SmilesList smilesList = ChemSearch.generateSmilesList(notationList);
			if (smilesList.smilesWarningFlag) {
				matches.smilesWarningFlag = true;
			}
			indicesOfInterest = ChemSearch.findMatchingCompounds(
					query.queryString, smilesList.list);
			break;
		case SIMPLE:
			SmilesSetsList allSimpleList = ChemSearch
					.generateAllSimpleSmiles(notationList);
			if (allSimpleList.smilesWarningFlag) {
				matches.smilesWarningFlag = true;
			}
			indicesOfInterest = ChemSearch.findMatchingCompounds2(
					query.queryString, allSimpleList.list);
			break;
		case MONOMER:
			SmilesSetsList allMonomerList = ChemSearch
					.generateAllMonomerSmiles(notationList);
			if (allMonomerList.smilesWarningFlag) {
				matches.smilesWarningFlag = true;
			}
			indicesOfInterest = ChemSearch.findMatchingCompounds2(
					query.queryString, allMonomerList.list);
			break;
		case CHEM:
			SmilesSetsList chemList = ChemSearch
					.generateChemSmiles(notationList);
			if (chemList.smilesWarningFlag) {
				matches.smilesWarningFlag = true;
			}
			indicesOfInterest = ChemSearch.findMatchingCompounds2(
					query.queryString, chemList.list);
			break;
		case PEPTIDE:
			SmilesSetsList peptideList = ChemSearch
					.generatePeptideSmiles(notationList);
			if (peptideList.smilesWarningFlag) {
				matches.smilesWarningFlag = true;
			}
			indicesOfInterest = ChemSearch.findMatchingCompounds2(
					query.queryString, peptideList.list);
			break;
		case RNA:
			SmilesSetsList rnaList = ChemSearch.generateRnaSmiles(notationList);
			if (rnaList.smilesWarningFlag) {
				matches.smilesWarningFlag = true;
			}
			indicesOfInterest = ChemSearch.findMatchingCompounds2(
					query.queryString, rnaList.list);
			break;
		case AMINOACID:
			SmilesSetsList aaList = ChemSearch
					.generateAminoAcidSmiles(notationList);
			if (aaList.smilesWarningFlag) {
				matches.smilesWarningFlag = true;
			}
			indicesOfInterest = ChemSearch.findMatchingCompounds2(
					query.queryString, aaList.list);
			break;
		case NUCLEOTIDE:
			SmilesSetsList nucleotideList = ChemSearch
					.generateNucleotideSmiles(notationList);
			if (nucleotideList.smilesWarningFlag) {
				matches.smilesWarningFlag = true;
			}
			indicesOfInterest = ChemSearch.findMatchingCompounds2(
					query.queryString, nucleotideList.list);
			break;
		case BASEPHOSPHATESUGAR:
			SmilesSetsList rnaMonomerList = ChemSearch
					.generateRnaMonomerSmiles(notationList);
			if (rnaMonomerList.smilesWarningFlag) {
				matches.smilesWarningFlag = true;
			}
			indicesOfInterest = ChemSearch.findMatchingCompounds2(
					query.queryString, rnaMonomerList.list);
			break;
		}
		if (query.negation) {
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

	private static Matches performSequenceSearch(SequenceQuery query,
			List<String> originalNotationList, Set<Integer> oldIndicesOfInterest) {
		// TODO restriction - check
		List<String> notationList = new ArrayList<String>();
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		List<Integer> indicesList = new ArrayList<Integer>(oldIndicesOfInterest);
		notationList = MatchingTools.matchNotation(indicesList,
				originalNotationList);
		Matches matches = performSequenceSearch(query, notationList);
		// FIX INDICES BACK - //TODO check
		for (Integer index : matches.indicesOfInterest) {
			indicesOfInterest.add(indicesList.get(index));
		}
		matches.indicesOfInterest = indicesOfInterest;
		return matches;
	}

	private static Matches performStructureSearch(StructureQuery query,
			List<String> originalNotationList, Set<Integer> oldIndicesOfInterest) {
		// TODO restriction - check
		List<String> notationList = new ArrayList<String>();
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		List<Integer> indicesList = new ArrayList<Integer>(oldIndicesOfInterest);
		notationList = MatchingTools.matchNotation(indicesList,
				originalNotationList);
		Matches matches = performStructureSearch(query, notationList);
		// FIX INDICES BACK - //TODO check
		for (Integer index : matches.indicesOfInterest) {
			indicesOfInterest.add(indicesList.get(index));
		}
		matches.indicesOfInterest = indicesOfInterest;
		return matches;
	}

	/**
	 * Method to combine the {@link Matches} from separate queries, either
	 * by intersection or union.
	 * 
	 * @param list {@link List} of {@link Matches} to be combined
	 * @param connector {@link Connector} denoting the combination operation
	 * @return {@link Matches} denoting combined results
	 */

	public static Matches combineSearches(List<Matches> list,
			Connector connector) {
		Matches matches = new Matches();
		if (list.isEmpty()) {
			return matches;
		} else {
			for (Matches element : list) {
				if (element.smilesWarningFlag) {
					matches.smilesWarningFlag = true;
				}
				if (element.timeoutWarningFlag) {
					matches.timeoutWarningFlag = true;
				}
			}
			matches.indicesOfInterest = new HashSet<Integer>(
					list.get(0).indicesOfInterest);
			if (connector.equals(Connector.AND)) { // intersection
				for (int i = 1; i < list.size(); i++) {
					matches.indicesOfInterest
							.retainAll(list.get(i).indicesOfInterest); // TODO
					// CHECK,
					// and
					// below
				}

			} else if (connector.equals(Connector.OR)) { // union
				for (int i = 1; i < list.size(); i++) {
					matches.indicesOfInterest
							.addAll(list.get(i).indicesOfInterest);
				}
			}
			return matches;
		}
	}

	/**
	 * Method for carrying out a combined search.
	 * 
	 * @param grouping {@link Grouping} containing queries and search logic
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link Matches} to combined query
	 */

	public static Matches performSearch(Grouping grouping,
			List<String> notationList) {
		grouping = grouping.regroup().optimise();
		boolean complete = false;
		while (!complete) {
			complete = true;
			outer: for (Grouping node : grouping) {
				if (node.isLeaf()) {
					if (node.data.getClass().getName()
							.equals("org.helm.notation.search.SequenceQuery")) {
						// TODO perform sequence search (NB: restrict search
						// based on existing matches in children of parent -
						// check) - but can improve further
						Set<Integer> indicesToSearch = new HashSet<Integer>();
						for (int i = 0; i < notationList.size(); i++) {
							indicesToSearch.add(i);
						}
						if (node.parent != null
								&& node.parent.data.equals(Connector.AND)) {
							for (Grouping child : node.parent.children) {
								if (child.data
										.getClass()
										.getName()
										.equals("org.helm.notation.search.Matches")) {
									Matches newIndices = (Matches) child.data;
									indicesToSearch
											.retainAll(newIndices.indicesOfInterest);
								}
							}
						} else if (node.parent != null
								&& node.parent.data.equals(Connector.OR)) {
							for (Grouping child : node.parent.children) {
								if (child.data
										.getClass()
										.getName()
										.equals("org.helm.notation.search.Matches")) {
									Matches newIndices = (Matches) child.data;
									indicesToSearch
											.removeAll(newIndices.indicesOfInterest);
								}
							}
						}
						node.data = performSequenceSearch(
								(SequenceQuery) node.data, notationList,
								indicesToSearch);
						complete = false;
						break outer;
					} else if (node.data.getClass().getName()
							.equals("org.helm.notation.search.StructureQuery")) {
						// TODO perform structure search (NB: restrict search
						// based on existing matches in children of parent -
						// check) - but can improve further
						Set<Integer> indicesToSearch = new HashSet<Integer>();
						for (int i = 0; i < notationList.size(); i++) {
							indicesToSearch.add(i);
						}
						if (node.parent != null
								&& node.parent.data.equals(Connector.AND)) {
							for (Grouping child : node.parent.children) {
								if (child.data
										.getClass()
										.getName()
										.equals("org.helm.notation.search.Matches")) {
									Matches newIndices = (Matches) child.data;
									indicesToSearch
											.retainAll(newIndices.indicesOfInterest);
								}
							}
						} else if (node.parent != null
								&& node.parent.data.equals(Connector.OR)) {
							for (int i = 0; i < notationList.size(); i++) {
								indicesToSearch.add(i);
							}
							for (Grouping child : node.parent.children) {
								if (child.data
										.getClass()
										.getName()
										.equals("org.helm.notation.search.Matches")) {
									Matches newIndices = (Matches) child.data;
									indicesToSearch
											.removeAll(newIndices.indicesOfInterest);
								}
							}
						}
						node.data = performStructureSearch(
								(StructureQuery) node.data, notationList,
								indicesToSearch);
						complete = false;
						break outer;
					}
				} else {
					boolean canCombine = true;
					for (Grouping child : node.children) {
						if (!child.data.getClass().getName()
								.equals("org.helm.notation.search.Matches")) {
							canCombine = false;
							break;
						}
					}
					if (canCombine) {
						// TODO combine results of children, CHECK
						List<Matches> list = new ArrayList<Matches>();
						for (Grouping child : node.children) {
							list.add((Matches) child.data);
						}
						node.data = combineSearches(list, (Connector) node.data);
						node.children = new LinkedList<Grouping>();
						complete = false;
						break outer;
					}
				}
			}
		}
		return (Matches) grouping.data;
	}

	// private static String createIndent(int depth) { //TODO can delete later -
	// helps with visualising tree
	// StringBuilder sb = new StringBuilder();
	// for (int i = 0; i < depth; i++) {
	// sb.append(' ');
	// }
	// return sb.toString();
	// }
}
