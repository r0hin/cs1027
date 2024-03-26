/**
 * LinkedBinaryTree implements the BinaryTreeADT interface
 * 
 * @author Dr. Lewis
 * @author Dr. Chase
 * @version 1.0, 8/19/08
 */

import java.util.Iterator;

public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {
	private BinaryTreeNode<T> root; 

	/**
	 * Creates an empty binary tree.
	 */
	public LinkedBinaryTree() {
		root = null;
	}


	/**
	 * Creates a binary tree with the given root node.
	 *
	 * @param rt  the root of the tree
	 */
	public LinkedBinaryTree (BinaryTreeNode<T> rt) {
		root = rt;
	}

	/**
	 * Creates a binary tree with the specified element as its root.
	 *
	 * @param element  the element that will become the root of the new binary
	 *                 tree
	 */
	public LinkedBinaryTree (T dataItem) {
		root = new BinaryTreeNode<T> (dataItem);
	}

	/**
	 * Constructor creates a tree from a node storing dataItem as root and two subtrees
	 * as left and right subtrees of root.
	 * @param dataItem		data to be stored at root node
	 * @param leftSubtree	left subtree
	 * @param rightSubtree	right subtree
	 */

	public LinkedBinaryTree (T dataItem, LinkedBinaryTree<T> leftSubtree,
			LinkedBinaryTree<T> rightSubtree) {
		root = new BinaryTreeNode<T> (dataItem);
		if (leftSubtree != null) root.setLeft(leftSubtree.root);
		else root.setLeft(null);

		if (rightSubtree !=null) root.setRight(rightSubtree.root);
		else root.setRight(null);
	}   

	/**
	 * Returns a reference to root node
	 *
	 * @return                           a reference to the root node
	 */
	public BinaryTreeNode<T> getRoot() {
		return root;
	}   

	/**
	 * Returns a reference to the element at the root
	 *
	 * @return                           a reference to the specified target
	 * @throws EmptyCollectionException  if the tree is empty
	 */
	public T getDataRoot() throws EmptyCollectionException {
		if (root == null) throw new EmptyCollectionException("LinkedBinaryTree");
		return root.getData();
	}

	/**
	 * Returns true if this binary tree is empty and false otherwise.
	 *
	 * @return  true if this binary tree is empty
	 */
	public boolean isEmpty() {
		return (root == null);
	}

	/**
	 * Returns the integer size of this tree.
	 *
	 * @return  the integer size of this tree
	 */
	public int size(BinaryTreeNode<T> r) {
		int count = 0;
		if (r != null) {
			count = 1 + size(r.getLeft()) + size(r.getRight());
		}
		return count;
	}

	/**
	 * Returns true if the tree with root r contains an element that matches the
	 * specified target element and false otherwise.
	 *
	 * @param r							root r of a binary tree
	 * @param targetElement              the element being sought in this tree
	 * @return                           true if the element in is this tree
	 * @throws ElementNotFoundException  if an element not found exception occurs
	 */
	public boolean contains (BinaryTreeNode<T> r, T targetElement) {
		boolean found = false;
		if (r != null) {
			if (r.getData().equals(targetElement)) {
				found = true;
			} else {
				found = contains(r.getLeft(), targetElement);
				if (!found) {
					found = contains(r.getRight(), targetElement);
				}
			}
		}
		return found;
	}

	/**
	 * Performs an inorder traversal on this binary tree by calling an
	 * overloaded, recursive inorder method that starts with
	 * the root.
	 *
	 * @return  an in order iterator over this binary tree
	 */
	public Iterator<T> iteratorInOrder() {
		ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
		inorder (root, tempList);

		return tempList.iterator();
	}

	/**
	 * Performs a recursive inorder traversal.
	 *
	 * @param node      the node to be used as the root for this traversal
	 * @param tempList  the temporary list for use in this traversal
	 */
	protected void inorder (BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
		if (node != null) {
			inorder (node.getLeft(), tempList);
			tempList.addToRear(node.getData());
			inorder (node.getRight(), tempList);
		}
	}

	/**
	 * Performs an preorder traversal on this binary tree by calling 
	 * an overloaded, recursive preorder method that starts with
	 * the root.
	 *
	 * @return  an pre order iterator over this tree
	 */
	public Iterator<T> iteratorPreOrder() {
		ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
		preorder (root, tempList);

		return tempList.iterator();
	}

	/**
	 * Performs a recursive preorder traversal.
	 *
	 * @param node  the node to be used as the root for this traversal
	 * @param tempList  the temporary list for use in this traversal
	 */
	protected void preorder (BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
		// recursive preorder traversal
		if (node != null) {
			tempList.addToRear(node.getData());
			preorder (node.getLeft(), tempList);
			preorder (node.getRight(), tempList);
		}
	}

	/**
	 * Performs an postorder traversal on this binary tree by calling
	 * an overloaded, recursive postorder method that starts
	 * with the root.
	 *
	 * @return  a post order iterator over this tree
	 */
	public Iterator<T> iteratorPostOrder() {
		ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
		postorder (root, tempList);

		return tempList.iterator();
	}

	/**
	 * Performs a recursive postorder traversal.
	 *
	 * @param node      the node to be used as the root for this traversal
	 * @param tempList  the temporary list for use in this traversal
	 */
	protected void postorder (BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
		// recursive postorder traversal
		if (node != null) {
			postorder (node.getLeft(), tempList);
			postorder (node.getRight(), tempList);
			tempList.addToRear(node.getData());
		}
	}

	/**
	 * Performs a levelorder traversal on this binary tree, using a
	 * templist.
	 *
	 * @return  a levelorder iterator over this binary tree
	 */
	public Iterator<T> iteratorLevelOrder() {
		ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
		LinkedQueue<BinaryTreeNode<T>> Q = new LinkedQueue<BinaryTreeNode<T>>();
		Q.enqueue(root);
		while (!Q.isEmpty()) {
			BinaryTreeNode<T> v = Q.dequeue();
			tempList.addToRear(v.getData());
			if (v.getLeft() != null) Q.enqueue(v.getLeft());
			if (v.getRight() != null) Q.enqueue(v.getRight());
		}
		
		return tempList.iterator();
	}


	public String toString() {
		String s = recFormatTree(root,"");
		return s;
	}
	
	private String recFormatTree(BinaryTreeNode<T> node, String space) {
		if (node == null) return "";
		
		String s = recFormatTree(node.getRight(), space+"\t");
		s += space+node.getData()+"\n";
		s += recFormatTree(node.getLeft(), space+"\t");
		return s;
	}

}

