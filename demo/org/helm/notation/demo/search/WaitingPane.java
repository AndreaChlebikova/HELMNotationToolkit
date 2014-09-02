package org.helm.notation.demo.search;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * @author Andrea Chlebikova
 *
 */
public class WaitingPane extends Container {
	JLabel titleLabel = new JLabel("Please Wait");
	JButton interruptSearch = new JButton("Interrupt search");

	private final ActionListener interrupt = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			GuiDemo.interrupted = true;
			GuiDemo.displayResults();
		}
	};

	public WaitingPane() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font(titleLabel.getFont().getFontName(),
				Font.BOLD, 16));
		interruptSearch.addActionListener(interrupt);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		add(titleLabel, c);

		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		add(interruptSearch, c);
	}
}
