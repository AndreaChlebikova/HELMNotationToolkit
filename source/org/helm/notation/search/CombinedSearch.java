package org.helm.notation.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.helm.notation.search.Constants.*;

/**
 * This class provides methods which integrate the sequence and substructure
 * search elements, using a {@link Query} object as input.
 * 
 * @author Andrea Chlebikova
 *
 */

public class CombinedSearch {

	public static Matches performSequenceSearch(SequenceQuery query,
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

	public static Matches performStructureSearch(StructureQuery query,
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

	public static Matches performSequenceSearch(SequenceQuery query,
			List<String> originalNotationList, Set<Integer> oldIndicesOfInterest) {
		// TODO restriction - check
		List<String> notationList = new ArrayList<String>();
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		List<Integer> indicesList = new ArrayList<Integer>(oldIndicesOfInterest); // not
		// sorted,
		// but
		// elements
		// in
		// fixed
		// order;
		// same
		// as
		// notationList
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

	public static Matches performStructureSearch(StructureQuery query,
			List<String> originalNotationList, Set<Integer> oldIndicesOfInterest) {
		// TODO restriction - check
		List<String> notationList = new ArrayList<String>();
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		List<Integer> indicesList = new ArrayList<Integer>(oldIndicesOfInterest); // not
		// sorted,
		// but
		// elements
		// in
		// fixed
		// order;
		// same
		// as
		// notationList
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
	 * Performs search starting from list of HELM strings.
	 * 
	 * @param q
	 *            Search {@link Query}
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link Matches} to search query
	 */
	@Deprecated
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
				if (smilesList.contains("*")) { // Will be better to build
					// this
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
	@Deprecated
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
	@Deprecated
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
	@Deprecated
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
	@Deprecated
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
	 * 
	 * @param grouping
	 * @param notationList
	 * @return
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

	@Deprecated
	public static Matches performUnoptimisedSearchDemo(CombinedQuery cq,
			List<String> notationList) {
		Matches matches = new Matches();
		List<Matches> partialMatches = new ArrayList<Matches>();
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		for (int j = 0; j < cq.queryList.size(); j++) {
			partialMatches
					.add(performSearch(cq.queryList.get(j), notationList));
			matches.smilesWarningFlag = matches.smilesWarningFlag
					|| partialMatches.get(j).smilesWarningFlag;
		}
		for (int i = 0; i < notationList.size(); i++) {
			String expression = cq.booleanConnectorsList.get(0);
			for (int j = 0; j < cq.queryList.size(); j++) {
				expression = expression
						+ (partialMatches.get(j).indicesOfInterest.contains(i))
						+ cq.booleanConnectorsList.get(j + 1);
			}
			expression = expression.replace("AND", "&&");
			expression = expression.replace("OR", "||");
			expression = expression.replace("NOT", "!");
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			Object result = null;
			try {
				result = engine.eval(expression);
				if ((Boolean) result) {
					indicesOfInterest.add(i);
				}
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		matches.indicesOfInterest = indicesOfInterest;
		matches.notationList = notationList;
		return matches;
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
