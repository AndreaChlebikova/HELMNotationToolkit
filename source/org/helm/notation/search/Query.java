package org.helm.notation.search;

import org.helm.notation.search.Constants.SearchType;

/**
 * The Query class represents a partial query.
 * 
 * @author Andrea Chlebikova
 *
 */
public class Query {
	/** {@link String} denoting search term - SMARTS for substructure search, sequence for sequence search */
	public String queryString;
	/** {@link SearchType} denoting the type of search to be performed */
	public SearchType searchType = SearchType.SUBMATCH;
	/** {@link int} denoting cutoff to use in the case of a similarity search */
	public int cutoff = 75;
	/** {@link boolean} denoting whether looking for compounds NOT fitting the described search criteria */
	public boolean negation = false;
}