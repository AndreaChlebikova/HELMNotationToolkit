/**
 * 
 */
package org.helm.notation.search;

import org.helm.notation.search.Constants.*;

/**
 * The SequenceQuery class represents a sequence query.
 * 
 * @author Andrea Chlebikova
 *
 */
public class SequenceQuery extends Query {
	/** {@link String} denoting sequence to be found */
	public String sequence; // TODO delete, or delete queryString
	/** {@link SequenceType} denoting whether the sequence is a peptide/nucleotide sequence */
	public SequenceType sequenceType;

	public SequenceQuery(String sequence, SequenceType sequenceType) {
		this.sequence = sequence;
		this.queryString = sequence;
		this.sequenceType = sequenceType;
	}

	public SequenceQuery(String sequence, SequenceType sequenceType,
			boolean negation) {
		this.sequence = sequence;
		this.queryString = sequence;
		this.sequenceType = sequenceType;
		this.negation = negation;
	}

	public SequenceQuery(String sequence, SequenceType sequenceType,
			SearchType searchType) {
		this.sequence = sequence;
		this.queryString = sequence;
		this.sequenceType = sequenceType;
		this.searchType = searchType;
	}

	public SequenceQuery(String sequence, SequenceType sequenceType,
			SearchType searchType, boolean negation) {
		this.sequence = sequence;
		this.queryString = sequence;
		this.sequenceType = sequenceType;
		this.searchType = searchType;
		this.negation = negation;
	}

	public SequenceQuery(String sequence, SequenceType sequenceType,
			SearchType searchType, int cutoff) {
		this.sequence = sequence;
		this.queryString = sequence;
		this.sequenceType = sequenceType;
		this.searchType = searchType;
		this.cutoff = cutoff;
	}

	public SequenceQuery(String sequence, SequenceType sequenceType,
			SearchType searchType, int cutoff, boolean negation) {
		this.sequence = sequence;
		this.queryString = sequence;
		this.sequenceType = sequenceType;
		this.searchType = searchType;
		this.cutoff = cutoff;
		this.negation = negation;
	}
}
