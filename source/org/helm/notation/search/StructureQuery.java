/**
 * 
 */
package org.helm.notation.search;

import org.helm.notation.search.Constants.*;

/**
 * @author Andrea Chlebikova
 *
 */
public class StructureQuery extends GeneralQuery {
	public String smarts; // TODO delete, or delete queryString
	public SmilesLevel smilesLevel;
	public SearchType searchType = SearchType.SUBMATCH;

	public StructureQuery(String smarts, SmilesLevel smilesLevel) {
		this.smarts = smarts;
		this.queryString = smarts;
		this.smilesLevel = smilesLevel;
	}

	public StructureQuery(String smarts, SmilesLevel smilesLevel,
			boolean negation) {
		this.smarts = smarts;
		this.queryString = smarts;
		this.smilesLevel = smilesLevel;
		this.negation = negation;
	}

	public StructureQuery(String smarts, SmilesLevel smilesLevel,
			SearchType searchType) {
		this.smarts = smarts;
		this.queryString = smarts;
		this.smilesLevel = smilesLevel;
		this.searchType = searchType;
	}

	public StructureQuery(String smarts, SmilesLevel smilesLevel,
			SearchType searchType, boolean negation) {
		this.smarts = smarts;
		this.queryString = smarts;
		this.smilesLevel = smilesLevel;
		this.searchType = searchType;
		this.negation = negation;
	}

	public StructureQuery(String smarts, SmilesLevel smilesLevel,
			SearchType searchType, int cutoff) {
		this.smarts = smarts;
		this.queryString = smarts;
		this.smilesLevel = smilesLevel;
		this.searchType = searchType;
		this.cutoff = cutoff;
	}

	public StructureQuery(String smarts, SmilesLevel smilesLevel,
			SearchType searchType, int cutoff, boolean negation) {
		this.smarts = smarts;
		this.queryString = smarts;
		this.smilesLevel = smilesLevel;
		this.searchType = searchType;
		this.cutoff = cutoff;
		this.negation = negation;
	}
}
