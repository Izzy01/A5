import java.io.*;
import java.util.*;

/**
* Author: Ilnaz Daghighian
* 
* DiskSchedulingAlgorithms
* public class DiskSchedulingAlgorithms 
* The DiskSchedulingAlgorithms class implements the FCFS, SSTF, SCAN, C-SCAN, LOOK, C-LOOK disk scheduling algorithms. 
* The data file A5Data.txt is read which provides two sets of scheduling data and will output the total amount of 
* head movement required by each algorithm for each of the given sets of data. 
*/
public class DiskSchedulingAlgorithms {
	
	private int numberOfCylinders; 
	private int startingDiskPosition;
	private int[] inputOutputRequests; 
	private static final String INPUTFILENAME = "A5Data.txt";
	
	//constructor 
	public DiskSchedulingAlgorithms(int set) {
		readTextFile(set);
	}
	
	//getters
	public int getNumberOfCylinders() {
		return numberOfCylinders;
	}

	public int getStartingDiskPosition() {
		return startingDiskPosition;
	}

	public int[] getInputOutputRequests() {
		return inputOutputRequests;
	}


	public int FCFS() {
		int totalHeadMovement = Math.abs(inputOutputRequests[0] - startingDiskPosition);
		
		for (int i = 1; i < inputOutputRequests.length; i++) {
			totalHeadMovement += Math.abs(inputOutputRequests[i] - inputOutputRequests[i-1]);
		}
		return totalHeadMovement;
	}

	
	public int SSTF() {
		int totalHeadMovement = 0; 
		Integer currentHeadPosition = startingDiskPosition;
		List<Integer> listOfRequests = getSortedlistOfRequests();
		int indexOfCurrentHeadPosition = listOfRequests.indexOf(currentHeadPosition);
		
		for(int i = inputOutputRequests.length - 1 ; i > 0; i--) {
			
			if (indexOfCurrentHeadPosition > 0) {
				//seek time between current head position and the next element in sorted list  
				int distance1 = listOfRequests.get(indexOfCurrentHeadPosition + 1) - currentHeadPosition;
				//seek time between current head position and the element before it in sorted list
				int distance2 = currentHeadPosition - listOfRequests.get(indexOfCurrentHeadPosition-1);
				totalHeadMovement += Math.min(distance1, distance2);
			
				if (distance1 < distance2) {
					//set current head position to the next element in sorted list
					currentHeadPosition = listOfRequests.get(indexOfCurrentHeadPosition + 1);
					listOfRequests.remove(indexOfCurrentHeadPosition); //remove the current element 
					//update index of current head position
					indexOfCurrentHeadPosition = listOfRequests.indexOf(currentHeadPosition);
				}
				if (distance2 < distance1) {
					//set current head position to the element before it in sorted list
					currentHeadPosition = listOfRequests.get(indexOfCurrentHeadPosition - 1);
					listOfRequests.remove(indexOfCurrentHeadPosition);//remove the current element
					//update index of current head position
					indexOfCurrentHeadPosition = listOfRequests.indexOf(currentHeadPosition);
				}
			}
			if (indexOfCurrentHeadPosition == 0) {
				int distance0 = listOfRequests.get(indexOfCurrentHeadPosition + 1) - currentHeadPosition;
				totalHeadMovement += distance0;
				currentHeadPosition = listOfRequests.get(indexOfCurrentHeadPosition + 1);
				listOfRequests.remove(indexOfCurrentHeadPosition);
			}
		}
		return totalHeadMovement;
	}
	
	public int SCAN() {
		int totalHeadMovement = 0; 
		List<Integer> listOfRequests = getSortedlistOfRequests();
		int indexOfCurrentHeadPosition = listOfRequests.indexOf(startingDiskPosition);
		
		//move down the sorted list starting at the index of currentHeadPosition 
		for(int i = indexOfCurrentHeadPosition; i > 0; i--) {
			totalHeadMovement += listOfRequests.get(i) - listOfRequests.get(i-1);
		}
		
		//adding the value to the edge of disk (to 0) and the value to the beginning of the other end of disk
		totalHeadMovement += listOfRequests.get(0) + listOfRequests.get(indexOfCurrentHeadPosition + 1);
		
		//moving up the sorted list to last request
		for(int i = indexOfCurrentHeadPosition + 2; i < listOfRequests.size(); i++) {
			totalHeadMovement += listOfRequests.get(i) - listOfRequests.get(i-1);
		}
		return totalHeadMovement; 
	}
	
	public int CSCAN() {
		int totalHeadMovement = 0; 
		List<Integer> listOfRequests = getSortedlistOfRequests();
		//adding the edge values of cylinder to list
		listOfRequests.add(0);
		listOfRequests.add(numberOfCylinders - 1);
		Collections.sort(listOfRequests);
		int indexOfCurrentHeadPosition = listOfRequests.indexOf(startingDiskPosition);
		
		//moving up the sorted list to the upper edge of the cylinder/disk 
		for(int i = indexOfCurrentHeadPosition + 1; i < listOfRequests.size(); i++) {
			totalHeadMovement += listOfRequests.get(i) - listOfRequests.get(i-1);
		}
		
		//add/go back to the start of cylinder
		totalHeadMovement += (numberOfCylinders -1);
		
		//moving up the sorted list to the last request  
		for(int i = 1; i < indexOfCurrentHeadPosition ; i++) {
			totalHeadMovement += listOfRequests.get(i) - listOfRequests.get(i-1);
		}		
		return totalHeadMovement;	
	}
	
	public int LOOK() {
		int totalHeadMovement = 0; 
		List<Integer> listOfRequests = getSortedlistOfRequests();
		int indexOfCurrentHeadPosition = listOfRequests.indexOf(startingDiskPosition);
		
		//move down the sorted list starting at the index of currentHeadPosition 
		for(int i = indexOfCurrentHeadPosition; i > 0; i--) {
			totalHeadMovement += listOfRequests.get(i) - listOfRequests.get(i-1);
		}
		
		//do not go to the edge 0 instead turn around after servicing last request on the way down
		//and adding the movement from last request on the way down to first track on the way up
		totalHeadMovement += listOfRequests.get(indexOfCurrentHeadPosition + 1) -listOfRequests.get(0);
		
		//moving up the sorted list to last request
		for(int i = indexOfCurrentHeadPosition + 2; i < listOfRequests.size(); i++) {
			totalHeadMovement += listOfRequests.get(i) - listOfRequests.get(i-1);
		}
		return totalHeadMovement;
	}
	
	public int CLOOK() {
		int totalHeadMovement = 0; 
		List<Integer> listOfRequests = getSortedlistOfRequests();
		int indexOfCurrentHeadPosition = listOfRequests.indexOf(startingDiskPosition);
		
		//moving up the sorted list to the last request in this direction
		for(int i = indexOfCurrentHeadPosition + 1; i < listOfRequests.size(); i++) {
			totalHeadMovement += listOfRequests.get(i) - listOfRequests.get(i-1);
		}
		
		//going back to the start(first request from the beginning)
		totalHeadMovement += listOfRequests.get(listOfRequests.size() - 1) - listOfRequests.get(0);
		
		//moving up the sorted list to the last request  
		for(int i = 1; i < indexOfCurrentHeadPosition ; i++) {
			totalHeadMovement += listOfRequests.get(i) - listOfRequests.get(i-1);
		}
		
		return totalHeadMovement;
	}
	
	//////PRIVATE HELPER METHODS//////
	
	private void readTextFile(int set) {
		try {	
			File file = new File(INPUTFILENAME); 
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line; 
			
			//reads first 3 lines/data for first set 
			if (set == 1) {
				for (int i = 0; i < 3; i++) {
					line = bufferedReader.readLine();
					switch(i) {
					case 0: numberOfCylinders = Integer.parseInt(line); break;
					case 1: startingDiskPosition = Integer.parseInt(line); break;
					case 2: inputOutputRequests = Arrays.stream(line.split("\\s")).mapToInt(Integer::parseInt).toArray(); break;	
					}
				}
			}	
			//reads last 3 lines/data for second set
			if (set == 2) {
				for (int i = 0; i < 6; i++) {
					line = bufferedReader.readLine();
					switch(i) {
					case 0: break;
					case 1: break;
					case 2: break;
					case 3: numberOfCylinders = Integer.parseInt(line); break;
					case 4: startingDiskPosition = Integer.parseInt(line); break;
					case 5: inputOutputRequests = Arrays.stream(line.split("\\s")).mapToInt(Integer::parseInt).toArray(); break;
					}
				}
			}			
			bufferedReader.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<Integer> getSortedlistOfRequests() {
		List<Integer> listOfRequests = new ArrayList<Integer>();
		for(int value : inputOutputRequests) {
			listOfRequests.add(value);
		}
		listOfRequests.add(0, startingDiskPosition); //add starting disk position to listOfRequests
		Collections.sort(listOfRequests);//sort list 
		return listOfRequests;
	}
	
}//end class
