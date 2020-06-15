package time_data_structures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.HashMap;
import java.util.TreeMap;

import data_structures.AVLTree;
import data_structures.Hash;
import data_structures.LinkedList;
import dns_resolver.IPAddress;
import dns_resolver.URL;

/**
 * TimeHashAVLTree was created to time the AVLTree and Hash table that I 
 * created. The main method created a hash table, AVLTree, a java imported
 * hash map, and java imported tree map. Each takes the same data from the
 * file top-250k.ip and times how long it takes to store and search for 
 * that data. The results are printed at the end in nanoseconds.
 * @author Peter Conant
 *
 */
public class TimeHashAVLTree {
	public static void main(String args[]) throws IOException {
		//creating linked list of keys and iterator for searching data structures
		LinkedList<IPAddress> IPList = new LinkedList<IPAddress>();
		BufferedReader reader = new BufferedReader(new FileReader("data/top-250k.ip"));
		String line;
		while((line = reader.readLine())!= null) {
			line = reader.readLine();
			String[] L = line.split("\t");
			IPAddress ip = new IPAddress(L[1]);
			IPList.add(ip);
		}
		Iterator<IPAddress> iter = IPList.iterator();
		
		//timing the author made hash table
		
		Hash<IPAddress,URL> IPHash = new Hash<IPAddress,URL>(16);
		
		reader = new BufferedReader(new FileReader("data/top-250k.ip"));
		
		long start = System.nanoTime();//start timer
		while((line = reader.readLine())!= null) {
			line = reader.readLine();
			String[] L = line.split("\t");
			URL ur = new URL(L[0]);
			IPAddress ip = new IPAddress(L[1]);
			IPHash.add(ip,ur);
		}
		long end = System.nanoTime();//end timer
		long HashStore = (end - start);
		int tableSize = IPHash.tableSize();
		
		start = System.nanoTime();
		
		do {
			IPHash.getValue(iter.next());
		}while(iter.hasNext());
		
		end = System.nanoTime();
		long HashSearch = (end - start);
		IPHash = null;
		
		//timing the author made AVLTree
		AVLTree<IPAddress,URL> IPTree = new AVLTree<IPAddress,URL>();
		
		start = System.nanoTime();
		while((line = reader.readLine())!= null) {
			line = reader.readLine();
			String[] L = line.split("\t");
			URL ur = new URL(L[0]);
			IPAddress ip = new IPAddress(L[1]);
			IPTree.add(ip,ur);
		}
		end = System.nanoTime();
		long TreeStore = (end - start);
		
		iter = IPList.iterator();
		
		start = System.nanoTime();
		do {
			IPTree.getValue(iter.next());
		}while(iter.hasNext());
		end = System.nanoTime();
		long TreeSearch = (end - start);
		
		IPTree = null;
		
		
		//Timing the Java imported Hash table
		HashMap<IPAddress, URL> hashMap = new HashMap<IPAddress, URL>();
		
		start = System.nanoTime();
		while((line = reader.readLine())!= null) {
			line = reader.readLine();
			String[] L = line.split("\t");
			URL ur = new URL(L[0]);
			IPAddress ip = new IPAddress(L[1]);
			hashMap.put(ip,ur);
		}
		
		end = System.nanoTime();
		long hashMapStore = (end - start);
		
		iter = IPList.iterator();
		
		start = System.nanoTime();
		do {
			hashMap.containsKey(iter.next());
			
		}while(iter.hasNext());
		end = System.nanoTime();
		long hashMapSearch = (end - start);
		
		hashMap = null;
		
		//timing Java imported TreeMap
		TreeMap<IPAddress, URL> treeMap = new TreeMap<IPAddress, URL>();
		
		start = System.nanoTime();
		while((line = reader.readLine())!= null) {
			line = reader.readLine();
			String[] L = line.split("\t");
			URL ur = new URL(L[0]);
			IPAddress ip = new IPAddress(L[1]);
			treeMap.put(ip,ur);
		}
		
		end = System.nanoTime();
		long treeMapStore = (end - start);
		
		iter = IPList.iterator();
		
		start = System.nanoTime();
		do {
			treeMap.containsKey(iter.next());
			
		}while(iter.hasNext());
		end = System.nanoTime();
		long treeMapSearch = (end - start);
		
		treeMap = null;
		
		System.out.println("HashStore: " + HashStore);
		System.out.println("HashSearch: " +HashSearch);
		System.out.println("TreeStore: " + TreeStore);
		System.out.println("TreeSearch: " + TreeSearch);
		System.out.println("hashMapStore: " + hashMapStore);
		System.out.println("hashMapSearch: " + hashMapSearch);
		System.out.println("treeMapStore: " + treeMapStore);
		System.out.println("treeMapSearch: " + treeMapSearch);
		
		
	}
}
