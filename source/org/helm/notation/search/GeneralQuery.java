package org.helm.notation.search;

import org.helm.notation.search.Constants.SearchType;

/**
 * @author Andrea Chlebikova
 *
 */
public class GeneralQuery {
	public String queryString;
	public SearchType searchType = SearchType.SUBMATCH;
	public int cutoff = 75;
	public boolean negation = false;
}