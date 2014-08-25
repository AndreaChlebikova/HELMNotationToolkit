package org.helm.notation.search;

import org.helm.notation.tools.*;
import org.helm.notation.model.Monomer;
import org.helm.notation.model.Nucleotide;
import org.helm.notation.model.PolymerNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * This class provides methods for SMILES-based searching of individual
 * monomers, simple polymers, as well as the complex polymer.
 *
 * @author Andrea Chlebikova
 */
// TODO could rewrite and group SmilesSetsList generating methods together into
// fewer?
public class ChemSearch {

	/**
	 * Isolates the SMILES strings of the CHEM "polymers" present.
	 * 
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link Set}s of {@link String}s, the SMILES
	 *         strings of the CHEM "polymers" present in each complex polymer
	 */
	@Deprecated
	public static List<Set<String>> isolateChemSmiles(List<String> notationList) {
		try {
			List<Set<String>> chemList = new ArrayList<Set<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> chemSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.CHEMICAL_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String complexNotation = "CHEM1{" + simpleNotation
								+ "}$$$$";
						try {
							String s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							chemSubSet.add(s);
						} catch (Exception e) {
							// e.printStackTrace();
							chemSubSet.add("*");
						}
					}
				}
				chemList.add(chemSubSet);
			}
			return chemList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static SmilesSetsList generateChemSmiles(List<String> notationList) {
		try {
			SmilesSetsList chemList = new SmilesSetsList();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> chemSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.CHEMICAL_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String complexNotation = "CHEM1{" + simpleNotation
								+ "}$$$$";
						try {
							String s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							chemSubSet.add(s);
						} catch (Exception e) {
							chemSubSet.add("*");
							chemList.smilesWarningFlag = true;
						}
					}
				}
				chemList.list.add(chemSubSet);
			}
			return chemList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Isolates the SMILES strings of the peptide monomers (amino acids)
	 * present.
	 * 
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link Set}s of {@link String}s, the SMILES
	 *         strings of the peptide monomers (amino acids) present in each
	 *         complex polymer
	 */
	@Deprecated
	public static List<Set<String>> isolateAminoAcidSmiles(
			List<String> notationList) {
		try {
			List<Set<String>> aaList = new ArrayList<Set<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> aaSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.PEPTIDE_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						List<Monomer> aaSubSubList = SimpleNotationParser
								.getMonomerList(simpleNotation,
										Monomer.PEPTIDE_POLYMER_TYPE);
						for (Monomer aa : aaSubSubList) {
							String s = aa.getAlternateId();
							String complexNotation = "PEPTIDE1{[" + s
									+ "]}$$$$";
							try {
								String s2 = ComplexNotationParser
										.getComplexPolymerSMILES(complexNotation);
								aaSubSet.add(s2);
							} catch (Exception e) {
								// e.printStackTrace();
								aaSubSet.add("*");
							}
						}
					}
				}
				aaList.add(aaSubSet);
			}
			return aaList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static SmilesSetsList generateAminoAcidSmiles(
			List<String> notationList) {
		try {
			SmilesSetsList aaList = new SmilesSetsList();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> aaSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.PEPTIDE_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						List<Monomer> aaSubSubList = SimpleNotationParser
								.getMonomerList(simpleNotation,
										Monomer.PEPTIDE_POLYMER_TYPE);
						for (Monomer aa : aaSubSubList) {
							String s = aa.getAlternateId();
							String complexNotation = "PEPTIDE1{[" + s
									+ "]}$$$$";
							try {
								String s2 = ComplexNotationParser
										.getComplexPolymerSMILES(complexNotation);
								aaSubSet.add(s2);
							} catch (Exception e) {
								aaSubSet.add("*");
								aaList.smilesWarningFlag = true;
							}
						}
					}
				}
				aaList.list.add(aaSubSet);
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
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link Set}s of {@link String}s, the SMILES
	 *         strings of the peptides present in each complex polymer
	 */
	@Deprecated
	public static List<Set<String>> isolatePeptideSmiles(
			List<String> notationList) {
		try {
			List<Set<String>> peptideList = new ArrayList<Set<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> peptideSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.PEPTIDE_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String complexNotation = "PEPTIDE1{" + simpleNotation
								+ "}$$$$";
						try {
							String s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							peptideSubSet.add(s);
						} catch (Exception e) {
							// e.printStackTrace();
							peptideSubSet.add("*");
						}
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

	public static SmilesSetsList generatePeptideSmiles(List<String> notationList) {
		try {
			SmilesSetsList peptideList = new SmilesSetsList();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> peptideSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(Monomer.PEPTIDE_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String complexNotation = "PEPTIDE1{" + simpleNotation
								+ "}$$$$";
						try {
							String s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							peptideSubSet.add(s);
						} catch (Exception e) {
							peptideSubSet.add("*");
							peptideList.smilesWarningFlag = true;
						}
					}
				}
				peptideList.list.add(peptideSubSet);
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
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link Set}s of {@link String}s, the SMILES
	 *         strings of the nucleotide monomers present in each complex
	 *         polymer
	 */
	@Deprecated
	public static List<Set<String>> isolateNucleotideSmiles(
			List<String> notationList) {
		try {
			List<Set<String>> monomerList = new ArrayList<Set<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> monomerSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(
							Monomer.NUCLIEC_ACID_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						List<Nucleotide> monomerSubSubList = SimpleNotationParser
								.getNucleotideList(simpleNotation);
						for (Nucleotide monomer : monomerSubSubList) {
							String s = monomer.getNotation();
							String complexNotation = "RNA1{" + s + "}$$$$";
							try {
								String s2 = ComplexNotationParser
										.getComplexPolymerSMILES(complexNotation);
								monomerSubSet.add(s2);
							} catch (Exception e) {
								// e.printStackTrace();
								monomerSubSet.add("*");
							}
						}
					}
				}
				monomerList.add(monomerSubSet);
			}
			return monomerList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static SmilesSetsList generateNucleotideSmiles(
			List<String> notationList) {
		try {
			SmilesSetsList monomerList = new SmilesSetsList();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> monomerSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(
							Monomer.NUCLIEC_ACID_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						List<Nucleotide> monomerSubSubList = SimpleNotationParser
								.getNucleotideList(simpleNotation);
						for (Nucleotide monomer : monomerSubSubList) {
							String s = monomer.getNotation();
							String complexNotation = "RNA1{" + s + "}$$$$";
							try {
								String s2 = ComplexNotationParser
										.getComplexPolymerSMILES(complexNotation);
								monomerSubSet.add(s2);
							} catch (Exception e) {
								monomerSubSet.add("*");
								monomerList.smilesWarningFlag = true;
							}
						}
					}
				}
				monomerList.list.add(monomerSubSet);
			}
			return monomerList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Isolates the SMILES strings of the RNA monomers present.
	 * 
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link Set}s of {@link String}s, the SMILES
	 *         strings of the RNA monomers present in each complex polymer
	 */
	@Deprecated
	public static List<Set<String>> isolateRnaMonomerSmiles(
			List<String> notationList) {
		try {
			List<Set<String>> monomerList = new ArrayList<Set<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> monomerSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(
							Monomer.NUCLIEC_ACID_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						List<Monomer> monomerSubSubList = SimpleNotationParser
								.getMonomerList(simpleNotation,
										Monomer.NUCLIEC_ACID_POLYMER_TYPE);
						for (Monomer monomer : monomerSubSubList) {
							String s = monomer.getAlternateId();
							String complexNotation = "RNA1{[" + s + "]}$$$$";
							try {
								String s2 = ComplexNotationParser
										.getComplexPolymerSMILES(complexNotation);
								monomerSubSet.add(s2);
							} catch (Exception e) {
								// e.printStackTrace();
								monomerSubSet.add("*");
							}
						}
					}
				}
				monomerList.add(monomerSubSet);
			}
			return monomerList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static SmilesSetsList generateRnaMonomerSmiles(
			List<String> notationList) {
		try {
			SmilesSetsList monomerList = new SmilesSetsList();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> monomerSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(
							Monomer.NUCLIEC_ACID_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						List<Monomer> monomerSubSubList = SimpleNotationParser
								.getMonomerList(simpleNotation,
										Monomer.NUCLIEC_ACID_POLYMER_TYPE);
						for (Monomer monomer : monomerSubSubList) {
							String s = monomer.getAlternateId();
							String complexNotation = "RNA1{[" + s + "]}$$$$";
							try {
								String s2 = ComplexNotationParser
										.getComplexPolymerSMILES(complexNotation);
								monomerSubSet.add(s2);
							} catch (Exception e) {
								monomerSubSet.add("*");
								monomerList.smilesWarningFlag = true;
							}
						}
					}
				}
				monomerList.list.add(monomerSubSet);
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
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link Set}s of {@link String}s, the SMILES
	 *         strings of the nucleotide simple polymers present in each complex
	 *         polymer
	 */
	@Deprecated
	public static List<Set<String>> isolateRnaSmiles(List<String> notationList) {
		try {
			List<Set<String>> rnaList = new ArrayList<Set<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> rnaSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(
							Monomer.NUCLIEC_ACID_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String complexNotation = "RNA1{" + simpleNotation
								+ "}$$$$";
						try {
							String s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							rnaSubSet.add(s);
						} catch (Exception e) {
							// e.printStackTrace();
							rnaSubSet.add("*");
						}
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

	public static SmilesSetsList generateRnaSmiles(List<String> notationList) {
		try {
			SmilesSetsList rnaList = new SmilesSetsList();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> rnaSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					if (polymer.getType().equals(
							Monomer.NUCLIEC_ACID_POLYMER_TYPE)) {
						String simpleNotation = polymer.getLabel();
						String complexNotation = "RNA1{" + simpleNotation
								+ "}$$$$";
						try {
							String s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							rnaSubSet.add(s);
						} catch (Exception e) {
							rnaSubSet.add("*");
							rnaList.smilesWarningFlag = true;
						}
					}
				}
				rnaList.list.add(rnaSubSet);
			}
			return rnaList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Isolates the SMILES strings of the CHEM monomers, amino acids and
	 * nucleotides present.
	 * 
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link Set}s of {@link String}s, the SMILES
	 *         strings of the monomers present in each complex polymer
	 */
	@Deprecated
	public static List<Set<String>> isolateAllMonomerSmiles(
			List<String> notationList) {
		try {
			List<Set<String>> monomerList = new ArrayList<Set<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> monomerSubSet = new HashSet<String>();
				String s, s2 = new String();
				for (PolymerNode polymer : polymers) {
					String simpleNotation = polymer.getLabel();
					switch (polymer.getType()) {
					case Monomer.CHEMICAL_POLYMER_TYPE:
						String complexNotation = "CHEM1{" + simpleNotation
								+ "}$$$$";
						try {
							s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							monomerSubSet.add(s);
						} catch (Exception e) {
							// e.printStackTrace();
							monomerSubSet.add("*");
						}
						break;
					case Monomer.PEPTIDE_POLYMER_TYPE:
						List<Monomer> aaSubSubList = SimpleNotationParser
								.getMonomerList(simpleNotation,
										Monomer.PEPTIDE_POLYMER_TYPE);
						for (Monomer aa : aaSubSubList) {
							s = aa.getAlternateId();
							complexNotation = "PEPTIDE1{[" + s + "]}$$$$";
							try {
								s2 = ComplexNotationParser
										.getComplexPolymerSMILES(complexNotation);
								monomerSubSet.add(s2);
							} catch (Exception e) {
								// e.printStackTrace();
								monomerSubSet.add("*");
							}
						}
						break;
					case Monomer.NUCLIEC_ACID_POLYMER_TYPE:
						List<Nucleotide> monomerSubSubList = SimpleNotationParser
								.getNucleotideList(simpleNotation);
						for (Nucleotide monomer : monomerSubSubList) {
							s = monomer.getNotation();
							complexNotation = "RNA1{" + s + "}$$$$";
							try {
								s2 = ComplexNotationParser
										.getComplexPolymerSMILES(complexNotation);
								monomerSubSet.add(s2);
							} catch (Exception e) {
								// e.printStackTrace();
								monomerSubSet.add("*");
							}
						}
					}
				}
				monomerList.add(monomerSubSet);
			}
			return monomerList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static SmilesSetsList generateAllMonomerSmiles(
			List<String> notationList) {
		try {
			SmilesSetsList monomerList = new SmilesSetsList();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> monomerSubSet = new HashSet<String>();
				String s, s2 = new String();
				for (PolymerNode polymer : polymers) {
					String simpleNotation = polymer.getLabel();
					switch (polymer.getType()) {
					case Monomer.CHEMICAL_POLYMER_TYPE:
						String complexNotation = "CHEM1{" + simpleNotation
								+ "}$$$$";
						try {
							s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							monomerSubSet.add(s);
						} catch (Exception e) {
							monomerSubSet.add("*");
							monomerList.smilesWarningFlag = true;
						}
						break;
					case Monomer.PEPTIDE_POLYMER_TYPE:
						List<Monomer> aaSubSubList = SimpleNotationParser
								.getMonomerList(simpleNotation,
										Monomer.PEPTIDE_POLYMER_TYPE);
						for (Monomer aa : aaSubSubList) {
							s = aa.getAlternateId();
							complexNotation = "PEPTIDE1{[" + s + "]}$$$$";
							try {
								s2 = ComplexNotationParser
										.getComplexPolymerSMILES(complexNotation);
								monomerSubSet.add(s2);
							} catch (Exception e) {
								monomerSubSet.add("*");
								monomerList.smilesWarningFlag = true;
							}
						}
						break;
					case Monomer.NUCLIEC_ACID_POLYMER_TYPE:
						List<Nucleotide> monomerSubSubList = SimpleNotationParser
								.getNucleotideList(simpleNotation);
						for (Nucleotide monomer : monomerSubSubList) {
							s = monomer.getNotation();
							complexNotation = "RNA1{" + s + "}$$$$";
							try {
								s2 = ComplexNotationParser
										.getComplexPolymerSMILES(complexNotation);
								monomerSubSet.add(s2);
							} catch (Exception e) {
								monomerSubSet.add("*");
								monomerList.smilesWarningFlag = true;
							}
						}
					}
				}
				monomerList.list.add(monomerSubSet);
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
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of {@link Set}s of {@link String}s, the SMILES
	 *         strings of the simple polymers present in each complex polymer
	 */
	@Deprecated
	public static List<Set<String>> isolateAllSimpleSmiles(
			List<String> notationList) {
		try {
			List<Set<String>> smilesList = new ArrayList<Set<String>>();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> smilesSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					switch (polymer.getType()) {
					case Monomer.CHEMICAL_POLYMER_TYPE:
						String simpleNotation = polymer.getLabel();
						String complexNotation = "CHEM1{" + simpleNotation
								+ "}$$$$";
						try {
							String s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							smilesSubSet.add(s);
						} catch (Exception e) {
							// e.printStackTrace();
							smilesSubSet.add("*");
						}
						break;
					case Monomer.PEPTIDE_POLYMER_TYPE:
						simpleNotation = polymer.getLabel();
						complexNotation = "PEPTIDE1{" + simpleNotation
								+ "}$$$$";
						try {
							String s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							smilesSubSet.add(s);
						} catch (Exception e) {
							// e.printStackTrace();
							smilesSubSet.add("*");
						}
						break;
					case Monomer.NUCLIEC_ACID_POLYMER_TYPE:
						simpleNotation = polymer.getLabel();
						complexNotation = "RNA1{" + simpleNotation + "}$$$$";
						try {
							String s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							smilesSubSet.add(s);
						} catch (Exception e) {
							// e.printStackTrace();
							smilesSubSet.add("*");
						}
						break;
					}
				}
				smilesList.add(smilesSubSet);
			}
			return smilesList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static SmilesSetsList generateAllSimpleSmiles(
			List<String> notationList) {
		try {
			SmilesSetsList smilesList = new SmilesSetsList();
			for (String notation : notationList) {
				List<PolymerNode> polymers = ComplexNotationParser
						.getPolymerNodeList(notation);
				Set<String> smilesSubSet = new HashSet<String>();
				for (PolymerNode polymer : polymers) {
					switch (polymer.getType()) {
					case Monomer.CHEMICAL_POLYMER_TYPE:
						String simpleNotation = polymer.getLabel();
						String complexNotation = "CHEM1{" + simpleNotation
								+ "}$$$$";
						try {
							String s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							smilesSubSet.add(s);
						} catch (Exception e) {
							smilesSubSet.add("*");
							smilesList.smilesWarningFlag = true;
						}
						break;
					case Monomer.PEPTIDE_POLYMER_TYPE:
						simpleNotation = polymer.getLabel();
						complexNotation = "PEPTIDE1{" + simpleNotation
								+ "}$$$$";
						try {
							String s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							smilesSubSet.add(s);
						} catch (Exception e) {
							smilesSubSet.add("*");
							smilesList.smilesWarningFlag = true;
						}
						break;
					case Monomer.NUCLIEC_ACID_POLYMER_TYPE:
						simpleNotation = polymer.getLabel();
						complexNotation = "RNA1{" + simpleNotation + "}$$$$";
						try {
							String s = ComplexNotationParser
									.getComplexPolymerSMILES(complexNotation);
							smilesSubSet.add(s);
						} catch (Exception e) {
							smilesSubSet.add("*");
							smilesList.smilesWarningFlag = true;
						}
						break;
					}
				}
				smilesList.list.add(smilesSubSet);
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
	 * @param notationList
	 *            {@link List} of HELM {@link String}s denoting complex polymers
	 * @return {@link List} of SMILES {@link String}s of the complex polymers
	 */
	@Deprecated
	public static List<String> generateSmilesStrings(List<String> notationList) {
		try {
			List<String> smilesList = new ArrayList<String>();
			for (String notation : notationList) {
				try {
					String smiles = ComplexNotationParser
							.getComplexPolymerSMILES(notation);
					smilesList.add(smiles);
				} catch (Exception e) {
					// e.printStackTrace();
					smilesList.add("*");
				}
			}
			return smilesList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static SmilesList generateSmilesList(List<String> notationList) {
		try {
			SmilesList smilesList = new SmilesList();
			for (String notation : notationList) {
				try {
					String smiles = ComplexNotationParser
							.getComplexPolymerSMILES(notation);
					smilesList.list.add(smiles);
				} catch (Exception e) {
					smilesList.list.add("*");
					smilesList.smilesWarningFlag = true;
				}
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
	 * @param smartsOfInterest
	 *            SMARTS {@String} of substructure to be found
	 * @param smilesList
	 *            {@link List} of SMILES {@link String}s of the complex polymers
	 * @return {@link Set} of {@link Integer}s, the indices of complex polymers
	 *         containing the substructure of interest
	 */

	public static Set<Integer> findMatchingCompounds(String smartsOfInterest,
			List<String> smilesList) {
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		for (int i = 0; i < smilesList.size(); i++) {
			if (SubstructureTest.isSubstructure(smilesList.get(i),
					smartsOfInterest)) {
				indicesOfInterest.add(i);
			}
		}
		return indicesOfInterest;
	}

	/**
	 * Finds complex polymer parts with substructure of interest.
	 * 
	 * @param smartsOfInterest
	 *            SMARTS {@String} of substructure to be found
	 * @param smilesListSet
	 *            {@link List} of {@link Set}s of SMILES {@link String}s of the
	 *            complex polymer parts (monomers/simple polymers/...)
	 * @return {@link Set} of {@link Integer}s, the indices of complex polymers
	 *         with parts containing the substructure of interest
	 */

	public static Set<Integer> findMatchingCompounds2(String smartsOfInterest,
			List<Set<String>> smilesListSet) {
		Set<Integer> indicesOfInterest = new HashSet<Integer>();
		for (int i = 0; i < smilesListSet.size(); i++) {
			Set<String> smilesSet = smilesListSet.get(i);
			for (String smiles : smilesSet) {
				if (SubstructureTest.isSubstructure(smiles, smartsOfInterest)) {
					indicesOfInterest.add(i);
					break;
				}
			}
		}
		return indicesOfInterest;
	}
}
