package org.helm.notation.search;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Andrea Chlebikova
 *
 */

public class SimpleSearchTests {
	
	List<String> notationList  = new ArrayList<String>();
	
	@Before
	public void init() {
		try {
			//MonomerFactory.finalizeMonomerCache();
			//MonomerFactory.getInstance();
			//NucleotideFactory.getInstance();
			//SimpleNotationParser.resetSeed(); //for ad hoc monomer labels
			notationList = Files.readAllLines(Paths.get("test/org/helm/notation/search/HELMStrings.txt"), Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void finish() {
		//MonomerFactory.finalizeMonomerCache();
		notationList.clear();
	}
	
	@Test
	public void testSubstructureTest() {
		assertTrue(SubstructureTest.isSubstructure("C1CC1CN","CN"));
		assertTrue(SubstructureTest.isSubstructure("C1CC1","CC(C)C")); //cyclopropane/isobutane
		assertFalse(SubstructureTest.isSubstructure("CCC", "C=O"));
	}
	
	@Test
	public void testSequenceSearch() {
		Boolean findPeptide = false; //true for peptide search, false for nucleotide search
		String sequenceOfInterest = "AU";
		
		Set<Integer> indicesOfInterest = SequenceSearch.findMatchingCompounds(findPeptide, sequenceOfInterest, notationList);
		Set<String> notationsOfInterest = MatchingTools.matchNotation(indicesOfInterest, notationList);
		
		System.out.println("Finding Compounds with Nucleotide Sequence of Interest, "+sequenceOfInterest);
		System.out.println(indicesOfInterest);
		System.out.println(notationsOfInterest);
		
		findPeptide = true; //true for peptide search, false for nucleotide search
		sequenceOfInterest = "AC";
		
		indicesOfInterest = SequenceSearch.findMatchingCompounds(findPeptide, sequenceOfInterest, notationList);
		notationsOfInterest = MatchingTools.matchNotation(indicesOfInterest, notationList);
		
		System.out.println("Finding Compounds with Peptide Sequence of Interest, "+sequenceOfInterest);
		System.out.println(indicesOfInterest);
		System.out.println(notationsOfInterest);
	}
	
	@Test
	public void testChemSearch() {
		List<Set<String>> chemList = ChemSearch.isolateChemSmiles(notationList);
		System.out.println(chemList);

		List<Set<String>> aaList = ChemSearch.isolateAminoAcidSmiles(notationList);
		System.out.println(aaList);

		List<Set<String>> peptideList = ChemSearch.isolatePeptideSmiles(notationList);
		System.out.println(peptideList);

		List<Set<String>> rnaMonomerList = ChemSearch.isolateRnaMonomerSmiles(notationList);
		System.out.println(rnaMonomerList);
		
		List<Set<String>> nucleotideList = ChemSearch.isolateNucleotideSmiles(notationList);
		System.out.println(nucleotideList);

		List<Set<String>> rnaList = ChemSearch.isolateRnaSmiles(notationList);
		System.out.println(rnaList);

		List<Set<String>> allMonomerList = ChemSearch.isolateAllMonomerSmiles(notationList);
		System.out.println(allMonomerList);

		List<Set<String>> allSimpleList = ChemSearch.isolateAllSimpleSmiles(notationList);
		System.out.println(allSimpleList);

		List<String> smilesList = ChemSearch.generateSmilesStrings(notationList);
		System.out.println(smilesList);

		String smartsOfInterest = "C(=O)N"; //found in peptide bonds across monomer boundaries in peptides (not within amino acids though, except N/Q, but also within nucleotides, as present in C/G/T/U bases
		
		Set<Integer> indicesOfInterest = ChemSearch.findMatchingCompounds(smartsOfInterest, smilesList);
		Set<Integer> indicesOfInterest2 = ChemSearch.findMatchingCompounds2(smartsOfInterest, chemList);
		Set<Integer> indicesOfInterest3 = ChemSearch.findMatchingCompounds2(smartsOfInterest, aaList);
		Set<Integer> indicesOfInterest4 = ChemSearch.findMatchingCompounds2(smartsOfInterest, peptideList);
		Set<Integer> indicesOfInterest5 = ChemSearch.findMatchingCompounds2(smartsOfInterest, rnaMonomerList);
		Set<Integer> indicesOfInterest6 = ChemSearch.findMatchingCompounds2(smartsOfInterest, nucleotideList);
		Set<Integer> indicesOfInterest7 = ChemSearch.findMatchingCompounds2(smartsOfInterest, rnaList);
		Set<Integer> indicesOfInterest8 = ChemSearch.findMatchingCompounds2(smartsOfInterest, allMonomerList);
		Set<Integer> indicesOfInterest9 = ChemSearch.findMatchingCompounds2(smartsOfInterest, allSimpleList);
		Set<String> notationsOfInterest = MatchingTools.matchNotation(indicesOfInterest, notationList);
		
		System.out.println("Finding Compounds with Substructure of Interest, "+smartsOfInterest);
		System.out.println(indicesOfInterest);
		System.out.println(indicesOfInterest2);
		System.out.println(indicesOfInterest3);
		System.out.println(indicesOfInterest4);
		System.out.println(indicesOfInterest5);
		System.out.println(indicesOfInterest6);
		System.out.println(indicesOfInterest7);
		System.out.println(indicesOfInterest8);
		System.out.println(indicesOfInterest9);
		System.out.println(notationsOfInterest);
		
		smartsOfInterest = "SCCCOOCCCS"; //present across monomer and simple polymer boundaries in SS3 dimer - only found in complex polymer smiles search
		
		indicesOfInterest = ChemSearch.findMatchingCompounds(smartsOfInterest, smilesList);
		indicesOfInterest2 = ChemSearch.findMatchingCompounds2(smartsOfInterest, chemList);
		indicesOfInterest3 = ChemSearch.findMatchingCompounds2(smartsOfInterest, aaList);
		indicesOfInterest4 = ChemSearch.findMatchingCompounds2(smartsOfInterest, peptideList);
		indicesOfInterest5 = ChemSearch.findMatchingCompounds2(smartsOfInterest, rnaMonomerList);
		indicesOfInterest6 = ChemSearch.findMatchingCompounds2(smartsOfInterest, nucleotideList);
		indicesOfInterest7 = ChemSearch.findMatchingCompounds2(smartsOfInterest, rnaList);
		indicesOfInterest8 = ChemSearch.findMatchingCompounds2(smartsOfInterest, allMonomerList);
		indicesOfInterest9 = ChemSearch.findMatchingCompounds2(smartsOfInterest, allSimpleList);
		notationsOfInterest = MatchingTools.matchNotation(indicesOfInterest, notationList);
		
		System.out.println("Finding Compounds with Substructure of Interest, "+smartsOfInterest);
		System.out.println(indicesOfInterest);
		System.out.println(indicesOfInterest2);
		System.out.println(indicesOfInterest3);
		System.out.println(indicesOfInterest4);
		System.out.println(indicesOfInterest5);
		System.out.println(indicesOfInterest6);
		System.out.println(indicesOfInterest7);
		System.out.println(indicesOfInterest8);
		System.out.println(indicesOfInterest9);
		System.out.println(notationsOfInterest);
		
		smartsOfInterest = "SCCCO"; //present in SS3 monomer
		
		indicesOfInterest = ChemSearch.findMatchingCompounds(smartsOfInterest, smilesList);
		indicesOfInterest2 = ChemSearch.findMatchingCompounds2(smartsOfInterest, chemList);
		indicesOfInterest3 = ChemSearch.findMatchingCompounds2(smartsOfInterest, aaList);
		indicesOfInterest4 = ChemSearch.findMatchingCompounds2(smartsOfInterest, peptideList);
		indicesOfInterest5 = ChemSearch.findMatchingCompounds2(smartsOfInterest, rnaMonomerList);
		indicesOfInterest6 = ChemSearch.findMatchingCompounds2(smartsOfInterest, nucleotideList);
		indicesOfInterest7 = ChemSearch.findMatchingCompounds2(smartsOfInterest, rnaList);
		indicesOfInterest8 = ChemSearch.findMatchingCompounds2(smartsOfInterest, allMonomerList);
		indicesOfInterest9 = ChemSearch.findMatchingCompounds2(smartsOfInterest, allSimpleList);
		notationsOfInterest = MatchingTools.matchNotation(indicesOfInterest, notationList);
		
		System.out.println("Finding Compounds with Substructure of Interest, "+smartsOfInterest);
		System.out.println(indicesOfInterest);
		System.out.println(indicesOfInterest2);
		System.out.println(indicesOfInterest3);
		System.out.println(indicesOfInterest4);
		System.out.println(indicesOfInterest5);
		System.out.println(indicesOfInterest6);
		System.out.println(indicesOfInterest7);
		System.out.println(indicesOfInterest8);
		System.out.println(indicesOfInterest9);
		System.out.println(notationsOfInterest);
	}
}