package org.helm.notation.search;

import org.helm.notation.tools.*;
import org.helm.notation.model.Monomer;
import org.helm.notation.model.Nucleotide;
import org.helm.notation.model.PolymerNode;

import java.util.ArrayList;
import java.util.List;


/**
* This class provides methods for SMILES-based searching of individual monomers, simple polymers, as well as the complex polymer
*
*@author Andrea Chlebikova
*/

public class ChemSearch {
	
	/**
	 * Isolates the SMILES strings of the CHEM monomers present.
	 * 
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link List}s of {@link String}s, the SMILES strings of the CHEM monomers present in each complex polymer
	 */
	
	public static List<List<String>> isolateChemMonomerSmiles(List<String> notationList) {
		try {
			List<List<String>> chemList = new ArrayList<List<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser.getPolymerNodeList(notation);
				List<String> chemSubList = new ArrayList<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.CHEMICAL_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String s = SimpleNotationParser.getSimplePolymerSMILES(simpleNotation, Monomer.CHEMICAL_POLYMER_TYPE);
						Integer i = s.indexOf("|");
						String s2 = s.substring(0, i-1);
						chemSubList.add(s2);
					}
				}
				chemList.add(chemSubList);
			}
			return chemList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Isolates the SMILES strings of the CHEM "polymers" present.
	 * 
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link List}s of {@link String}s, the SMILES strings of the CHEM "polymers" present in each complex polymer
	 */
	
	public static List<List<String>> isolateChemSmiles(List<String> notationList) {
		try {
			List<List<String>> chemList = new ArrayList<List<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser.getPolymerNodeList(notation);
				List<String> chemSubList = new ArrayList<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.CHEMICAL_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String complexNotation = "CHEM1{"+simpleNotation+"}$$$$";
						String s = ComplexNotationParser.getComplexPolymerSMILES(complexNotation);
						chemSubList.add(s);
					}
				}
				chemList.add(chemSubList);
			}
			return chemList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Isolates the SMILES strings of the peptide monomers (amino acids) present.
	 * 
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link List}s of {@link String}s, the SMILES strings of the peptide monomers (amino acids) present in each complex polymer
	 */
	
	public static List<List<String>> isolatePeptideMonomerSmiles(List<String> notationList) {
		try {
			List<List<String>> aaList = new ArrayList<List<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser.getPolymerNodeList(notation);
				List<String> aaSubList = new ArrayList<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.PEPTIDE_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						List<Monomer> aaSubSubList = SimpleNotationParser.getMonomerList(simpleNotation, Monomer.PEPTIDE_POLYMER_TYPE);
						for (Monomer aa : aaSubSubList) {
							String s = aa.getCanSMILES();
							Integer i = s.indexOf("|");
							String s2 = s.substring(0, i-1);
							aaSubList.add(s2);
						}
					}
				}
				aaList.add(aaSubList);
			}
			return aaList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Isolate the SMILES strings of the peptide simple polymers present.
	 * 
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link List}s of {@link String}s, the SMILES strings of the peptides present in each complex polymer
	 */
	
	public static List<List<String>> isolatePeptideSmiles(List<String> notationList) {
		try {
			List<List<String>> peptideList = new ArrayList<List<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser.getPolymerNodeList(notation);
				List<String> peptideSubList = new ArrayList<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.PEPTIDE_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String complexNotation = "PEPTIDE1{"+simpleNotation+"}$$$$";
						String s = ComplexNotationParser.getComplexPolymerSMILES(complexNotation);
						peptideSubList.add(s);
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
	 * Isolates the SMILES strings of the nucleotide monomers present.
	 * 
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link List}s of {@link String}s, the SMILES strings of the nucleotide monomers present in each complex polymer
	 */
	
	public static List<List<String>> isolateRnaMonomerSmiles(List<String> notationList) {
		try {
			List<List<String>> monomerList = new ArrayList<List<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser.getPolymerNodeList(notation);
				List<String> monomerSubList = new ArrayList<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.NUCLIEC_ACID_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						List<Nucleotide> monomerSubSubList = SimpleNotationParser.getNucleotideList(simpleNotation);
						for (Nucleotide monomer : monomerSubSubList) {
							String s = monomer.getNotation();
							String complexNotation = "RNA1{"+s+"}$$$$";
							String s2 = ComplexNotationParser.getComplexPolymerSMILES(complexNotation);
							monomerSubList.add(s2);
						}
					}
				}
				monomerList.add(monomerSubList);
			}
			return monomerList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Isolate the SMILES strings of the nucleotide simple polymers present.
	 * 
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link List}s of {@link String}s, the SMILES strings of the nucleotide simple polymers present in each complex polymer
	 */
	
	public static List<List<String>> isolateRnaSmiles(List<String> notationList) {
		try {
			List<List<String>> rnaList = new ArrayList<List<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser.getPolymerNodeList(notation);
				List<String> rnaSubList = new ArrayList<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.NUCLIEC_ACID_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String complexNotation = "RNA1{"+simpleNotation+"}$$$$";
						String s = ComplexNotationParser.getComplexPolymerSMILES(complexNotation);
						rnaSubList.add(s);
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
	 * Isolates the SMILES strings of the CHEM monomers, amino acids and nucleotides present.
	 * 
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link List}s of {@link String}s, the SMILES strings of the monomers present in each complex polymer
	 */
	
	public static List<List<String>> isolateAllMonomerSmiles(List<String> notationList) {
		try {
			List<List<String>> monomerList = new ArrayList<List<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser.getPolymerNodeList(notation);
				List<String> monomerSubList = new ArrayList<String>();
				String s, s2 = new String();
				for (PolymerNode polymer : polymers) {
					String simpleNotation = polymer.getLabel();
					switch (polymer.getType()) {
						case Monomer.CHEMICAL_POLYMER_TYPE:
							s = SimpleNotationParser.getSimplePolymerSMILES(simpleNotation, Monomer.CHEMICAL_POLYMER_TYPE);
							Integer i = s.indexOf("|");
							s2 = s.substring(0, i-1);
							monomerSubList.add(s2);
							break;
						case Monomer.PEPTIDE_POLYMER_TYPE:
							List<Monomer> aaSubSubList = SimpleNotationParser.getMonomerList(simpleNotation, Monomer.PEPTIDE_POLYMER_TYPE);
							for (Monomer aa : aaSubSubList) {
								s = aa.getCanSMILES();
								i = s.indexOf("|");
								s2 = s.substring(0, i-1);
								monomerSubList.add(s2);
							}
							break;
						case Monomer.NUCLIEC_ACID_POLYMER_TYPE:
							List<Nucleotide> monomerSubSubList = SimpleNotationParser.getNucleotideList(simpleNotation);
							for (Nucleotide monomer : monomerSubSubList) {
								s = monomer.getNotation();
								String complexNotation = "RNA1{"+s+"}$$$$";
								s2 = ComplexNotationParser.getComplexPolymerSMILES(complexNotation);
								monomerSubList.add(s2);
							}
					}
				}
				monomerList.add(monomerSubList);
			}
			return monomerList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Isolates the SMILES strings of the simple polymers present.
	 * 
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link List}s of {@link String}s, the SMILES strings of the simple polymers present in each complex polymer
	 */
	
	public static List<List<String>> isolateAllSimpleSmiles(List<String> notationList) {
		try {
			List<List<String>> smilesList = new ArrayList<List<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser.getPolymerNodeList(notation);
				List<String> smilesSubList = new ArrayList<String>();
				for (PolymerNode polymer : polymers) {
					switch (polymer.getType()) {
					case Monomer.CHEMICAL_POLYMER_TYPE:
						String simpleNotation = polymer.getLabel();
						String complexNotation = "CHEM1{"+simpleNotation+"}$$$$";
						String s = ComplexNotationParser.getComplexPolymerSMILES(complexNotation);
						smilesSubList.add(s);
						break;
					case Monomer.PEPTIDE_POLYMER_TYPE:
						simpleNotation = polymer.getLabel();
						complexNotation = "PEPTIDE1{"+simpleNotation+"}$$$$";
						s = ComplexNotationParser.getComplexPolymerSMILES(complexNotation);
						smilesSubList.add(s);
						break;
					case Monomer.NUCLIEC_ACID_POLYMER_TYPE:
						simpleNotation = polymer.getLabel();
						complexNotation = "RNA1{"+simpleNotation+"}$$$$";
						s = ComplexNotationParser.getComplexPolymerSMILES(complexNotation);
						smilesSubList.add(s);
						break;
					}
				}
				smilesList.add(smilesSubList);
			}
			return smilesList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Generates the SMILES strings for the overall complex polymers.
	 * 
	 * @param notationList {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of SMILES {@link String}s of the complex polymers
	 */
	
	public static List<String> generateSmilesStrings(List<String> notationList) {
		try {
			List<String> smilesList = new ArrayList<String>();
			for (String notation : notationList) {
				String smiles = ComplexNotationParser.getComplexPolymerSMILES(notation);
				smilesList.add(smiles);
			}
			return smilesList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Finds complex polymers with substructure of interest.
	 * 
	 * @param smartsOfInterest SMARTS {@String} of substructure to be found
	 * @param smilesList {@link List} of SMILES {@link String}s of the complex polymers
	 * @return {@link List} of {@link Integer}s, the indices of complex polymers containing the substructure of interest
	 */
	
	public static List<Integer> findMatchingCompounds(String smartsOfInterest, List<String> smilesList) {
		List<Integer> indicesOfInterest = new ArrayList();
		for (int i = 0; i < smilesList.size(); i++) {
			if (SubstructureTest.isSubstructure(smilesList.get(i),smartsOfInterest)) {
				indicesOfInterest.add(i);
			}
		}
		return indicesOfInterest;
	}
	
	/**
	 * Finds complex polymer parts with substructure of interest.
	 * 
	 * @param smartsOfInterest SMARTS {@String} of substructure to be found
	 * @param smilesListList {@link List} of {@link List}s of SMILES {@link String}s of the complex polymer parts (monomers/simple polymers/...)
	 * @return {@link List} of {@link Integer}s, the indices of complex polymers with parts containing the substructure of interest
	 */
	
	public static List<Integer> findMatchingCompounds2(String smartsOfInterest, List<List<String>> smilesListList) {
		List<Integer> indicesOfInterest = new ArrayList();
		for (int i = 0; i < smilesListList.size(); i++) {
			List<String> smilesList = smilesListList.get(i);
			for (String smiles : smilesList) {
				if (SubstructureTest.isSubstructure(smiles,smartsOfInterest)) {
					indicesOfInterest.add(i);
					break;
				}
			}
		}
		return indicesOfInterest;
	}
}
