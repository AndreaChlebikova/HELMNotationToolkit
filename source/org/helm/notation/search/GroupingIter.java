package org.helm.notation.search;

import java.util.Iterator;

/**
 * Iterator for tree structure by Grzegorz Dev
 *
 */
public class GroupingIter implements Iterator<Grouping> {

	enum ProcessStages {
		ProcessParent, ProcessChildCurNode, ProcessChildSubNode
	}

	private Grouping grouping;

	public GroupingIter(Grouping grouping) {
		this.grouping = grouping;
		this.doNext = ProcessStages.ProcessParent;
		this.childrenCurNodeIter = grouping.children.iterator();
	}

	private ProcessStages doNext;
	private Grouping next;
	private Iterator<Grouping> childrenCurNodeIter;
	private Iterator<Grouping> childrenSubNodeIter;

	@Override
	public boolean hasNext() {
		if (this.doNext == ProcessStages.ProcessParent) {
			this.next = this.grouping;
			this.doNext = ProcessStages.ProcessChildCurNode;
			return true;
		}

		if (this.doNext == ProcessStages.ProcessChildCurNode) {
			if (childrenCurNodeIter.hasNext()) {
				Grouping childDirect = childrenCurNodeIter.next();
				childrenSubNodeIter = childDirect.iterator();
				this.doNext = ProcessStages.ProcessChildSubNode;
				return hasNext();
			} else {
				this.doNext = null;
				return false;
			}
		}

		if (this.doNext == ProcessStages.ProcessChildSubNode) {
			if (childrenSubNodeIter.hasNext()) {
				this.next = childrenSubNodeIter.next();
				return true;
			} else {
				this.next = null;
				this.doNext = ProcessStages.ProcessChildCurNode;
				return hasNext();
			}
		}
		return false;
	}

	@Override
	public Grouping next() {
		return this.next;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}