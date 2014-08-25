/**
 * 
 */
package org.helm.notation.search;

/**
 * @author Andrea Chlebikova
 *
 */
public class Constants {
	
	/**
	 * Denotes boolean connectors (and/or)
	 * @author Andrea Chlebikova
	 *
	 */
	public enum Connector {
		OR(0, '|'), AND(1, '&');

		private final Integer precedence;
		private final Character symbol;

		Connector(Integer precedence, Character symbol) {
			this.precedence = precedence;
			this.symbol = symbol;
		}

		public Integer getPrecedence() {
			return precedence;
		}

		public Character getSymbol() {
			return symbol;
		}
	}

	/**
	 * Denotes type of sequence (peptide/nucleotide)
	 * 
	 * @author Andrea Chlebikova
	 *
	 */
	public enum SequenceType {
		PEPTIDE, NUCLEOTIDE
	}

	/**
	 * Denotes type of search (exact/submatch/similarity)
	 * 
	 * @author Andrea Chlebikova
	 *
	 */
	public enum SearchType {
		EXACT, SUBMATCH, SIMILARITY
	}
	
	/**
	 * Denotes level of substructure search (complex polymer/simple polymer/monomer/chem/peptide/rna/amino acid/nucleotide/ base, phosphate, sugar)
	 * 
	 * @author Andrea Chlebikova
	 *
	 */
	public enum SmilesLevel {
		COMPLEX, SIMPLE, MONOMER, CHEM, PEPTIDE, RNA, AMINOACID, NUCLEOTIDE, BASEPHOSPHATESUGAR
	}
}
