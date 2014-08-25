package org.helm.notation.search;

/**
 * The Query class represents queries for exact match searches, both for
 * subsequences and for substructures.
 * 
 * @author Andrea Chlebikova
 *
 */
@Deprecated
public class Query {
	/**
	 * Subsequence/SMARTS string to be found.
	 */
	public String queryString = new String();
	/**
	 * True if searching for subsequence, false if searching for substructure.
	 */
	public Boolean sequence = true;
	/**
	 * True if searching for peptide subsequence, false if searching for nucleic
	 * acid subsequence.
	 */
	public Boolean peptide = true;
	/**
	 * The level at which substructure search is to take place. 'c' for complex
	 * polymer, 's' for simple polymer, 'm' for monomer, 'h' for CHEM
	 * (simple/monomer), 'p' for peptide (simple), 'r' for RNA (simple), 'a' for
	 * amino acid (monomer), 'n' for nucleotide, 'b' for base/sugar/phosphate
	 * (monomer).
	 */
	public char smilesLevel = 'c';
	/**
	 * Looking for compounds not containing the query elements if true.
	 */
	public Boolean negation = false;

	/**
	 * Constructor for Query for sequence-based search.
	 * 
	 * @param s
	 *            {@link String} denoting subsequence to be found
	 * @param seq
	 *            true - sequence-based search
	 * @param pep
	 *            {@link Boolean} - true if searching for peptide subsequence,
	 *            false if searching for nucleic acid subsequence.-
	 */

	public Query(String s, Boolean seq, Boolean pep) {
		queryString = s;
		sequence = true;
		peptide = pep;
	}

	/**
	 * Constructor for Query for sequence-based search.
	 * 
	 * @param s
	 *            {@link String} denoting subsequence to be found
	 * @param pep
	 *            {@link Boolean} - true if searching for peptide subsequence,
	 *            false if searching for nucleic acid subsequence.-
	 */

	public Query(String s, Boolean pep) {
		queryString = s;
		sequence = true;
		peptide = pep;
	}

	/**
	 * Constructor for Query for substructure-based search.
	 * 
	 * @param s
	 *            SMARTS {@link String} denoting substructure to be found
	 * @param seq
	 *            false - substructure-based search
	 * @param l
	 *            {@link char} - The level at which substructure search is to
	 *            take place. 'c' for complex polymer, 's' for simple polymer,
	 *            'm' for monomer, 'h' for CHEM (simple/monomer), 'p' for
	 *            peptide (simple), 'r' for RNA (simple), 'a' for amino acid
	 *            (monomer), 'n' for nucleotide, 'b' for base/sugar/phosphate
	 *            (monomer).
	 */

	public Query(String s, Boolean seq, char l) {
		queryString = s;
		sequence = false;
		smilesLevel = l;
	}

	/**
	 * Constructor for Query for substructure-based search.
	 * 
	 * @param s
	 *            SMARTS {@link String} denoting substructure to be found
	 * @param l
	 *            {@link char} - The level at which substructure search is to
	 *            take place. 'c' for complex polymer, 's' for simple polymer,
	 *            'm' for monomer, 'h' for CHEM (simple/monomer), 'p' for
	 *            peptide (simple), 'r' for RNA (simple), 'a' for amino acid
	 *            (monomer), 'n' for nucleotide, 'b' for base/sugar/phosphate
	 *            (monomer).
	 */

	public Query(String s, char l) {
		queryString = s;
		sequence = false;
		smilesLevel = l;
	}
}