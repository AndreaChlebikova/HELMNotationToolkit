package org.helm.notation.search;

import org.helm.notation.search.Constants.*;

/**
 * The StructureQuery class represents a structure query.
 * 
 * @author Andrea Chlebikova
 *
 */
public class StructureQuery extends Query {
	/** {@link SmilesLevel} denoting what level the search is to be carried out on */
	public SmilesLevel smilesLevel;

	public StructureQuery(String smarts, SmilesLevel smilesLevel) {
		this.queryString = smarts;
		this.smilesLevel = smilesLevel;
	}

	public StructureQuery(String smarts, SmilesLevel smilesLevel,
			boolean negation) {
		this.queryString = smarts;
		this.smilesLevel = smilesLevel;
		this.negation = negation;
	}

	public StructureQuery(String smarts, SmilesLevel smilesLevel,
			SearchType searchType) {
		this.queryString = smarts;
		this.smilesLevel = smilesLevel;
		this.searchType = searchType;
	}

	public StructureQuery(String smarts, SmilesLevel smilesLevel,
			SearchType searchType, boolean negation) {
		this.queryString = smarts;
		this.smilesLevel = smilesLevel;
		this.searchType = searchType;
		this.negation = negation;
	}

	public StructureQuery(String smarts, SmilesLevel smilesLevel,
			SearchType searchType, int cutoff) {
		this.queryString = smarts;
		this.smilesLevel = smilesLevel;
		this.searchType = searchType;
		this.cutoff = cutoff;
	}

	public StructureQuery(String smarts, SmilesLevel smilesLevel,
			SearchType searchType, int cutoff, boolean negation) {
		this.queryString = smarts;
		this.smilesLevel = smilesLevel;
		this.searchType = searchType;
		this.cutoff = cutoff;
		this.negation = negation;
	}
}
