/** 
Author: Hansen Li
Date: Nov 28, 2019
*/

import java.util.Comparator;

class Location implements Comparator<Location> {
	
	public String cityName;
	public int cityCost;

	// default constructor
	public Location() {
		
	}
	
	// constructor with values
	public Location(String city, int cost) {
		cityName = city;
		cityCost = cost;
	}

	// compares two cost values of the location nodes
	public int compare(Location node1, Location node2) {
		
		if (node1.cityCost < node2.cityCost)
			return -1;
		else if (node1.cityCost > node2.cityCost)
			return 1;
		else;
			return 0;
	}


}
