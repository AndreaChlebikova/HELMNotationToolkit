/**
 * 
 */
package org.helm.notation.demo.search;

import java.awt.*;

import javax.swing.*;

import org.helm.notation.search.*;

/**
 * @author Andrea Chlebikova
 *
 */
public class ResultsPane extends JScrollPane {
	JLabel titleLabel = new JLabel("Search Results");
	JTextArea resultsText = new JTextArea(
			"You selected for no results to be displayed on the screen, but they have now been printed to the file.");
	Container content = new Container();
	
	public ResultsPane(Matches matches, boolean helmS, boolean helmF,
			boolean indicesS, boolean indicesF) {
		content.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font(titleLabel.getFont().getFontName(),
				Font.BOLD, 16));
		if (matches.indices().isEmpty()) {
			resultsText.setText(matches.text() + "\nNo matches were found.");
		} else {
			if (helmS) {
				if (indicesS) {
					resultsText.setText(matches.text() + "List of matches:\n"
							+ matches.indicesAndHelms());
				} else {
					resultsText.setText(matches.text() + "List of matches:\n"
							+ matches.helms());
				}
			} else if (indicesS) {
				resultsText.setText(matches.text() + "List of matches:\n"
						+ matches.indices());
			} else {
				resultsText
						.setText(matches.text()
								+ "You selected for no results to be displayed on the screen, but they have now been printed to the file.");
			}
		}
		resultsText.setEditable(false);
		resultsText.setBackground(this.getBackground());

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		content.add(titleLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		content.add(resultsText, c);

		content.revalidate();
		content.repaint();

		setViewportView(content);
	}
}
