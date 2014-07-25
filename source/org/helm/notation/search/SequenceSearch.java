package org.helm.notation.search;

import org.helm.notation.tools.*;
import org.helm.notation.model.Monomer;
import org.helm.notation.model.PolymerNode;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods for natural sequence based searching of complex polymers based on a list of HELM notations.
 * 
 * @author Andrea Chlebikova
 *
 */

public class SequenceSearch {
	
	/**
	 * Isolates the (natural analogue) peptide sequences present.
	 * 
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link List}s of {@link String}s, the natural analogue peptide sequences present in each complex polymer
	 */
	
	public static List<List<String>> isolatePeptideSequences(List<String> notationList) {
		try {
			List<List<String>> peptideList = new ArrayList<List<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser.getPolymerNodeList(notation);
				List<String> peptideSubList = new ArrayList<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.PEPTIDE_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String seq = SimpleNotationParser.getPeptideSequence(simpleNotation);
						peptideSubList.add(seq);
					}
				}
				peptideList.add(peptideSubList);
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
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link List}s of {@link String}s, the natural analogue nucleotide sequences present in each complex polymer
	 */
	
	public static List<List<String>> isolateRnaSequences(List<String> notationList) {
		try {
			List<List<String>> rnaList = new ArrayList<List<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser.getPolymerNodeList(notation);
				List<String> rnaSubList = new ArrayList<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.NUCLIEC_ACID_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String seq = SimpleNotationParser.getTrimmedNucleotideSequence(simpleNotation);
						rnaSubList.add(seq);
					}
				}
				rnaList.add(rnaSubList);
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
	 * @param findPeptide {@link Boolean}, true if looking for amino acid subsequence, false if looking for nucleotide subsequence
	 * @param sequenceOfInterest {@link String} denoting subsequence to be found
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link Integer}s, the indices of complex polymers containing the subsequence of interest
	 */
	
	public static List<Integer> findMatchingCompounds(Boolean findPeptide, String sequenceOfInterest, List<String> notationList) {
		if (findPeptide) {
			List<List<String>> peptideList = isolatePeptideSequences(notationList);
			return findPeptideMatchingCompounds(sequenceOfInterest, peptideList);
		} else {
			List<List<String>> rnaList = isolateRnaSequences(notationList);
			return findRnaMatchingCompounds(sequenceOfInterest, rnaList);
		}
	}
	
	/**
	 * Finds complex polymers with amino acid subsequence of interest.
	 * 
	 * @param sequenceOfInterest {@link String} denoting amino acid subsequence to be found
	 * @param peptideList {@link List} of {@link List}s of {@link String}s, the natural analogue peptide sequences present in each complex polymer
	 * @return {@link List} of {@link Integer}s, the indices of complex polymers containing the subsequence of interest
	 */
	
	public static List<Integer> findPeptideMatchingCompounds(String sequenceOfInterest, List<List<String>> peptideList) {
		List<Integer> indicesOfInterest = new ArrayList();
		for (int i = 0; i < peptideList.size(); i++) {
			List<String> peptideSubList = peptideList.get(i);
			for (String sequence : peptideSubList) {
				if (sequence.contains(sequenceOfInterest)) {
					indicesOfInterest.add(i);
					break;
				}
			}
		}
		return indicesOfInterest;
	}
	
	/**
	 * Finds complex polymers with nucleotide subsequence of interest.
	 * 
	 * @param sequenceOfInterest {@link String} denoting nucleotide subsequence to be found
	 * @param rnaList {@link List} of {@link List}s of {@link String}s, the natural analogue nucleotide sequences present in each complex polymer
	 * @return {@link List} of {@link Integer}s, the indices of complex polymers containing the subsequence of interest
	 */
	
	public static List<Integer> findRnaMatchingCompounds(String sequenceOfInterest, List<List<String>> rnaList) {
		List<Integer> indicesOfInterest = new ArrayList();
		for (int i = 0; i < rnaList.size(); i++) {
			List<String> rnaSubList = rnaList.get(i);
			for (String sequence : rnaSubList) {
				if (sequence.contains(sequenceOfInterest)) {
					indicesOfInterest.add(i);
					break;
				}
			}
		}
		return indicesOfInterest;
	}
}
