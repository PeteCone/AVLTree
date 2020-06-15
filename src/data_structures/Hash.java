package data_structures;

import java.util.Iterator;



/**
 * The Hash data structure has O(1) time complexity (best case) for add, remove, and find
 * for an object in the data structure. The methods in the Hash data structure are defined
 * by the HashI interface. The Hash consists of an array of Linked Lists,
 * the Linked Lists are defined by the HashListI interface.
 * 
 * @author
 *
 * @param <K> The key for entries in the hash
 * @param <V> The value for entries in the hash
 */

public class Hash<K,V> implements HashI<K, V> {

	LinkedList<HashElement<K,V>>[]harray;
	int tableSize;
	int numElements;
	double maxLoadFactor;

	/**
	 *  The Hash constructor accepts a single parameter that
	 *  sets the initial size of the Dictionary.
	 *  
	 *  @param tableSize The table size desired for this hash
	 */
	public Hash(int tableSize) {
		this.tableSize = tableSize;
		harray = (LinkedList<HashElement<K,V>>[]) new LinkedList[tableSize];
		for(int i = 0; i < tableSize; i++)
			harray[i] = new LinkedList<HashElement<K,V>>();
		maxLoadFactor = 0.75;
		numElements = 0;
	}

	class HashElement <K,V> implements Comparable<HashElement<K,V>>{
		K key;
		V value;

		public HashElement(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public int compareTo(HashElement<K,V> o) {
			return((Comparable<K>)o.key).compareTo(this.key);
		}
	}



	@Override
	public void resize(int newSize) {

		LinkedList<HashElement<K,V>>[] tmparray = (LinkedList<HashElement<K,V>>[]) new LinkedList[newSize];
		for(int i = 0; i < newSize; i++)
			tmparray[i] = new LinkedList<HashElement<K,V>>();
		for(K key: this) {
			V value = getValue(key);
			HashElement<K,V> newhe = new HashElement<K,V>(key,value);
			int hashval = key.hashCode();
			hashval = hashval & 0x7FFFFFFF;
			hashval = hashval % newSize;
			tmparray[hashval].add(newhe);
		}

		harray = tmparray;
		tableSize = newSize;
	}



	/**  
	 * Adds the given key/value pair to the dictionary. Returns 
	 * false if the dictionary is full, or if the key is a duplicate. 
	 * Returns true if addition succeeded. 
	 *  
	 * @param key the key to add
	 * @param value the value associated with the key
	 * @return true if the key/value are added to the hash.
	 */
	@Override
	public boolean add(K key, V value) {
		if(loadFactor() > maxLoadFactor)
			resize(tableSize*2);
		HashElement<K,V> he = new HashElement(key,value);
		int hashval = key.hashCode();
		hashval = hashval & 0x7FFFFFFF;
		hashval = hashval % tableSize;
		harray[hashval].add(he);
		numElements++;
		return true;
	}

	/**
	 * Deletes the key/value pair identified by the key parameter. 
	 * Returns true if the key/value pair was found and removed, 
	 * otherwise returns false.
	 *  
	 * @param key the key for the element that is to be removed
	 * @return true
	 */

	@Override
	public boolean remove(K key) { 
		if(contains(key)) {
			int hashval = key.hashCode() & 0x7FFFFFFF % tableSize;
			V value = getValue(key);
			HashElement<K,V> he = new HashElement(key,value);
			harray[hashval].remove(he);
			numElements--;
			return true;
		}
		return false;
	}

	/**
	 * Change the value associated with an existing key. Will
	 * return false if no key is found
	 *
	 * @param key The key to change
	 * @param value The new value to assign to the key
	 * @return true if successful, false if no key is found
	 */
	@Override
	public boolean changeValue(K key, V value) {
		if(contains(key)) {
			int hashval = key.hashCode() & 0x7FFFFFFF % tableSize;
			HashElement<K,V> he = new HashElement(key,value);
			harray[hashval].remove(he);
			harray[hashval].add(he);
			return true;
		}
		return false;
	}

	/**
	 * Test whether the hash has the entry associated with the key.
	 * Returns true if the key was found.
	 *
	 * @param key the key to look for
	 * @return whether it is there.
	 */
	@Override
	public boolean contains(K key) {
		int hashval = key.hashCode() & 0x7FFFFFFF % tableSize;
		for(HashElement<K,V> he :harray[hashval])
			if(((Comparable<K>)key).compareTo(he.key) == 0)
				return true;
		return false;
	}

	/**
	 * Returns the value associated with the parameter key. 
	 * Returns null if the key is not found or the dictionary is empty. 
	 *
	 * @param key the key to find the value for
	 * @return the value
	 */
	@Override
	public V getValue(K key) {
		int hashval = key.hashCode() & 0x7FFFFFFF % tableSize;
		for(HashElement<K,V> he :harray[hashval])
			if(((Comparable<K>)key).compareTo(he.key) == 0)
				return he.value;
		return null;
	}

	/**
	 * Returns the number of key/value pairs currently stored in the dictionary 
	 * @return Number of Elements
	 */
	@Override
	public int size() {
		return numElements;
	}

	/**
	 * Returns true if Dictionary is empty
	 * @return true if empty
	 */
	@Override
	public boolean isEmpty() {
		return numElements == 0;

	}

	/**
	 * Makes the Dictionary empty
	 */
	@Override
	public void makeEmpty() {
		for(LinkedList<HashElement<K,V>> he :harray)
			he.makeEmpty();
		numElements = 0;
	}

	/**
	 * returns the current loadFactor
	 */
	@Override
	public double loadFactor() {
		return 1.0 * numElements/tableSize;
	}

	/**
	 * returns the maximum load factor
	 */
	@Override
	public double getMaxLoadFactor() {
		return maxLoadFactor;
	}

	/**
	 * sets the maximum load factor
	 * @param double new max load factor
	 */
	@Override
	public void setMaxLoadFactor(double loadfactor) {
		maxLoadFactor = loadfactor;
		return;

	}

	/**
	 * Returns an Iterator of the keys in the dictionary, in ascending 
	 * sorted order 
	 *
	 * @return an instance of an Iterator<K> inner class
	 */
	@Override
	public Iterator<K> iterator() {
		return new KeyIteratorHelper<K>();
	}

	class KeyIteratorHelper<T> implements Iterator<T>{
		T[] keys;
		int position;
		public KeyIteratorHelper() {
			keys = (T[]) new Object[numElements];
			int counter = 0;
			for( int i = 0; i < tableSize; i++) {
				LinkedList<HashElement<K,V>> list = harray[i];
				for(HashElement<K,V> he : list)
					keys[counter++] = (T)he.key;
			}
			position = 0;
		}
		public boolean hasNext() {
			return position<keys.length;
		}

		public T next() {
			if (!hasNext())
				return null;
			return keys[position++];
		}
	}
	
	public int tableSize() {
		return tableSize;
	}


}




