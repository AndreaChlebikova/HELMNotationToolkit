package org.helm.notation.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to hold all information used during search for a given structure.
 * 
 * @author Andrea Chlebikova
 *
 */
public class Attributes {
//TODO This class is currently not used in the rest of the code, but is included for future use
	public String helmNotation = new String();
	public List<Sequence> peptideSequences = new ArrayList<Sequence>();
	public List<Sequence> nucleotideSequences = new ArrayList<Sequence>();
	public Set<String> complexSmilesSet = new HashSet<String>();
	public Set<String> simpleSmilesSet = new HashSet<String>();
	public Set<String> monomerSmilesSet = new HashSet<String>();
	public Set<String> chemSmilesSet = new HashSet<String>();
	public Set<String> peptideSmilesSet = new HashSet<String>();
	public Set<String> rnaSmilesSet = new HashSet<String>();
	public Set<String> aminoAcidSmilesSet = new HashSet<String>();
	public Set<String> nucleotideSmilesSet = new HashSet<String>();
	public Set<String> basePhosphateSugarSmilesSet = new HashSet<String>();
}