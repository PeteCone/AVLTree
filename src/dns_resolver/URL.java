package dns_resolver;

/**
 * A URL Object is a representation of the URL that we have been giving. 
 * It knows how to compare URLs!
 *
 * Note: The templates for some methods have been provided, but you should consider which additional
 * methods to add to this class.
 * 
 * @author CS310
 *
 */
public class URL implements Comparable<URL> {

	String str;
	
	public URL(String S) {
		this.str = S;
	}
	
	@Override
	public int compareTo(URL obj) {
		return this.str.compareTo(obj.str);
	}
	
	public int hashCode() {
		return str.hashCode();
	}
	
	public boolean equals(Object obj) {
		return ((URL)obj).str.equals(this.str);
	}
	
	public String toString() {
		return str;
	}
	
}
