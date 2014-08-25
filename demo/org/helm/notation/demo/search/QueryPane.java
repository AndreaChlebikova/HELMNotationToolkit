/**
 * 
 */
package org.helm.notation.demo.search;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * @author Andrea Chlebikova
 *
 */

public class QueryPane extends Container {

	JLabel titleLabel = new JLabel("Partial Query");
	ButtonGroup isSequenceOrStructure = new ButtonGroup();
	JRadioButton isSequence = new JRadioButton("Subsequence Search");
	JRadioButton isStructure = new JRadioButton("Substructure Search");
	SequenceSearchPanel sequenceSearch = new SequenceSearchPanel();
	SubstructureSearchPanel substructureSearch = new SubstructureSearchPanel();
	JCheckBox isNegated = new JCheckBox(
			"Tick if this partial query is to be negated.");
	JButton anotherQuery = new JButton("Add another partial query");
	JButton combineQueries = new JButton("Combine partial queries");

	private final ActionListener isSequenceOrStructureListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (isSequence.isSelected()) {
				substructureSearch.setVisible(false);
				sequenceSearch.setVisible(true);
				revalidate();
				repaint();
			} else {
				substructureSearch.setVisible(true);
				sequenceSearch.setVisible(false);
				revalidate();
				repaint();
			}
		}
	};

	private final ActionListener addAnotherQuery = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// TODO remove CombinedSearchDemo2.saveQuery(1);
			GuiDemo.saveQuery(1);
		}
	};

	private final ActionListener startCombineQueries = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// TODO remove CombinedSearchDemo2.saveQuery(2);
			GuiDemo.saveQuery(2);
		}
	};

	public QueryPane() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font(titleLabel.getFont().getFontName(),
				Font.BOLD, 16));
		isSequenceOrStructure.add(isSequence);
		isSequenceOrStructure.add(isStructure);
		isSequence.setSelected(true);
		isSequence.addActionListener(isSequenceOrStructureListener);
		isStructure.addActionListener(isSequenceOrStructureListener);
		anotherQuery.addActionListener(addAnotherQuery);
		combineQueries.addActionListener(startCombineQueries);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		add(titleLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridwidth = 3;
		c.gridy = 1;
		add(new JSeparator(), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 2;
		add(isSequence, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 2;
		add(isStructure, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.gridy = 3;
		add(new JSeparator(), c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.gridy = 4;
		sequenceSearch.setMinimumSize(new Dimension(0, 80));
		sequenceSearch.setPreferredSize(new Dimension(1000, 80));
		add(sequenceSearch, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.gridy = 4;
		substructureSearch.setMinimumSize(new Dimension(0, 80));
		substructureSearch.setPreferredSize(new Dimension(1000, 80));
		add(substructureSearch, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.ipady = 0;
		c.gridy = 5;
		add(isNegated, c);

		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		c.gridy = 6;
		add(anotherQuery, c);

		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		c.gridy = 6;
		add(combineQueries, c);

	}
}
