package org.helm.notation.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.helm.notation.search.Constants.Connector;

/**
 * @author Andrea Chlebikova
 *
 */

public class BooleanParser { // TODO does not necessarily flag up invalid
								// expressions, if they can be interpreted in
								// some way
	private static final Map<Character, Connector> connectorMapping;
	static {
		Map<Character, Connector> map = new HashMap<Character, Connector>(); // TODO
																				// can
																				// generalise
		map.put('&', Connector.AND);
		map.put('|', Connector.OR);
		connectorMapping = Collections.unmodifiableMap(map);
	}

	private static void addNode(Stack<Grouping> stack, Object data)
			throws ExpressionException {
		if (stack.size() > 1) {
			Grouping secondNode = stack.pop();
			Grouping firstNode = stack.pop();
			Grouping newGrouping = new Grouping(data, firstNode, secondNode);
			firstNode.parent = newGrouping;
			secondNode.parent = newGrouping;
			stack.push(newGrouping);
		} else
			throw new ExpressionException("Invalid logic in search expression.");
	}

	public static List<Object> combineListsToInfix(
			List<GeneralQuery> queryList, List<String> booleanConnectorsList)
			throws ExpressionException {
		final List<Object> infix = new ArrayList<Object>();

		for (int j = 0; j < booleanConnectorsList.size(); j++) {
			String bool = booleanConnectorsList.get(j);
			bool = bool.replace("AND", "&");
			bool = bool.replace("OR", "|");
			if (!bool.isEmpty()) {
				for (int i = 0; i < bool.length(); i++) {
					if (bool.charAt(i) == '(' || bool.charAt(i) == ')'
							|| connectorMapping.containsKey(bool.charAt(i))) {
						infix.add(bool.charAt(i));
					} else {
						throw new ExpressionException(
								"Invalid term starting with '" + bool.charAt(i)
										+ "' in search query.");
					}
				}
			}
			if (j < booleanConnectorsList.size() - 1)
				infix.add(queryList.get(j));
		}
		return infix;
	}

	@Deprecated
	public static List<Object> combineListsToInfix2(List<Query> queryList,
			List<String> booleanConnectorsList) throws Exception {
		final List<Object> infix = new ArrayList<Object>();

		for (int j = 0; j < booleanConnectorsList.size(); j++) {
			String bool = booleanConnectorsList.get(j);
			bool = bool.replace("AND", "&");
			bool = bool.replace("OR", "|");
			if (!bool.isEmpty()) {
				for (int i = 0; i < bool.length(); i++) {
					if (bool.charAt(i) == '(' || bool.charAt(i) == ')'
							|| connectorMapping.containsKey(bool.charAt(i))) {
						infix.add(bool.charAt(i));
					} else {
						throw new Exception("Invalid term starting with "
								+ bool.charAt(i) + " in search query.");
					}
				}
			}
			if (j < booleanConnectorsList.size() - 1)
				infix.add(queryList.get(j));
		}
		return infix;
	}

	public static Grouping parse(List<Object> infix) throws ExpressionException {
		final Stack<Object> tempStack = new Stack<Object>();
		final Stack<Grouping> finalStack = new Stack<Grouping>();

		for (Object token : infix) {
			if (token.getClass().getName().equals("java.lang.Character")) {
				if (connectorMapping.containsKey(token)) {
					Connector o1 = connectorMapping.get(token);
					while (!tempStack.isEmpty()
							&& connectorMapping.containsValue(tempStack.peek())) {
						Connector o2 = (Connector) tempStack.peek();
						if (o1.getPrecedence() <= o2.getPrecedence()) { // connectors
																		// are
																		// assumed
																		// to be
																		// left-associative
							tempStack.pop();
							addNode(finalStack, o2);
						} else {
							break;
						}
					}
					tempStack.push(o1);
				} else if (token.equals('(')) {
					tempStack.push('(');
				} else if (token.equals(')')) {
					Object topToken = new Object();
					while (!tempStack.isEmpty()) {
						topToken = tempStack.pop();
						if (topToken.equals('(')) {
							break;
						} else {
							addNode(finalStack, topToken);
						}
					}
					if (!topToken.equals('(')) {
						throw new ExpressionException("Unmatched brackets.");
					}
				}
			} else {
				finalStack.push(new Grouping(token));
			}
		}
		while (!tempStack.isEmpty()) {
			Object topToken = tempStack.pop();
			if (topToken.equals('(')) {
				throw new ExpressionException("Unmatched brackets.");
			} else {
				addNode(finalStack, topToken);
			}
		}
		return finalStack.pop();
	}

}
