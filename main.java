/** 
Author: Hansen Li
Date: Nov 28, 2019
*/
import java.io.*;
import java.util.*;

public class main {

	public static void main(String[] arg) throws FileNotFoundException {
		
		// declare variables
		String flightData;
		String flightPlan;
		String currLine;
		String currNode;
		PrintWriter out = new PrintWriter("FlightResult.txt");


		BufferedReader flightDataBR, flightPlanBR;
		List<String> locationList;
		String timeCost[][], pathArray[][];
		
		// get user input for file names
		System.out.println("Have both the flight data and flight plan files in the program directory.");
		System.out.println("Please enter the flight data filename:");
		Scanner flightDataFile = new Scanner(System.in);
		flightData = flightDataFile.nextLine();
		
		System.out.println("Please enter the flight plan filename:");
		Scanner flightPlanFile = new Scanner(System.in);
		flightPlan = flightPlanFile.nextLine();

		
		try {

			// read through files
			flightDataBR = new BufferedReader(new FileReader(flightData));
			flightPlanBR = new BufferedReader(new FileReader(flightPlan));

			// generate arrays with information
			locationList = new ArrayList<String>();
			timeCost = new String[Integer.parseInt(flightDataBR.readLine())][4];
			pathArray = new String[Integer.parseInt(flightPlanBR.readLine())][3];

			int i = 0;
			int j = 0;
			
			// iterate until end of file
			while ((currLine = flightDataBR.readLine()) != null) {
				
				// set j counter = 0
				j = 0;
				
				// tokenizes string using pipe to separate
				StringTokenizer data = new StringTokenizer(currLine, "|");
				
				int k = 1;
				
				// while loop for while k is less than or equal to 2
				while (k <= 2) {
					
					currNode = data.nextToken();
					if (locationList.contains(currNode) == false) {
						
						timeCost[i][j] = currNode;
						j++;
						locationList.add(currNode);
						
					} 
					else {
						timeCost[i][j] = currNode;
						j++;
					}
					k++;
				}

				while (data.hasMoreTokens()) {
					timeCost[i][j] = data.nextToken();
					j++;
				}
				
				i++;
			}
			
			i = 0;

			// tokenizes flight plan data file
			while ((currLine = flightPlanBR.readLine()) != null) {
				
				j = 0;
				
				StringTokenizer data = new StringTokenizer(currLine, "|");
				
				while (data.hasMoreTokens()) {
					
					pathArray[i][j] = data.nextToken();
					j++;
				}
				
				i++;
			}

			i = 1;

			// for-each loop for pathArrays in array
			for (String wantedFlightPlan[] : pathArray) {
				
				if (!(locationList.contains(wantedFlightPlan[0]) && locationList.contains(wantedFlightPlan[1]))) {
					out.println("Unfortunately the path could not be found.");
					continue;
				}
				
				String costType1 = null;
				String costType2 = null;

				
				// reads file for type to use
				if (wantedFlightPlan[2].equals("T")) {
					costType1 = "Time";
					costType2 = "Cost";
				}
				else if (wantedFlightPlan[2].equals("C")) {
					costType1 = "Cost";
					costType2 = "Time";
				}

				DijkstraAlgorithm priorityQueue = new DijkstraAlgorithm(locationList);
				priorityQueue.dijkastra(timeCost, wantedFlightPlan);

				out.println("Flight " + i + ": " + priorityQueue.SVertex + ", " + priorityQueue.DVertex + " (" + costType1 + ")");
				
				// for-each loop 
				for (String vertex : locationList) {
					
					if (vertex.equals(priorityQueue.DVertex) == false) {
						continue;
					}
					
					// creates list from the shortest path in priority queue
					List<String> list = priorityQueue.PathofTheRoot(priorityQueue.ListOfVisitedNode, priorityQueue.DVertex);
					
					
					// prints out list of cities
					for (int k = 0; k < list.size(); k++) {
						
						if (k == list.size() - 1) {
							out.print(list.get(k) + ". ");
						}	
						else {	
							out.print(list.get(k) + " -> ");
						}
					}
					
					// prints out cost portion of line
					out.println(costType1 + ": " + priorityQueue.minimalDistance.get(vertex) + " " + costType2 + ": "
							+ priorityQueue.distOfVertices.get(vertex));
					break;
				}
				
				i++;
				
			}

		// catch block	
		} catch (Exception e) {
			System.out.println("Error has occurred with the input files.");
		}
		
		// close file for out
		out.close();
		System.out.println("The output file has been generated and written.");
	}
}
