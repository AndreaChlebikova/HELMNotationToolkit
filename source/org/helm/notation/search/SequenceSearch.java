package org.helm.notation.search;

import org.helm.notation.tools.*;
import org.helm.notation.model.Monomer;
import org.helm.notation.model.PolymerNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.*;

/**
 * This class provides methods for natural sequence based searching of complex
 * polymers based on a list of HELM notations.
 * 
 * @author Andrea Chlebikova
 *
 */

public class SequenceSearch {

	/**
	 * Returns regex pattern for peptide sequence to be matched.
	 * 
	 * @param sequenceOfInterest
	 *            {@link String} denoting peptide subsequence to be found
	 * @return Regex {@link Pattern} denoting sequence to be matched
	 */

	public static Pattern peptideStringToRegex(String sequenceOfInterest) {
		sequenceOfInterest = sequenceOfInterest.toUpperCase();
		String regex = new String();
		if (sequenceOfInterest.charAt(sequenceOfInterest.length() - 1) == '*') { // *
			// denotes
			// translation
			// stop,
			// so
			// this
			// is
			// only
			// matched
			// if
			// at
			// the
			// end
			// of
			// linear
			// sequences
			regex = sequenceOfInterest.substring(0,
					sequenceOfInterest.length() - 1) + "$";
		} else {
			regex = sequenceOfInterest;
		}
		regex = regex.replace("-", ".*?"); // gap
		regex = regex.replace('X', '.'); // wildcard amino acid
		regex = regex.replace('U', 'C'); // selenocysteine
		regex = regex.replace("B", "[DN]");
		regex = regex.replace("J", "[LI]");
		regex = regex.replace("Z", "[EQ]");
		Pattern peptidePattern = Pattern.compile("(" + regex + ")");
		return peptidePattern;
	}

	/**
	 * Returns regex pattern for nucleotide sequence to be matched.
	 * 
	 * @param sequenceOfInterest
	 *            {@link String} denoting nucleotide subsequence to be found
	 * @return Regex {@link Pattern} denoting sequence to be matched
	 */

	public static Pattern rnaStringToRegex(String sequenceOfInterest) {
		sequenceOfInterest = sequenceOfInterest.toUpperCase();
		String regex = sequenceOfInterest;
		regex = regex.replace("-", ".*?"); // gap
		regex = regex.replace('N', '.'); // wildcard nucleotide
		regex = regex.replace('X', '.'); // masked nucleotide
		regex = regex.replace("R", "[AG]"); // purines
		regex = regex.replace("Y", "[CTU]"); // pyrimidines
		regex = regex.replace("S", "[GC]"); // strong interaction
		regex = regex.replace("W", "[ATU]"); // weak interaction
		regex = regex.replace("K", "[GTU]"); // ketones
		regex = regex.replace("M", "[AC]"); // amino groups
		regex = regex.replace("B", "[CGTU]"); // not A
		regex = regex.replace("D", "[AGTU]"); // not C
		regex = regex.replace("H", "[ACTU]"); // not G
		regex = regex.replace("V", "[ACG]"); // not T or U
		Pattern rnaPattern = Pattern.compile("(" + regex + ")");
		return rnaPattern;
	}

	/**
	 * Isolates the (natural analogue) peptide sequences present.
	 * 
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link Set}s of {@link Sequence}s, the natural
	 *         analogue peptide sequences present in each complex polymer
	 */

	public static List<Set<Sequence>> isolatePeptideSequences(
			List<String> notationList) {
		try {
			List<Set<Sequence>> peptideList = new ArrayList<Set<Sequence>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<Sequence> peptideSubSet = new HashSet<Sequence>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.PEPTIDE_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String s = SimpleNotationParser
								.getPeptideSequence(simpleNotation);
						Sequence seq = new Sequence(s);
						if (notation.contains(polymer.getId()
								+ ","
								+ polymer.getId()
								+ ",1:R1-"
								+ SimpleNotationParser.getMonomerList(
										simpleNotation,
										Monomer.PEPTIDE_POLYMER_TYPE).size()
								+ ":R2")
								|| notation.contains(polymer.getId()
										+ ","
										+ polymer.getId()
										+ ","
										+ SimpleNotationParser.getMonomerList(
												simpleNotation,
												Monomer.PEPTIDE_POLYMER_TYPE)
												.size() + ":R2-1:R1")) {
							seq.cyclic = true;
						}
						peptideSubSet.add(seq);
					}
				}
				peptideList.add(peptideSubSet);
			}
			return peptideList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Isolates the (natural analogue) nucleotide sequences present.
	 * 
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link Set}s of {@link Sequence}s, the natural
	 *         analogue nucleotide sequences present in each complex polymer
	 */

	public static List<Set<Sequence>> isolateRnaSequences(
			List<String> notationList) {
		try {
			List<Set<Sequence>> rnaList = new ArrayList<Set<Sequence>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<Sequence> rnaSubSet = new HashSet<Sequence>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(
							Monomer.NUCLIEC_ACID_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String s = SimpleNotationParser
								.getNucleotideSequence(simpleNotation);
						Sequence seq = new Sequence(s);
						if (notation.contains(polymer.getId()
								+ ","
								+ polymer.getId()
								+ ",1:R1-"
								+ SimpleNotationParser.getMonomerList(
										simpleNotation,
										Monomer.NUCLIEC_ACID_POLYMER_TYPE)
										.size() + ":R2")
								|| notation
										.contains(polymer.getId()
												+ ","
												+ polymer.getId()
												+ ","
												+ SimpleNotationParser
														.getMonomerList(
																simpleNotation,
																Monomer.NUCLIEC_ACID_POLYMER_TYPE)
														.size() + ":R2-1:R1")) {
							seq.cyclic = true;
						}
						rnaSubSet.add(seq);
					}
				}
				rnaList.add(rnaSubSet);
			}
			return rnaList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Finds complex polymers with subsequence of interest.
	 * 
	 * @param findPeptide
	 *            {@link Boolean}, true if looking for amino acid subsequence,
	 *            false if looking for nucleotide subsequence
	 * @param sequenceOfInterest
	 *            {@link String} denoting subsequence to be found
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link Set} of {@link Integer}s, the indices of complex polymers
	 *         containing the subsequence of interest
	 */
	@Deprecated
	public static Set<Integer> findMatchingCompounds(Boolean findPeptide,
			String sequenceOfInterest, List<String> notationList) {
		if (findPeptide) {
			List<Set<Sequence>> peptideList = isolatePeptideSequences(notationList);
			Pattern regex = peptideStringToRegex(sequenceOfInterest);
			return findPeptideMatchingCompounds(regex, peptideList);
		} else {
			List<Set<Sequence>> rnaList = isolateRnaSequences(notationList);
			Pattern regex = rnaStringToRegex(sequenceOfInterest);
			return findRnaMatchingCompounds(regex, rnaList);
		}
	}

	/**
	 * Finds complex polymers with amino acid subsequence of interest.
	 * 
	 * @param regex
	 *            {@link Pattern} denoting amino acid sequence to be found
	 * @param peptideList
	 *            {@link List} of {@link Set}s of {@link Sequence}s, the natural
	 *            analogue peptide sequences present in each complex polymer
	 * @return {@link Set} of {@link Integer}s, the indices of complex polymers
	 *         containing the subsequence of interest
	 */

	public static Set<Integer> findPeptideMatchingCompounds(Pattern regex,
			List<Set<Sequence>> peptideList) {
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		for (int i = 0; i < peptideList.size(); i++) {
			Set<Sequence> peptideSubSet = peptideList.get(i);
			sequenceLoop: for (Sequence seq : peptideSubSet) {
				Matcher matcher = regex.matcher(seq.sequence);
				if (matcher.find()) {
					indicesOfInterest.add(i);
					break;
				} else if (seq.cyclic) {
					String extendedSequence = seq.sequence + seq.sequence; // TODO
																			// optimise
					int k = 0;
					matcher = regex.matcher(extendedSequence);
					while (matcher.find()) {
						k = k + matcher.start(1) + 1;
						if (matcher.group(1).length() <= seq.sequence.length()) {
							indicesOfInterest.add(i);
							break sequenceLoop;
						}
						matcher = regex.matcher(extendedSequence.substring(k));
					}
				}
			}
		}
		return indicesOfInterest;
	}

	/**
	 * Finds complex polymers with nucleotide subsequence of interest.
	 * 
	 * @param regex
	 *            {@link Pattern} denoting nucleotide sequence to be found
	 * @param rnaList
	 *            {@link List} of {@link Set}s of {@link Sequence}s, the natural
	 *            analogue nucleotide sequences present in each complex polymer
	 * @return {@link Set} of {@link Integer}s, the indices of complex polymers
	 *         containing the subsequence of interest
	 */

	public static Set<Integer> findRnaMatchingCompounds(Pattern regex,
			List<Set<Sequence>> rnaList) {
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		for (int i = 0; i < rnaList.size(); i++) {
			Set<Sequence> rnaSubSet = rnaList.get(i);
			for (Sequence seq : rnaSubSet) {
				Matcher matcher = regex.matcher(seq.sequence);
				if (matcher.find()) {
					indicesOfInterest.add(i);
					break;
				}
			}
		}
		return indicesOfInterest;
	}
}
