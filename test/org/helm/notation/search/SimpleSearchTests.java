package org.helm.notation.search;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.helm.notation.MonomerFactory;
import org.helm.notation.NucleotideFactory;
import org.helm.notation.tools.SimpleNotationParser;

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
			MonomerFactory.finalizeMonomerCache();
			MonomerFactory.getInstance();
			NucleotideFactory.getInstance();
			SimpleNotationParser.resetSeed();
			notationList = Files.readAllLines(Paths.get("test/org/helm/notation/search/HELMStrings.txt"), Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void finish() {
		MonomerFactory.finalizeMonomerCache();
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
		List<List<String>> peptideList = SequenceSearch.isolatePeptideSequences(notationList);
		System.out.println(peptideList);
		
		List<List<String>> rnaList = SequenceSearch.isolateRnaSequences(notationList);
		System.out.println(rnaList);
		
		Boolean findPeptide = false; //true for peptide search, false for nucleotide search
		String sequenceOfInterest = "AU";
		
		List<Integer> indicesOfInterest = SequenceSearch.findMatchingCompounds(findPeptide, sequenceOfInterest, notationList);
		List<String> notationsOfInterest = MatchingTools.matchNotation(indicesOfInterest, notationList);
		
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
		List<List<String>> chemList = ChemSearch.isolateChemMonomerSmiles(notationList);
		System.out.println(chemList);
		
		List<List<String>> chemList2 = ChemSearch.isolateChemSmiles(notationList);
		System.out.println(chemList2);
		
		List<List<String>> peptideList = ChemSearch.isolatePeptideMonomerSmiles(notationList);
		System.out.println(peptideList);
		
		List<List<String>> peptideList2 = ChemSearch.isolatePeptideSmiles(notationList);
		System.out.println(peptideList2);
		
		List<List<String>> rnaList = ChemSearch.isolateRnaMonomerSmiles(notationList);
		System.out.println(rnaList);
		
		List<List<String>> rnaList2 = ChemSearch.isolateRnaSmiles(notationList);
		System.out.println(rnaList2);
		
		List<List<String>> allList = ChemSearch.isolateAllMonomerSmiles(notationList);
		System.out.println(allList);
		
		List<List<String>> allList2 = ChemSearch.isolateAllSimpleSmiles(notationList);
		System.out.println(allList2);
		
		List<String> smilesList = ChemSearch.generateSmilesStrings(notationList);
		System.out.println(smilesList);

		String smartsOfInterest = "C(=O)N"; //found in peptide bonds across monomer boundaries in peptides (not within amino acids though), but also within nucleotides, as present in C/G/T/U bases
		
		List<Integer> indicesOfInterest = ChemSearch.findMatchingCompounds(smartsOfInterest, smilesList);
		List<Integer> indicesOfInterest2 = ChemSearch.findMatchingCompounds2(smartsOfInterest, chemList);
		List<Integer> indicesOfInterest3 = ChemSearch.findMatchingCompounds2(smartsOfInterest, chemList2);
		List<Integer> indicesOfInterest4 = ChemSearch.findMatchingCompounds2(smartsOfInterest, peptideList);
		List<Integer> indicesOfInterest5 = ChemSearch.findMatchingCompounds2(smartsOfInterest, peptideList2);
		List<Integer> indicesOfInterest6 = ChemSearch.findMatchingCompounds2(smartsOfInterest, rnaList);
		List<Integer> indicesOfInterest7 = ChemSearch.findMatchingCompounds2(smartsOfInterest, rnaList2);
		List<Integer> indicesOfInterest8 = ChemSearch.findMatchingCompounds2(smartsOfInterest, allList);
		List<Integer> indicesOfInterest9 = ChemSearch.findMatchingCompounds2(smartsOfInterest, allList2);
		List<String> notationsOfInterest = MatchingTools.matchNotation(indicesOfInterest, notationList);
		
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
		indicesOfInterest3 = ChemSearch.findMatchingCompounds2(smartsOfInterest, chemList2);
		indicesOfInterest4 = ChemSearch.findMatchingCompounds2(smartsOfInterest, peptideList);
		indicesOfInterest5 = ChemSearch.findMatchingCompounds2(smartsOfInterest, peptideList2);
		indicesOfInterest6 = ChemSearch.findMatchingCompounds2(smartsOfInterest, rnaList);
		indicesOfInterest7 = ChemSearch.findMatchingCompounds2(smartsOfInterest, rnaList2);
		indicesOfInterest8 = ChemSearch.findMatchingCompounds2(smartsOfInterest, allList);
		indicesOfInterest9 = ChemSearch.findMatchingCompounds2(smartsOfInterest, allList2);
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
		indicesOfInterest3 = ChemSearch.findMatchingCompounds2(smartsOfInterest, chemList2);
		indicesOfInterest4 = ChemSearch.findMatchingCompounds2(smartsOfInterest, peptideList);
		indicesOfInterest5 = ChemSearch.findMatchingCompounds2(smartsOfInterest, peptideList2);
		indicesOfInterest6 = ChemSearch.findMatchingCompounds2(smartsOfInterest, rnaList);
		indicesOfInterest7 = ChemSearch.findMatchingCompounds2(smartsOfInterest, rnaList2);
		indicesOfInterest8 = ChemSearch.findMatchingCompounds2(smartsOfInterest, allList);
		indicesOfInterest9 = ChemSearch.findMatchingCompounds2(smartsOfInterest, allList2);
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