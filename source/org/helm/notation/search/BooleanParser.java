package org.helm.notation.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.helm.notation.search.Constants.Connector;

/**
 * This class provides methods for converting infix input of boolean connectors between queries into a grouping (tree form).
 * 
 * @author Andrea Chlebikova
 *
 */

public class BooleanParser { // TODO does not necessarily flag up invalid
								// expressions, if they can be interpreted in
								// some way
	private static final Map<Character, Connector> connectorMapping;
	static {
		Map<Character, Connector> map = new HashMap<Character, Connector>();
		for (Connector connector : Connector.values()) {
			map.put(connector.getSymbol(), connector);
		}
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

	/**
	 * Method for producing infix form by combining query and string objects input.
	 * 
	 * @param queryList {@link List} of {@link Query} objects placed between strings denoting logic.
	 * @param booleanConnectorsList {@link List} of {@link String}s containing logic connectors and brackets
	 * @return {@link List} of {@link Object}s to be parsed, in infix form
	 * @throws ExpressionException
	 */
	
	public static List<Object> combineListsToInfix(
			List<Query> queryList, List<String> booleanConnectorsList)
			throws ExpressionException {
		final List<Object> infix = new ArrayList<Object>();

		for (int j = 0; j < booleanConnectorsList.size(); j++) {
			String bool = booleanConnectorsList.get(j);
			for (Connector connector : Connector.values()) {
				bool = bool.replace(connector.name(), String.valueOf(connector.getSymbol()));
			}
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

	/**
	 * Method for parsing infix input of objects into a grouping (tree form).
	 * 
	 * @param infix {@link List} of {@link Object}s to be parsed
	 * @return {@link Grouping} encoding the same logical expression
	 * @throws ExpressionException
	 */
	
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
