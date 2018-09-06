/**
* Author: Ilnaz Daghighian
* 
* TestDriver
* public class TestDriver
* The TestDriver class tests all of the functions of the DiskSchedulingAlgorithms class  
*/
public class TestDriver {

	public static void main(String[] args) {

		DiskSchedulingAlgorithms set1 = new DiskSchedulingAlgorithms(1);
		
		System.out.println("SET 1:\n" );

		System.out.println("For FCSC, the total head movement was " + set1.FCFS() + " cylinders.");
		System.out.println("For SSTF, the total head movement was " + set1.SSTF() + " cylinders.");
		System.out.println("For SCAN, the total head movement was " + set1.SCAN() + " cylinders.");
		System.out.println("For C-SCAN, the total head movement was " + set1.CSCAN() + " cylinders.");
		System.out.println("For LOOK, the total head movement was " + set1.LOOK() + " cylinders.");
		System.out.println("For C-LOOK, the total head movement was " + set1.CLOOK() + " cylinders.");
		
		System.out.println("\nSET 2:\n");
		
		DiskSchedulingAlgorithms set2 = new DiskSchedulingAlgorithms(2);
		
		System.out.println("For FCSC, the total head movement was " + set2.FCFS() + " cylinders.");	
		System.out.println("For SSTF, the total head movement was " + set2.SSTF() + " cylinders.");
		System.out.println("For SCAN, the total head movement was " + set2.SCAN() + " cylinders.");
		System.out.println("For C-SCAN, the total head movement was " + set2.CSCAN() + " cylinders.");
		System.out.println("For LOOK, the total head movement was " + set2.LOOK() + " cylinders.");
		System.out.println("For C-LOOK, the total head movement was " + set2.CLOOK() + " cylinders.");
		
	}//end main
}//end class
