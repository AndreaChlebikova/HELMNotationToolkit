package org.helm.notation.search;

import java.util.ArrayList;
import java.util.List;

/**
 * The SmilesList class holds a list of SMILES strings, as well as a flag raised if not all SMILES string could be successfully generated.
 * 
 * @author Andrea Chlebikova
 *
 */

public class SmilesList {
	public List<String> list = new ArrayList<String>();
	public boolean smilesWarningFlag = false;
}
