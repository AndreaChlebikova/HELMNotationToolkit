/**
 * 
 */
package org.helm.notation.search;

/**
 * @author Andrea Chlebikova
 *
 */
public class Constants {

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

	public enum SequenceType {
		PEPTIDE, NUCLEOTIDE
	}

	public enum SearchType {
		EXACT, SUBMATCH, SIMILARITY
	}

	public enum SmilesLevel {
		COMPLEX, SIMPLE, MONOMER, CHEM, PEPTIDE, RNA, AMINOACID, NUCLEOTIDE, BASEPHOSPHATESUGAR
	}
}
