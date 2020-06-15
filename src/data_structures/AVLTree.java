/**
 * 
 */
package data_structures;

import java.util.Iterator;

/**
 * Creates a binary AVLtree data structure that sorts based off of the 
 * entered key and stores the entered value. The tree auto balances 
 * when a new element is added to reduce the complexity of the data structure.
 * @author Peter Conant
 * @param <V> the value that will be stored in the tree
 * @param <K> The Key for sorting entries
 *
 */

public class AVLTree<K, V> implements AVLTreeI<K, V> {

	/**
	 * Creates a new Node that will hold the key and data for each new entry.
	 * It also has a pointer for its left and right child Node and parent Node.
	 */
	class Node<K,V>{
		V value;
		K key;
		Node<K,V> left;
		Node<K,V> right;
		Node<K,V> parent;
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			left = right = parent = null;
		}
	}

	Node<K,V> root;
	int currentSize;

	/**
	 *Constructor, sets root and current size of the tree
	 */
	public AVLTree() {
		root = null;
		currentSize = 0;
	}

	/**
	 * Adds a new node to the tree and will auto balance after each entry 
	 */
	@Override
	public void add(K key, V value) {
		Node<K,V> node = new Node<K,V>(key, value);
		if (root == null) {//if the tree is empty then new node is the root
			root = node;
			currentSize++;
			return;
		}
		add(root, node);

	}

	/**
	 * Is called by public add method when a node other than the root
	 * has to be added. 
	 * @param parent, the node that the new Node is being compared to
	 * @param newNode, node being added to the tree
	 */
	private void add(Node<K,V> parent, Node<K,V> newNode) {
		if(((Comparable<K>) newNode.key).compareTo(parent.key)>0) {//go right

			if(parent.right == null) {//place node here if there is no node
				parent.right = newNode;
				newNode.parent = parent;
				currentSize++;
			}
			else {
				add(parent.right, newNode);//call add on right node
			}
		}
		else {//go left

			if(parent.left == null) {//place node here if there is no node
				parent.left = newNode;
				newNode.parent = parent;
				currentSize++;
			}
			else {
				add(parent.left, newNode);//call add on left node
			}
		}
		checkBalance(newNode);//make sure the new node does not break rules of the 
	}
	
	/**
	 * Checks to see if the the tree is unbalanced. Compares the height of 
	 * the left and right side to determine if the tree must be rebalanced
	 * @param node
	 */

	public void checkBalance(Node<K,V> node) {
		if((height(node.left) - height(node.right) >1) || (height(node.left) - height(node.right) <-1)) {
			rebalance(node);
		}
		if(node.parent == null)
			return;
		checkBalance(node.parent);
	}

	/**
	 * rebalances the Node by checking the height of the all the nodes
	 * @param node
	 */
	public void rebalance(Node<K,V> node) {
		if(height(node.left)-height(node.right)> 1) {
			if(height(node.left.left)>height(node.left.right)) 
				node = rightRotate(node);
			else 
				node = leftRightRotate(node);
		}
		else{
			if(height(node.right.right)>height(node.right.left)) 
				node = leftRotate(node);

			else 
				node = rightLeftRotate(node);
		}
		if(node.parent == null)
			root = node;
	}

	/** 
	 * Rotates the grand parent of the trouble node to the left to balance the tree
	 * @param node
	 * @return
	 */
	public Node<K,V> leftRotate(Node<K,V> node) {
		Node<K,V> temp = node.right;
		node.right = temp.left;
		
		temp.left = node;
		if(node.parent != null) {
			if(node.parent.left == node) {//if the grandparent is a left child
				node.parent.left = temp;
			}
			else {//if the grandparent is a right child,
				node.parent.right = temp;
			}
		}
		temp.parent = node.parent;
		node.parent = temp;
		return temp;
	}

	/**
	 * Rotates the grand parent of the trouble node to the right to balance the tree
	 * @param node
	 * @return
	 */
	public Node<K,V> rightRotate(Node<K,V> node){
		Node<K,V> temp = node.left;
		node.left = temp.right;//rotate the 
		temp.right = node;
		if(node.parent != null) {
			if(node.parent.left == node) {//if the grandparent is a left child
				node.parent.left = temp;
			}
			else {//if the grandparent is a right child,
				node.parent.right = temp;
			}
		}
		temp.parent = node.parent;
		node.parent = temp;
		return temp;
	}

	public Node<K,V> rightLeftRotate(Node<K,V> node){
		node.right = rightRotate(node.right);
		return leftRotate(node);
	}
	
	public Node<K,V> leftRightRotate(Node<K,V> node){
		node.left = leftRotate(node.left);
		return rightRotate(node);
	}

	/**
	 * searches the tree for a specified key and returns true if 
	 * the key is found
	 */
	@Override
	public boolean contains(K key) {
		return contains(key,root);
	}

	private boolean contains(K key, Node<K,V> node) {
		if(node == null)
			return false;
		if(((Comparable<K>)node.key).compareTo(key) == 0)
			return true;
		if(((Comparable<K>)node.key).compareTo(key)<0)
			return contains(key,node.right);
		return contains(key,node.left);
	}

	//Assignment 3 says we need a "get(K key)" method but AVLTree has 
	//getValue(K key0). I included both just in case. They serve the same purpose.
	public V get(K key) {
		return getValue(key,root); 
	}
	
	/**
	 * Searches for a Key and returns the Value associated with that key
	 */
	@Override
	public V getValue(K key) {
		return getValue(key,root);
	}

	private V getValue(K key,Node<K,V> node) {
		if(node == null)
			return null;
		if(((Comparable<K>)node.key).compareTo((K)key) == 0)
			return node.value;
		if(((Comparable<K>)node.key).compareTo((K)key)<0)
			return getValue(key,node.right);
		return getValue(key,node.left);
	}

	/**
	 * returns the current size of the tree
	 */
	@Override
	public int size() {
		return currentSize;
	}

	/** 
	 * returns true is the list is empty
	 */
	@Override
	public boolean isEmpty() {
		if(root == null)
			return true;
		return false;
	}

	/**
	 * returns the height of the tree
	 */
	@Override
	public int height() {
		return height(root);
	}

	private int height(Node<K,V> node) {
		int leftHeight = 0, rightHeight = 0;
		if(root == null)
			return 0;
		if (node == null) {
			return -1; 
		}
		leftHeight = height(node.left) + 1;
		
		rightHeight = height(node.right) + 1;
		
		if (rightHeight > leftHeight)
			return rightHeight;
		
		return leftHeight;
	}

	/**
	 * Sorts the tree into a organized array and
	 * creates a new iterator that allows users to 
	 * sift through the data
	 */
	@Override
	public Iterator<K> iterator() {
		return new iteratorHelper<K>();
	}

	class iteratorHelper<K> implements Iterator<K>{
		K[] keys;
		int counter;
		int position;
		public iteratorHelper() {
			keys = (K[]) new Object[currentSize];
			position = 0;
			counter = 0;
			iterate(root);
		}

		public void iterate(Node node) {
			if(node == null)
				return;
			iterate(node.left);
			keys[counter++] = (K) node;
			iterate(node.right);
			return;
		}

		@Override
		public boolean hasNext() {
			return position<keys.length;
		}

		@Override
		public K next() {
			if(!hasNext())
				return null;
			return keys[position++];
		}

	}

	/**
	 * prints each the key and value of each element of the 
	 * tree in order and with periods "." to represent the height 
	 * of that node in the tree
	 */
	@Override
	public void print() {
		print(root,0);
	}

	private void print(Node<K,V> node, int height) {
		if(node.left != null) {
			height++;
			print(node.left, height);
			height--;
		}
		for(int i = 0; i < height; i++) {
			System.out.print(".");
		}
		System.out.print("Key: ");
		System.out.print(node.key);
		System.out.print(", Value: ");
		System.out.println(node.value);
		if(node.right != null) {
			height++;
			print(node.right,height);
			height--;
		}
		return;

	}

}
