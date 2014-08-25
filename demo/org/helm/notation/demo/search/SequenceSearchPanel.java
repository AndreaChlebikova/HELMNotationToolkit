package org.helm.notation.demo.search;

import java.awt.*;
import javax.swing.*;

/**
 * @author Andrea Chlebikova
 *
 */
public class SequenceSearchPanel extends Container {

	ButtonGroup isPeptideOrRna = new ButtonGroup();
	JRadioButton isPeptide = new JRadioButton("Peptide");
	JRadioButton isRna = new JRadioButton("Nucleotide");
	JTextField stringTextField = new JTextField(
			"Enter the sequence to be found (IUPAC convention)");

	public SequenceSearchPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		isPeptideOrRna.add(isPeptide);
		isPeptideOrRna.add(isRna);
		isPeptide.setSelected(true);

		c.fill = GridBagConstraints.HORIZONTAL;
		// c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		add(isPeptide, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		add(isRna, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		add(stringTextField, c);
	}
}
