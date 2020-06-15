/**
 * 
 */
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import data_structures.Hash.HashElement;
import data_structures.LinkedList.IteratorHelper;
import data_structures.LinkedList.Node;

/**
 * The linked list for our hash will only implement the
 * methods in the HashListI interface, a reduced set of
 * methods compared to the linked list from Assignment 1.
 * 
 * @author
 *
 */
public class LinkedList<E> implements HashListI<E> {

	private Node<E> head;
	private Node<E> tail;
	private int currentSize;

	//Constructor
	public LinkedList() {
		head = null;
		tail = null;
		currentSize = 0;


	}

	/**
	 * Defines the Node with an arbitrary element and a "next" pointer
	 */
	class Node<E> { 
		E data;
		Node<E> next;

		public Node(E obj) {
			data = obj;
			next = null;
		}
	}

	/**
	 * Add an object to the list at this position
	 * @param obj - the thing to be added
	 */
	@Override
	public void add(E obj) {
		Node<E> newNode = new Node<E>(obj); 
		newNode.next = head;
		if(tail == null)
			tail = newNode;
		head = newNode;
		currentSize++;

	}

	/**
	 * Remove an object from the list
	 * @param obj The object to remove
	 * @return The object removed
	 */
	@Override
	public E remove(E obj) {
		Node<E> current = head, previous = null;
		while(current!= null) {
			if(((Comparable<E>)current.data).compareTo(obj) == 0){
				if(current == head) {
					if(head == null) 
						return null; 
					E tmp = head.data;
					if(head == tail)
						head = tail = null;
					else
						head = head.next;
					currentSize--;
					return tmp;
				}
				if(current == tail) {
					if(head==null)
						return null;
					if(head==tail)
						head = tail = null;

					Node<E> current1 = head;
					Node<E> previous1 = null;
					while(current1.next != null) {
						previous1 = current1;
						current1 = current1.next;
					}

					previous1.next = null;
					tail= previous1;
					currentSize--;

					return current1.data;
				}
				currentSize--;
				previous.next = current.next;
				return current.data;

			}
			previous = current;
			current = current.next;
		}
		return null;
	}

	/**
	 * Make the list empty
	 */
	@Override
	public void makeEmpty() {
		head = tail = null;
		currentSize = 0;

	}

	/**
	 * Is the list empty?
	 * @return true if the list is empty
	 */
	@Override
	public boolean isEmpty() {

		return (head == null);
	}

	/**
	 * The current number of elements in the list
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return currentSize;
	}

	/**
	 * Does the list contain this object
	 * @param obj The object to look for
	 * @return True if the list contains it.
	 */
	@Override
	public boolean contains(E obj) {
		Node<E> tmp = head;
		while(tmp.data != null) {
			if(((Comparable<E>)tmp.data).compareTo(obj)==0)
				return true;
			tmp=tmp.next;
		}

		return false;
	}
	/**
	 * Returns an Iterator of the values in the list, presented in
	 * the same order as the list.
	 */
	@Override
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}

	class IteratorHelper implements Iterator<E> {
		Node<E> index;

		public IteratorHelper() {
			index = head;
		}

		@Override
		public boolean hasNext() {
			return index != null;
		}

		@Override
		public E next() {
			if(!hasNext())
				throw new NoSuchElementException();
			E tmp=index.data;
			index = index.next;
			return tmp;
		}
	}



}
