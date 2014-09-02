package org.helm.notation.demo.search;

import java.awt.*;

import javax.swing.*;

/**
 * @author Andrea Chlebikova
 *
 */
public class SubstructureSearchPanel extends Container {

	ButtonGroup searchLevel = new ButtonGroup();
	JRadioButton isComplex = new JRadioButton("Complex Polymer");
	JRadioButton isSimple = new JRadioButton("Simple Polymer");
	JRadioButton isMonomer = new JRadioButton("Monomer");
	JRadioButton isChem = new JRadioButton("CHEM");
	JRadioButton isPeptide = new JRadioButton("Peptide");
	JRadioButton isRna = new JRadioButton("Nucleotide Polymer");
	JRadioButton isAminoAcid = new JRadioButton("Amino Acid");
	JRadioButton isNucleotide = new JRadioButton("Nucleotide");
	JRadioButton isBasePhosphateSugar = new JRadioButton("Base/Phosphate/Sugar");
	JTextField stringTextField = new JTextField(
			"Enter the SMARTS string for the substructure");

	public SubstructureSearchPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		searchLevel.add(isComplex);
		searchLevel.add(isSimple);
		searchLevel.add(isMonomer);
		searchLevel.add(isChem);
		searchLevel.add(isPeptide);
		searchLevel.add(isRna);
		searchLevel.add(isAminoAcid);
		searchLevel.add(isNucleotide);
		searchLevel.add(isBasePhosphateSugar);
		isComplex.setSelected(true);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		add(isComplex, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		add(isSimple, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		add(isMonomer, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 0;
		add(isChem, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 4;
		c.gridy = 0;
		add(isPeptide, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		add(isRna, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		add(isAminoAcid, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 1;
		add(isNucleotide, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 1;
		add(isBasePhosphateSugar, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 5;
		add(stringTextField, c);
	}
}
