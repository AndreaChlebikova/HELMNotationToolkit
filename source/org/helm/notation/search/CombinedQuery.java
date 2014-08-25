package org.helm.notation.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Chlebikova
 */
@Deprecated
public class CombinedQuery {
	public List<Query> queryList = new ArrayList<Query>();
	public List<String> booleanConnectorsList = new ArrayList<String>();
}
