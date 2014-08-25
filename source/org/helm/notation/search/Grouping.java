package org.helm.notation.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Andrea Chlebikova (based on tree structure by Grzegorz Dev)
 *
 */

public class Grouping implements Iterable<Grouping> {
	public Object data; // Nodes are Connectors (AND/OR), as per Constants;
	// leaves are Queries
	public Grouping parent;
	List<Grouping> children;

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}

	public Grouping(Object data) {
		this.data = data;
		this.children = new LinkedList<Grouping>();
	}

	public Grouping(Object data, Grouping firstNode, Grouping secondNode) { // used
		// in
		// initial
		// tree
		// construction
		// from
		// parser
		this.data = data;
		this.children = new LinkedList<Grouping>();
		this.children.add(firstNode);
		this.children.add(secondNode);
	}

	public Grouping(Object parentData, List<Object> childrenData) {
		this.data = parentData;
		this.children = new LinkedList<Grouping>();
		for (Object childData : childrenData) {
			this.addChild(childData);
		}
	}

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

	public int getLevel() {
		if (this.isRoot())
			return 0;
		else
			return parent.getLevel() + 1;
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