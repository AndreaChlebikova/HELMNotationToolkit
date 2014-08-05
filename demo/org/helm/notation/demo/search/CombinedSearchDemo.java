package org.helm.notation.demo.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.helm.notation.search.ChemSearch;
import org.helm.notation.search.CombinedSearch;
import org.helm.notation.search.Matches;
import org.helm.notation.search.MatchingTools;
import org.helm.notation.search.Query;
import org.helm.notation.search.Sequence;
import org.helm.notation.search.SequenceSearch;

/**
 * An illustration of how the different search approaches can be combined in
 * practice.
 * 
 * @author Andrea Chlebikova
 *
 */
public class CombinedSearchDemo {

	static List<Set<String>> chemList = null; // h
	static List<Set<String>> aaList = null; // a
	static List<Set<String>> peptideList = null; // p
	static List<Set<String>> rnaMonomerList = null; // b
	static List<Set<String>> nucleotideList = null; // n
	static List<Set<String>> rnaList = null; // r
	static List<Set<String>> allMonomerList = null; // m
	static List<Set<String>> allSimpleList = null; // s
	static List<String> smilesList = null; // c

	static List<Set<Sequence>> peptideSequenceList = null;
	static List<Set<Sequence>> rnaSequenceList = null;

	public static void main(String[] args) {
		try {
			List<String> notationList = Files.readAllLines(
					Paths.get("test/org/helm/notation/search/HELMStrings.txt"),
					Charset.forName("UTF-8"));
			Matches m = new Matches();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String s = "";
			while (!s.equals("n")) {
				System.out.println("New query? (y/n)");
				while (!s.equals("y") & !s.equals("n")) {
					s = br.readLine();
				}
				if (s.equals("y")) {
					System.out.println("Fresh/Restrict/Extend? (f/r/e)");
					while (!s.equals("f") & !s.equals("r") & !s.equals("e")) {
						s = br.readLine();
					}
					if (s.equals("f")) {
						m = newQuery(notationList);
					} else if (s.equals("r")) {
						m = newQuery(notationList, m.indicesOfInterest);
					} else {
						m = CombinedSearch.combineSearches(m,
								newQuery(notationList), false);
					}
				}
				m.printout();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * A query is entered at runtime; search on full notation list.
	 * 
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link Matches} to query entered
	 */

	public static Matches newQuery(List<String> notationList) {
		try {
			Query q;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String s = "";
			/**
			 * indicates which CombinedSearch function needs to be run (based on
			 * relevantList) - 1 for performPartialSearch, 2 for
			 * performPartialSearch2, 3 for performPartialSearch3
			 */
			int function = 0;
			List relevantList = new ArrayList();
			System.out.println("sEquence/sUbstructure? (e/u)");
			while (!s.equals("e") & !s.equals("u")) {
				s = br.readLine();
			}
			if (s.equals("e")) {
				System.out.println("Peptide/Rna? (p/r)");
				while (!s.equals("p") & !s.equals("r")) {
					s = br.readLine();
				}
				System.out.println("Enter the sequence to be found:");
				String seq = br.readLine();
				if (s.equals("p")) {
					if (peptideSequenceList == null) {
						peptideSequenceList = SequenceSearch
								.isolatePeptideSequences(notationList);
					}
					relevantList = peptideSequenceList;
					function = 3;
					q = new Query(seq, true, true);
				} else {
					if (rnaSequenceList == null) {
						rnaSequenceList = SequenceSearch
								.isolateRnaSequences(notationList);
					}
					relevantList = rnaSequenceList;
					function = 3;
					q = new Query(seq, true, false);
				}
			} else {
				System.out
						.println("Level of SMILES search? (c/s/m/h/p/r/a/n/b)");
				while (!s.equals("c") & !s.equals("s") & !s.equals("m")
						& !s.equals("h") & !s.equals("p") & !s.equals("r")
						& !s.equals("a") & !s.equals("n") & !s.equals("b")) {
					s = br.readLine();
				}
				System.out.println("Enter SMARTS of substructure to be found:");
				String smarts = br.readLine();
				q = new Query(smarts, false, s.charAt(0));
				switch (s.charAt(0)) {
				case 'c':
					if (smilesList == null) {
						smilesList = ChemSearch
								.generateSmilesStrings(notationList);
					}
					relevantList = smilesList;
					function = 1;
					break;
				case 's':
					if (allSimpleList == null) {
						allSimpleList = ChemSearch
								.isolateAllSimpleSmiles(notationList);
					}
					relevantList = allSimpleList;
					function = 2;
					break;
				case 'm':
					if (allMonomerList == null) {
						allMonomerList = ChemSearch
								.isolateAllMonomerSmiles(notationList);
					}
					relevantList = allMonomerList;
					function = 2;
					break;
				case 'h':
					if (chemList == null) {
						chemList = ChemSearch.isolateChemSmiles(notationList);
					}
					relevantList = chemList;
					function = 2;
					break;
				case 'p':
					if (peptideList == null) {
						peptideList = ChemSearch
								.isolatePeptideSmiles(notationList);
					}
					relevantList = peptideList;
					function = 2;
					break;
				case 'r':
					if (rnaList == null) {
						rnaList = ChemSearch.isolateRnaSmiles(notationList);
					}
					relevantList = rnaList;
					function = 2;
					break;
				case 'a':
					if (aaList == null) {
						aaList = ChemSearch
								.isolateAminoAcidSmiles(notationList);
					}
					relevantList = aaList;
					function = 2;
					break;
				case 'n':
					if (nucleotideList == null) {
						nucleotideList = ChemSearch
								.isolateNucleotideSmiles(notationList);
					}
					relevantList = nucleotideList;
					function = 2;
					break;
				case 'b':
					if (rnaMonomerList == null) {
						rnaMonomerList = ChemSearch
								.isolateRnaMonomerSmiles(notationList);
					}
					relevantList = rnaMonomerList;
					function = 2;
					break;
				}
			}
			System.out.println("Negated query? (y/n)");
			while (!s.equals("y") & !s.equals("n")) {
				s = br.readLine();
			}
			if (s.equals("y")) {
				q.negation = true;
			}
			switch (function) {
			case 1:
				Matches m = CombinedSearch
						.performPartialSearch(q, relevantList);
				return m;
			case 2:
				m = CombinedSearch.performPartialSearch2(q, relevantList);
				return m;
			case 3:
				m = CombinedSearch.performPartialSearch3(q, relevantList);
				return m;
			case 0:
				m = CombinedSearch.performSearch(q, notationList);
				return m;
			default:
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// @Deprecated //This method does not save generated lists for future use.
	// public static Matches newQuery(List<String> notationList) {
	// try {
	// Query q;
	// BufferedReader br = new BufferedReader(new InputStreamReader(
	// System.in));
	// String s = "";
	// System.out.println("sEquence/sUbstructure? (e/u)");
	// while (!s.equals("e") & !s.equals("u")) {
	// s = br.readLine();
	// }
	// if (s.equals("e")) {
	// System.out.println("Peptide/Rna? (p/r)");
	// while (!s.equals("p") & !s.equals("r")) {
	// s = br.readLine();
	// }
	// System.out.println("Enter the sequence to be found:");
	// String seq = br.readLine();
	// if (s.equals("p")) {
	// q = new Query(seq, true, true);
	// } else {
	// q = new Query(seq, true, false);
	// }
	// } else {
	// System.out
	// .println("Level of SMILES search? (c/s/m/h/p/r/a/n/b)");
	// while (!s.equals("c") & !s.equals("s") & !s.equals("m")
	// & !s.equals("h") & !s.equals("p") & !s.equals("r")
	// & !s.equals("a") & !s.equals("n") & !s.equals("b")) {
	// s = br.readLine();
	// }
	// System.out.println("Enter SMARTS of substructure to be found:");
	// String smarts = br.readLine();
	// q = new Query(smarts, false, s.charAt(0));
	// }
	// System.out.println("Negated query? (y/n)");
	// while (!s.equals("y") & !s.equals("n")) {
	// s = br.readLine();
	// }
	// if (s.equals("y")) {
	// q.negation = true;
	// }
	// Matches m = CombinedSearch.performSearch(q, notationList);
	// return m;
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// return null;
	// }
	// }

	/**
	 * A query is entered at runtime; search on subset of full notation list.
	 * 
	 * @param originalNotationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @param oldIndicesOfInterest
	 *            {@link Set} of indices indicating relevant subset of notation
	 *            list.
	 * @return {@link Matches} to query entered
	 */

	public static Matches newQuery(List<String> originalNotationList,
			Set<Integer> oldIndicesOfInterest) {
		try {
			Query q;
			Matches m;
			List<String> notationList = new ArrayList<String>();
			Set<Integer> indicesOfInterest = new HashSet<Integer>();
			List<Integer> indicesList = new ArrayList<Integer>(
					oldIndicesOfInterest); // not sorted, but elements in same
			// order as in notationList
			notationList = MatchingTools.matchNotation(indicesList,
					originalNotationList);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String s = "";
			/**
			 * indicates which CombinedSearch function needs to be run (based on
			 * relevantList) - 1 for performPartialSearch, 2 for
			 * performPartialSearch2, 3 for performPartialSearch3
			 */
			int function = 0;
			List relevantList = new ArrayList();
			System.out.println("sEquence/sUbstructure? (e/u)");
			while (!s.equals("e") & !s.equals("u")) {
				s = br.readLine();
			}
			if (s.equals("e")) {
				System.out.println("Peptide/Rna? (p/r)");
				while (!s.equals("p") & !s.equals("r")) {
					s = br.readLine();
				}
				System.out.println("Enter the sequence to be found:");
				String seq = br.readLine();
				if (s.equals("p")) {
					if (peptideSequenceList != null) {
						relevantList = MatchingTools.matchSequences(
								indicesList, peptideSequenceList);
						function = 3;
					}
					q = new Query(seq, true, true);
				} else {
					if (rnaSequenceList != null) {
						relevantList = MatchingTools.matchSequences(
								indicesList, rnaSequenceList);
						function = 3;
					}
					q = new Query(seq, true, false);
				}
			} else {
				System.out
						.println("Level of SMILES search? (c/s/m/h/p/r/a/n/b)");
				while (!s.equals("c") & !s.equals("s") & !s.equals("m")
						& !s.equals("h") & !s.equals("p") & !s.equals("r")
						& !s.equals("a") & !s.equals("n") & !s.equals("b")) {
					s = br.readLine();
				}
				System.out.println("Enter SMARTS of substructure to be found:");
				String smarts = br.readLine();
				q = new Query(smarts, false, s.charAt(0));
				switch (s.charAt(0)) {
				case 'c':
					if (smilesList != null) {
						relevantList = MatchingTools.matchNotation(indicesList,
								smilesList);
						function = 1;
					}
					break;
				case 's':
					if (allSimpleList != null) {
						relevantList = MatchingTools.matchSmiles(indicesList,
								allSimpleList);
						function = 2;
					}
					break;
				case 'm':
					if (allMonomerList != null) {
						relevantList = MatchingTools.matchSmiles(indicesList,
								allMonomerList);
						function = 2;
					}
					break;
				case 'h':
					if (chemList != null) {
						relevantList = MatchingTools.matchSmiles(indicesList,
								chemList);
						function = 2;
					}
					break;
				case 'p':
					if (peptideList != null) {
						relevantList = MatchingTools.matchSmiles(indicesList,
								peptideList);
						function = 2;
					}
					break;
				case 'r':
					if (rnaList != null) {
						relevantList = MatchingTools.matchSmiles(indicesList,
								rnaList);
						function = 2;
					}
					break;
				case 'a':
					if (aaList != null) {
						relevantList = MatchingTools.matchSmiles(indicesList,
								aaList);
						function = 2;
					}
					break;
				case 'n':
					if (nucleotideList != null) {
						relevantList = MatchingTools.matchSmiles(indicesList,
								nucleotideList);
						function = 2;
					}
					break;
				case 'b':
					if (rnaMonomerList != null) {
						relevantList = MatchingTools.matchSmiles(indicesList,
								rnaMonomerList);
						function = 2;
					}
					break;
				}
			}
			System.out.println("Negated query? (y/n)");
			while (!s.equals("y") & !s.equals("n")) {
				s = br.readLine();
			}
			if (s.equals("y")) {
				q.negation = true;
			}
			switch (function) {
			case 1:
				m = CombinedSearch.performPartialSearch(q, relevantList);
				break;
			case 2:
				m = CombinedSearch.performPartialSearch2(q, relevantList);
				break;
			case 3:
				m = CombinedSearch.performPartialSearch3(q, relevantList);
				break;
			case 0:
				m = CombinedSearch.performSearch(q, notationList);
				break;
			default:
				m = null;
			}
			// correcting back to indices in overall list
			for (Integer index : m.indicesOfInterest) {
				indicesOfInterest.add(indicesList.get(index));
			}
			m.indicesOfInterest = indicesOfInterest;
			return m;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// @Deprecated //This method does not use lists which have been generated
	// for future use.
	// public static Matches newQuery(List<String> originalNotationList,
	// Set<Integer> oldIndicesOfInterest) {
	// try {
	// Query q;
	// List<String> notationList = new ArrayList<String>();
	// Set<Integer> indicesOfInterest = new HashSet<Integer>();
	// List<Integer> indicesList = new ArrayList<Integer>(
	// oldIndicesOfInterest); // not sorted, but elements in same
	// // order as in notationList
	// notationList = MatchingTools.matchNotation2(indicesList,
	// originalNotationList);
	// BufferedReader br = new BufferedReader(new InputStreamReader(
	// System.in));
	// String s = "";
	// System.out.println("sEquence/sUbstructure? (e/u)");
	// while (!s.equals("e") & !s.equals("u")) {
	// s = br.readLine();
	// }
	// if (s.equals("e")) {
	// System.out.println("Peptide/Rna? (p/r)");
	// while (!s.equals("p") & !s.equals("r")) {
	// s = br.readLine();
	// }
	// System.out.println("Enter the sequence to be found:");
	// String seq = br.readLine();
	// if (s.equals("p")) {
	// q = new Query(seq, true, true);
	// } else {
	// q = new Query(seq, true, false);
	// }
	// } else {
	// System.out
	// .println("Level of SMILES search? (c/s/m/h/p/r/a/n/b)");
	// while (!s.equals("c") & !s.equals("s") & !s.equals("m")
	// & !s.equals("h") & !s.equals("p") & !s.equals("r")
	// & !s.equals("a") & !s.equals("n") & !s.equals("b")) {
	// s = br.readLine();
	// }
	// System.out.println("Enter SMARTS of substructure to be found:");
	// String smarts = br.readLine();
	// q = new Query(smarts, false, s.charAt(0));
	// }
	// System.out.println("Negated query? (y/n)");
	// while (!s.equals("y") & !s.equals("n")) {
	// s = br.readLine();
	// }
	// if (s.equals("y")) {
	// q.negation = true;
	// }
	// Matches m = CombinedSearch.performSearch(q, notationList);
	// for (Integer index : m.indicesOfInterest) {
	// indicesOfInterest.add(indicesList.get(index));
	// }
	// m.indicesOfInterest = indicesOfInterest;
	// return m;
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// return null;
	// }
	// }
}
