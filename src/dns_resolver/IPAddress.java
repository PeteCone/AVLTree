package dns_resolver;

/**
 * The IPAddress is using iIPv4 and has dotted-decimal notation, with the network, two subnets, 
 * and host separated by periods. For example, the IP address 130.191.226.146 has 
 * a network of 130, a subnet of 191, the second subnet is 226, and the host address is 146.
 * 
 * Your IPAddress class should accept a string of dotted-decimal IPAddresses in the constructor
 * and separate them into the components. 
 *
 * Note: The templates for some methods have been provided, but you should consider which additional
 * methods to add to this class.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/IP_address#IPv4_addresses">Wikipedia IPv4 addresses</a>
 * @author CS310
 *
 */

public class IPAddress implements Comparable<IPAddress> {

	String address;
	int network;
	int subnet;
	int subnet2;
	int host;

	/**
	 * The constructor for the IPAddress class
	 * 
	 * @param ip the dotted-decimal IP address
	 */
	public IPAddress(String ip) {
		address = ip;
		String data[] = ip.split("\\.");
		int[] ipAd = new int[4];
		for(int i : ipAd) {
			ipAd[i] = Integer.parseInt(data[i]);
		}
		
		network = ipAd[0];
		subnet = ipAd[1];
		subnet2 = ipAd[2];
		host = ipAd[3];
		
	}


	@Override
	public int hashCode() {
		return network + subnet + subnet2 + host;
	}

	@Override
	public boolean equals(Object obj) {
		return ((IPAddress)obj).address.equals(this.address);
	}

	@Override
	public String toString() {
		return address;
	}

	@Override
	public int compareTo(IPAddress ip) {
		return this.address.compareTo(ip.address);
	}

}
