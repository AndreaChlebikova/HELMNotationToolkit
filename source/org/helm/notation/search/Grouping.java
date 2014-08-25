package org.helm.notation.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.helm.notation.search.Constants.*;

/**
 * The Grouping class stores both partial queries, and the way they are
 * logically connected, in a tree structure.
 * 
 * The leaves in the Grouping are usually {@link Query} objects ({@link Matches}
 * during evaluation), while the remaining nodes are {@link Connector}s.
 * 
 * @author Andrea Chlebikova (based on tree structure by Grzegorz Dev)
 *
 */

public class Grouping implements Iterable<Grouping> {
	/** Data held at node ({@link Query}/{@link Matches}/{@link Connector}) */
	public Object data;
	/** Parent ({@link Grouping}) */
	public Grouping parent;
	/** {@link List} of children ({@link Grouping}s) */
	List<Grouping> children;

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}

	public int getLevel() {
		if (this.isRoot())
			return 0;
		else
			return parent.getLevel() + 1;
	}

	/**
	 * Constructor from data at a single node only.
	 * 
	 * @param data
	 *            {@link Object} at node
	 */

	public Grouping(Object data) {
		this.data = data;
		this.children = new LinkedList<Grouping>();
	}

	/**
	 * Constructor used during parsing, which creates a binary tree.
	 * 
	 * @param data
	 *            {@link Object} at node
	 * @param firstNode
	 *            {@link Grouping} first child
	 * @param secondNode
	 *            {@link Grouping} second child
	 */

	public Grouping(Object data, Grouping firstNode, Grouping secondNode) {
		this.data = data;
		this.children = new LinkedList<Grouping>();
		this.children.add(firstNode);
		this.children.add(secondNode);
	}

	/**
	 * Constructor from a list of queries, or existing groupings, and a
	 * connector.
	 * 
	 * @param parentData
	 *            {@link Connector}
	 * @param childrenData
	 *            {@link List} of {@link Object}s ({@link Query}/{@link Matches}
	 *            /{@link Connector}/{@link Grouping})
	 */

	public Grouping(Object parentData, List<Object> childrenData) {
		this.data = parentData;
		this.children = new LinkedList<Grouping>();
		for (Object childData : childrenData) {
			this.addChild(childData);
		}
	}

	/**
	 * Method for adding a child to an existing grouping.
	 * 
	 * @param child
	 *            {@link Object} ({@link Query}/{@link Matches}/
	 *            {@link Connector}/{@link Grouping})
	 * @return {@link Grouping} with added child
	 */

	public Grouping addChild(Object child) {
		Grouping childNode;
		if (child.getClass().getName() != "org.helm.notation.search.Grouping") {
			childNode = new Grouping(child);
		} else {
			childNode = (Grouping) child;
		}
		childNode.parent = this;
		this.children.add(childNode);
		return childNode;
	}

	/**
	 * Method that simplifies initial binary tree obtained when parsing input.
	 * 
	 * @return Simplified {@link Grouping} that is no longer necessarily a
	 *         binary tree (ANDs/ORs brought to equal level if possible).
	 */

	public Grouping regroup() {
		Grouping newGrouping = this;
		boolean changed = true;
		while (changed) {
			changed = false;
			outer: for (Grouping node : newGrouping) {
				for (Grouping child : node.children) {
					if (child.data.equals(node.data)) {
						for (Grouping childOfChild : child.children) {
							node.addChild(childOfChild);
						}
						node.children.remove(child);
						changed = true;
						break outer;
					}
				}
			}
		}
		return newGrouping;
	}

	/**
	 * Method that optimises the grouping for search from left to right.
	 * 
	 * @return {@link Grouping} optimised for search.
	 */

	public Grouping optimise() {
		Grouping newGrouping = this; // TODO improve optimisation
		boolean changed = true;
		while (changed) {
			changed = false;
			outer: for (Grouping node : newGrouping) {
				List<Grouping> matchesChildren = new ArrayList<Grouping>();
				List<Grouping> sequenceQueryChildren = new ArrayList<Grouping>();
				List<Grouping> structureQueryChildren = new ArrayList<Grouping>();
				List<Grouping> otherChildren = new ArrayList<Grouping>();
				List<Grouping> reorderedChildren = new ArrayList<Grouping>();
				for (Grouping child : node.children) {
					if (child.data.getClass().getName() == "org.helm.notation.search.Matches") {
						matchesChildren.add(child);
					} else if (child.data.getClass().getName() == "org.helm.notation.search.SequenceQuery") {
						sequenceQueryChildren.add(child);
					} else if (child.data.getClass().getName() == "org.helm.notation.search.StructureQuery") {
						structureQueryChildren.add(child);
					} else {
						otherChildren.add(child);
					}
				}
				reorderedChildren.addAll(matchesChildren);
				reorderedChildren.addAll(sequenceQueryChildren);
				reorderedChildren.addAll(otherChildren);
				reorderedChildren.addAll(structureQueryChildren);
				if (!node.children.equals(reorderedChildren)) {
					node.children = reorderedChildren;
					changed = true;
					break outer;
				}
			}
		}
		return newGrouping;
	}

	@Override
	public Iterator<Grouping> iterator() {
		GroupingIter iter = new GroupingIter(this);
		return iter;
	}
}