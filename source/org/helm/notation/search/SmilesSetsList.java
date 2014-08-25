/**
 * 
 */
package org.helm.notation.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The SmilesSetsList class holds a list of sets of SMILES strings (a set per complex polymer), as well as a flag raised if not all SMILES string could be successfully generated.
 *
 * @author Andrea Chlebikova
 *
 */
public class SmilesSetsList {
	public List<Set<String>> list = new ArrayList<Set<String>>();
	public boolean smilesWarningFlag = false;
}
