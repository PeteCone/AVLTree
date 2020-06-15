package data_structures;

import java.util.Iterator;

/**
 * AVL Tree.  A data structure that maintains a balanced 
 * Balanced Search Tree where the difference in height is
 * never greater than one.
 *
 * Note that this interface only specifies the public
 * methods. You will need to add some private methods
 * to balance your tree!
 */

public interface AVLTreeI<K, V> extends Iterable<K> {
	
	
	/**
	 * The method to add to the AVLTree.  It will not allow duplicate additions.
	 * @param key the key to add
	 * @param value the value associated with the key
	 */
	public void add(K key, V value);

	/**
	 * Tests whether the AVLTree contains the key
	 * @param key the key to look for
	 * @return whether the key is found
	 */
	public boolean contains(K key); 

	/**
	 * Get the value associated with a given key
	 * @param key the key to get the value for
	 * @return the current value
	 */
	public V getValue(K key);

	/**
	 * Returns the number of elements in the AVLTree
	 * @return the number of elements in the tree
	 */
	public int size();

	/**
	 * Test whether the AVLTree is empty
	 * @return <code>true</code> if the tree is empty
	 * 		   <code>false</code> if the tree is not empty 
	 */
	public boolean isEmpty();

	/**
	 * The height of the tree. Recall that a tree with 
	 * only a root node has height 0. You will also need
	 * to have a private method that is not included here
	 * that overloads the height() method.
	 * @return the height of the tree at the root node
	 */
	public int height();
		
	/**
	 * An iterator for all the keys in the AVLTree. This will
	 * iterate over the keys using <b>InOrder Traversal</b>
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<K> iterator();

	
	/**
	 * Recursively print the tree. This method should print the
	 * entire tree using <em>Inorder Traversal</em> to the standard
	 * output (i.e. using System.out.println or System.out.print).
	 * You can print the tree one node per line, and use periods to
	 * note the hierarchy of the tree.
	 */
	public void print();
}
