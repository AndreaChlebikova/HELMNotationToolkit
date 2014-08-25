package org.helm.notation.demo.search;

/**
 * @author Andrea Chlebikova
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;

import org.helm.notation.search.*;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.smiles.smarts.SMARTSQueryTool;

@Deprecated
public class CombinedSearchDemo2 {

	static JFrame frame = new JFrame("HELM Search");
	static QueryPane queryPane;
	static CombinationPane combinationPane;
	static ResultsPane resultsPane;
	static WaitingPane waitingPane;
	static CombinedQuery combinedQuery = new CombinedQuery();
	static List<String> notationList;
	static Matches matches = new Matches();
	static boolean interrupted = false;

	public static void main(String[] args) {

		try {
			notationList = Files.readAllLines(
					Paths.get("test/org/helm/notation/search/HELMStrings.txt"),
					Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// check threading later
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.setSize(700, 300);
				frame.setResizable(false);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.toFront();

				loadNewQuery();
			}
		});
	}

	public static void loadNewQuery() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				queryPane = new QueryPane();
				queryPane.substructureSearch.setVisible(false);
				queryPane.revalidate();
				frame.setContentPane(queryPane);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

	public static void loadCombineQueries() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				combinationPane = new CombinationPane(combinedQuery);
				frame.setContentPane(combinationPane);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

	public static void saveQuery(int i) {
		Query query = null;
		if (queryPane.isSequence.isSelected()) {
			if (invalidSequence(
					queryPane.sequenceSearch.stringTextField.getText(),
					queryPane.sequenceSearch.isPeptide.isSelected())) {
				JOptionPane.showMessageDialog(frame, "Invalid search string.");
			} else {
				query = new Query(
						queryPane.sequenceSearch.stringTextField.getText(),
						queryPane.sequenceSearch.isPeptide.isSelected());
			}
		} else {
			if (invalidSmarts(queryPane.substructureSearch.stringTextField
					.getText())) {
				JOptionPane.showMessageDialog(frame,
						"Invalid SMARTS expression.");
			} else {
				char level = 'c';
				if (queryPane.substructureSearch.isComplex.isSelected()) {
					level = 'c';
				} else if (queryPane.substructureSearch.isSimple.isSelected()) {
					level = 's';
				} else if (queryPane.substructureSearch.isMonomer.isSelected()) {
					level = 'm';
				} else if (queryPane.substructureSearch.isChem.isSelected()) {
					level = 'h';
				} else if (queryPane.substructureSearch.isPeptide.isSelected()) {
					level = 'p';
				} else if (queryPane.substructureSearch.isRna.isSelected()) {
					level = 'r';
				} else if (queryPane.substructureSearch.isAminoAcid
						.isSelected()) {
					level = 'a';
				} else if (queryPane.substructureSearch.isNucleotide
						.isSelected()) {
					level = 'n';
				} else if (queryPane.substructureSearch.isBasePhosphateSugar
						.isSelected()) {
					level = 'b';
				}
				query = new Query(
						queryPane.substructureSearch.stringTextField.getText(),
						level);
			}
		}
		if (query != null) {
			if (queryPane.isNegated.isSelected()) {
				query.negation = true;
			}
			combinedQuery.queryList.add(query);
			if (i == 1) {
				CombinedSearchDemo2.loadNewQuery();
			} else {
				CombinedSearchDemo2.loadCombineQueries();
			}
		}
	}

	public static void runSearch() {

		if (invalidSearch()) {
			JOptionPane.showMessageDialog(frame, "Invalid search expression.");
		} else if (!(combinationPane.helmOnScreen.isSelected()
				| combinationPane.helmToFile.isSelected()
				| combinationPane.indicesOnScreen.isSelected() | combinationPane.indicesToFile
					.isSelected())) {
			JOptionPane.showMessageDialog(frame, "No ouput option selected.");
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					waitingPane = new WaitingPane();
					frame.setContentPane(waitingPane);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				}
			});

			Thread thread = new Thread() {
				public void run() {
					searching();
				}
			};
			thread.start();
		}
	}

	private static void searching() {
		for (String bool : combinationPane.booleanConnectorsList) {
			combinedQuery.booleanConnectorsList.add(bool);
		}
		try {
			BooleanParser.parse(BooleanParser.combineListsToInfix2(
					combinedQuery.queryList,
					combinationPane.booleanConnectorsList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		matches = CombinedSearch.performUnoptimisedSearchDemo(combinedQuery,
				notationList); // change to proper search later
		displayResults();
	}

	public static void displayResults() {
		if (interrupted) {
			matches.timeoutWarningFlag = true;
		}
		if (combinationPane.helmToFile.isSelected()
				|| combinationPane.indicesToFile.isSelected()) {
			try (PrintWriter out = new PrintWriter(
					combinationPane.location.getText())) {
				if (combinationPane.helmToFile.isSelected()) {
					if (combinationPane.indicesToFile.isSelected()) {
						out.println(matches.indicesAndHelms());
					} else {
						out.println(matches.helms());
					}
				} else if (combinationPane.indicesToFile.isSelected()) {
					out.println(matches.indices());
				}
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace(); // catch this at input stage
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				resultsPane = new ResultsPane(matches,
						combinationPane.helmOnScreen.isSelected(),
						combinationPane.helmToFile.isSelected(),
						combinationPane.indicesOnScreen.isSelected(),
						combinationPane.indicesToFile.isSelected());
				frame.setContentPane(resultsPane);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

	private static boolean invalidSmarts(String smarts) {
		try {
			SMARTSQueryTool test = new SMARTSQueryTool(smarts);
		} catch (CDKException e) {
			return true;
		}
		return false;
	}

	private static boolean invalidSequence(String sequence, boolean peptide) {
		Pattern valid;
		if (peptide) {
			valid = Pattern.compile("[a-zA-Z-]+\\*?"); // change + to * if empty
														// search string allowed
		} else {
			valid = Pattern.compile("[a-dghkmnr-yA-DGHKMNR-YA-]+");
		}
		Matcher matcher = valid.matcher(sequence);
		if (matcher.matches()) {
			return false;
		}
		return true;
	}

	@Deprecated
	private static boolean invalidSearch() {
		String expression = "";
		for (int i = 0; i < combinationPane.booleanConnectorsList.size(); i++) {
			expression = expression
					.concat(combinationPane.booleanConnectorsList.get(i));
			expression = expression.concat("true");
		}
		expression = expression.substring(0, expression.length() - 4);
		expression = expression.replace("AND", "&&");
		expression = expression.replace("OR", "||");
		expression = expression.replace("NOT", "!");
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		Object result = null;
		try {
			result = engine.eval(expression);
		} catch (ScriptException e) {
			return true;
		}
		return false; // TODO insert proper check later
	}
}
