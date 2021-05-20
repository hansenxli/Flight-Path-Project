/** 
Author: Hansen Li
Date: Nov 28, 2019
*/

import java.util.ArrayList;
import java.util.List;

public class StartRoot {
	
	String city;
	List<String> root;
	
	// default constructor of array list
	public StartRoot() {
		root = new ArrayList<String>();
	}
	
	public void setNode(String Node) {
		this.city = Node;
	}

	public void add(String city) {
		root.add(city);
	}

	public void delete(String city) {
		root.remove(city);
	}

	public String getCity() {
		return this.city;
	}

	public Boolean exists(String city) {
		if (root.contains(city))
			return true;
		else {
			return false;
		}
	}
}
