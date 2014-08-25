package org.helm.notation.demo.search;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.helm.notation.search.*;

/**
 * @author Andrea Chlebikova
 *
 */
public class CombinationPane extends JScrollPane {

	JLabel titleLabel = new JLabel("Combine queries");
	JLabel description = new JLabel(
			"Please enter boolean connectors between the queries.");
	List<String> booleanConnectorsList = new ArrayList<String>();
	List<JTextField> booleanTextFields = new ArrayList<JTextField>();
	JLabel outputSettings = new JLabel(
			"Please select the desired output details below:");
	JLabel toFile = new JLabel("Print to file:");
	JTextField location = new JTextField(
			"test/org/helm/notation/search/SearchResults.txt");
	JCheckBox indicesToFile = new JCheckBox("Indices");
	JCheckBox helmToFile = new JCheckBox("HELM notations");
	JLabel onScreen = new JLabel("Display on screen:");
	JCheckBox indicesOnScreen = new JCheckBox("Indices");
	JCheckBox helmOnScreen = new JCheckBox("HELM notations");
	JButton searchButton = new JButton("Start search");
	Container content = new Container();

	private final ActionListener startSearch = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			booleanConnectorsList.clear();
			for (JTextField bool : booleanTextFields) {
				booleanConnectorsList.add(bool.getText().toUpperCase()
						.replace(" ", ""));
			}
			GuiDemo.runSearch();
		}
	};

	public CombinationPane(CombinedQuery combinedQuery) {
		content.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font(titleLabel.getFont().getFontName(),
				Font.BOLD, 16));
		description.setHorizontalAlignment(SwingConstants.CENTER);
		description.setFont(new Font(titleLabel.getFont().getFontName(),
				Font.PLAIN, 14));
		outputSettings.setHorizontalAlignment(SwingConstants.CENTER);
		outputSettings.setFont(new Font(titleLabel.getFont().getFontName(),
				Font.PLAIN, 14));
		searchButton.addActionListener(startSearch);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		content.add(titleLabel, c);

		c.gridy = 1;
		content.add(description, c);

		int offset = 2;
		int y = 0;

		for (Query query : combinedQuery.queryList) {
			JTextField textField = new JTextField();
			String negated = "";
			c.gridy = offset + 2 * y;
			booleanTextFields.add(textField);
			content.add(textField, c);
			y++;
			c.gridy = offset + 2 * y - 1;
			if (query.negation) {
				negated = ", negated";
			}
			content.add(new JLabel("Query " + y + ": " + query.queryString
					+ negated), c);
		}

		JTextField textField = new JTextField();
		c.gridy = offset + 2 * y;
		booleanTextFields.add(textField);
		content.add(textField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridwidth = 3;
		c.gridy = offset + 2 * y + 1;
		content.add(outputSettings, c);

		c.gridy = offset + 2 * y + 2;
		content.add(new JSeparator(), c);

		int offset2 = offset + 2 * y + 3;

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = offset2;
		c.gridwidth = 1;
		content.add(toFile, c);

		c.gridx = 1;
		content.add(indicesToFile, c);

		c.gridx = 2;
		content.add(helmToFile, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = offset2 + 1;
		c.gridwidth = 2;
		content.add(location, c);

		c.gridx = 0;
		c.gridy = offset2 + 2;
		c.gridwidth = 1;
		content.add(onScreen, c);

		c.gridx = 1;
		content.add(indicesOnScreen, c);

		c.gridx = 2;
		content.add(helmOnScreen, c);

		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.gridy = offset2 + 3;
		content.add(searchButton, c);

		content.revalidate();
		content.repaint();

		setViewportView(content);
	}

	public CombinationPane(List<GeneralQuery> queryList) {
		content.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font(titleLabel.getFont().getFontName(),
				Font.BOLD, 16));
		description.setHorizontalAlignment(SwingConstants.CENTER);
		description.setFont(new Font(titleLabel.getFont().getFontName(),
				Font.PLAIN, 14));
		outputSettings.setHorizontalAlignment(SwingConstants.CENTER);
		outputSettings.setFont(new Font(titleLabel.getFont().getFontName(),
				Font.PLAIN, 14));
		searchButton.addActionListener(startSearch);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		content.add(titleLabel, c);

		c.gridy = 1;
		content.add(description, c);

		int offset = 2;
		int y = 0;

		for (GeneralQuery query : queryList) {
			JTextField textField = new JTextField();
			String negated = "";
			c.gridy = offset + 2 * y;
			booleanTextFields.add(textField);
			content.add(textField, c);
			y++;
			c.gridy = offset + 2 * y - 1;
			if (query.negation) {
				negated = ", negated";
			}
			content.add(new JLabel("Query " + y + ": " + query.queryString
					+ negated), c);
		}

		JTextField textField = new JTextField();
		c.gridy = offset + 2 * y;
		booleanTextFields.add(textField);
		content.add(textField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridwidth = 3;
		c.gridy = offset + 2 * y + 1;
		content.add(outputSettings, c);

		c.gridy = offset + 2 * y + 2;
		content.add(new JSeparator(), c);

		int offset2 = offset + 2 * y + 3;

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = offset2;
		c.gridwidth = 1;
		content.add(toFile, c);

		c.gridx = 1;
		content.add(indicesToFile, c);

		c.gridx = 2;
		content.add(helmToFile, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = offset2 + 1;
		c.gridwidth = 2;
		content.add(location, c);

		c.gridx = 0;
		c.gridy = offset2 + 2;
		c.gridwidth = 1;
		content.add(onScreen, c);

		c.gridx = 1;
		content.add(indicesOnScreen, c);

		c.gridx = 2;
		content.add(helmOnScreen, c);

		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.gridy = offset2 + 3;
		content.add(searchButton, c);

		content.revalidate();
		content.repaint();

		setViewportView(content);
	}
}
