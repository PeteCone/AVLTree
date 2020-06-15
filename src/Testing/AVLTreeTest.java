/**
 * 
 */
package Testing;
import data_structures.AVLTree;

/**
 * @author Peter
 *
 */
public class AVLTreeTest {
	public static void main(String args[]) {
		AVLTree<Integer, Integer> tree = new AVLTree<Integer, Integer>();
		tree.add(3,3);
		tree.add(7,7);
		tree.add(6,6);
		tree.add(10,10);
		tree.add(12,12);
		tree.add(4,4);
		tree.add(9,9);
		tree.add(6,6);
		
		tree.print();
		System.out.print(tree.contains(9));
		
	}

}
