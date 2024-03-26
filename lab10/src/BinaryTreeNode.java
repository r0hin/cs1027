/**
 * BinaryTreeNode represents a node in a binary tree with a left and 
 * right child.
 * 
 * @author Dr. Lewis
 * @author Dr. Chase
 * @author: CS1027
 * @version 1.0, 8/19/08
 */

public class BinaryTreeNode<T> {
	private T dataItem;
	private BinaryTreeNode<T> left, right;

	/**
	 * Creates a new tree node with the specified data.
	 *
	 * @param obj  the element that will become a part of the new tree node
	 */
	BinaryTreeNode (T theData) {
		dataItem = theData;
		left = null;
		right = null;
	}

	public T getData() {
		return dataItem;
	}

	public void setData(T obj) {
		dataItem = obj;
	}

	public void setLeft(BinaryTreeNode<T> node) {
		left = node;
	}

	public BinaryTreeNode<T> getLeft() {
		return left;
	}

	public void setRight(BinaryTreeNode<T> node) {
		right = node;
	}

	public BinaryTreeNode<T> getRight() {
		return right;
	}

}

