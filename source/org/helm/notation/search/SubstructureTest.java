package org.helm.notation.search;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
//import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
//import org.openscience.cdk.isomorphism.matchers.IQueryAtomContainer;
//import org.openscience.cdk.isomorphism.matchers.QueryAtomContainerCreator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.smiles.smarts.SMARTSQueryTool;

/**
 * This class provides a method for confirming whether a substructure is present
 * in a molecule (currently using the SMARTSQueryTool from CDK). //TODO try
 * using other algorithms
 *
 * @author Andrea Chlebikova
 */

public class SubstructureTest {
	public static void main(String[] args) {
		try {
			System.out.println(isSubstructure("[SeH]C[C@H](N[*])C([*])=O",
					"[Se]"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Checks whether a substructure is present in a molecule.
	 * 
	 * @param moleculeSmiles
	 *            SMILES {@String} of a molecule
	 * @param querySmiles
	 *            SMARTS {@String} of substructure to be found
	 * @return {@link boolean}, true if molecule contains the substructure,
	 *         false otherwise
	 */

	public static boolean isSubstructure(String moleculeSmiles,
			String querySmiles) {
		try {
			SmilesParser sp = new SmilesParser(
					DefaultChemObjectBuilder.getInstance());
			IAtomContainer atomContainerMolecule = sp
					.parseSmiles(moleculeSmiles);
			SMARTSQueryTool querytool = new SMARTSQueryTool(querySmiles);
			return querytool.matches(atomContainerMolecule); // suffers from
			// cyclopropane/isobutane
			// confusion,
			// and empty
			// query
			// problems
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
