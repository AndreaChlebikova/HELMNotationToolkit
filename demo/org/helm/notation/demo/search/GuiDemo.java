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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import org.helm.notation.search.*;
import org.helm.notation.search.Constants.*;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.smiles.smarts.SMARTSQueryTool;

public class GuiDemo {

	static JFrame frame = new JFrame("HELM Search");
	static QueryPane queryPane;
	static CombinationPane combinationPane;
	static ResultsPane resultsPane;
	static WaitingPane waitingPane;
	static Grouping grouping;
	static List<Query> queryList = new ArrayList<Query>();
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
		// TODO check threading
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
				combinationPane = new CombinationPane(queryList);
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
				if (queryPane.sequenceSearch.isPeptide.isSelected()) {
					query = new SequenceQuery(
							queryPane.sequenceSearch.stringTextField.getText(),
							SequenceType.PEPTIDE);
				} else {
					query = new SequenceQuery(
							queryPane.sequenceSearch.stringTextField.getText(),
							SequenceType.NUCLEOTIDE);
				}
			}
		} else {
			if (invalidSmarts(queryPane.substructureSearch.stringTextField
					.getText())) {
				JOptionPane.showMessageDialog(frame,
						"Invalid SMARTS expression.");
			} else {
				SmilesLevel level = SmilesLevel.COMPLEX;
				if (queryPane.substructureSearch.isComplex.isSelected()) {
					level = SmilesLevel.COMPLEX;
				} else if (queryPane.substructureSearch.isSimple.isSelected()) {
					level = SmilesLevel.SIMPLE;
				} else if (queryPane.substructureSearch.isMonomer.isSelected()) {
					level = SmilesLevel.MONOMER;
				} else if (queryPane.substructureSearch.isChem.isSelected()) {
					level = SmilesLevel.CHEM;
				} else if (queryPane.substructureSearch.isPeptide.isSelected()) {
					level = SmilesLevel.PEPTIDE;
				} else if (queryPane.substructureSearch.isRna.isSelected()) {
					level = SmilesLevel.RNA;
				} else if (queryPane.substructureSearch.isAminoAcid
						.isSelected()) {
					level = SmilesLevel.AMINOACID;
				} else if (queryPane.substructureSearch.isNucleotide
						.isSelected()) {
					level = SmilesLevel.NUCLEOTIDE;
				} else if (queryPane.substructureSearch.isBasePhosphateSugar
						.isSelected()) {
					level = SmilesLevel.BASEPHOSPHATESUGAR;
				}
				query = new StructureQuery(
						queryPane.substructureSearch.stringTextField.getText(),
						level);
			}
		}
		if (query != null) {
			if (queryPane.isNegated.isSelected()) {
				query.negation = true;
			}
			queryList.add(query);
			if (i == 1) {
				loadNewQuery();
			} else {
				loadCombineQueries();
			}
		}
	}

	public static void runSearch() {
		try {
			grouping = BooleanParser.parse(BooleanParser.combineListsToInfix(
					queryList, combinationPane.booleanConnectorsList));
			if (!(combinationPane.helmOnScreen.isSelected()
					| combinationPane.helmToFile.isSelected()
					| combinationPane.indicesOnScreen.isSelected() | combinationPane.indicesToFile
					.isSelected())) {
				JOptionPane.showMessageDialog(frame,
						"No ouput option selected.");
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, e.getMessage());
		}
	}

	private static void searching() {
		matches = CombinedSearch.performSearch(grouping, notationList);
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
				e.printStackTrace(); // TODO catch at input stage
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
}
