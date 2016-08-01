package asigments;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.*;
/*
 * NAME:TSHIKOTSHI VHUTALI
 * STD NO:TSHVHU003
 * CSC2002S ASSIGMENT 1
 */

public class DriverSort {
	static long startTime = 0;
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	private static float toc(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	 
	public static void main(String args[]){
		final int lo = 20;
		final int hi = 2000000;
		//final int max = 4000000;
		Scanner kb = new Scanner(System.in);
		//System.out.print("Enter sort: merge or quick");
		//String sortType = kb.nextLine();
		/*System.out.print("Enter array 'min'  and 'max' and increment");
		int min = kb.nextInt();
		int max = kb.nextInt();
		int incre = kb.nextInt();*/
		int min = 3000000;
		int max =  10000000;
		int incre = 100000;
		String sortType = "merge";
		
		final int numOfCores = Runtime.getRuntime().availableProcessors();//get the numbers of processors
		int threshold = 0;
		int randomNumArray[] = null;
		int newRandomArray [] = null;
		try {
			final FileWriter output = new FileWriter(sortType+".txt");
		
		//icrement by the given value
			while(min <= max){
				 
				randomNumArray= new int[min];
				threshold = randomNumArray.length/numOfCores;//get the sequential cuttoff;
				RandomNumGenerator rand = new RandomNumGenerator();
				randomNumArray = RandomNumGenerator.createRandomArray(min);//generate random numbers(Integers)
				int numOfThreads =  randomNumArray.length/threshold;//get the number of threads
				newRandomArray = new int[min];
				
				System.arraycopy(randomNumArray, lo, newRandomArray,0,hi - lo);//copy the appropriate range to be sorted using arraysort
				
				System.gc() ;//incase if the garbage collector runs
				tick();
				Arrays.sort(newRandomArray);//sort the array and measure time
				float t = toc();
				
				/*
				 * Quicksort object
				 * Parallelsort object
				 * fork join pool
				 */
				QuickSortParallel sot = new QuickSortParallel(randomNumArray, lo,hi,threshold);
				ParallelMergeSort sort = new ParallelMergeSort(randomNumArray, lo,hi, threshold);
				ForkJoinPool pool = new ForkJoinPool();
				
				
				float time = 0;
				//invoke either merge of quicksort
				if (sortType.equalsIgnoreCase("merge")){
					System.gc() ;//incase if the garbage collector runs
					tick();
					pool.invoke(sort);
					time = toc();
				}
				else{
					if(sortType.equalsIgnoreCase("quick")){
						System.gc() ;//incase if the garbage collector runs
						tick();
						pool.invoke(sot);
						time = toc();
					}
				}
				 
				//System.out.println(min + " Num threads  "+numOfThreads+ " time  "+time*1000 +" "+t*1000+"  milliseconds for "+sortType+ " Speedup "+(t*1000)/(time*1000));
				output.write(min +"  "+ numOfThreads+"  "+time*1000 +"  "+"  " +(t*1000)/(time*1000) + "\n");//write the output to a file
				 
				min = incre + min;
				
			}
			output.close();//close the file after writing
		}catch(IOException ioExcep){
			System.out.println("Problems writing to a file");
		}
		System.out.println("Sequential cutoff "+ threshold);
		System.out.println("min "+ min);
	    
		//verify if the array is sorted
		boolean checkSort = RandomNumGenerator.isArraySorted(randomNumArray,lo,hi);
		if(checkSort == true){
			System.out.println("The Array is sorted");
		}
		else{
			System.out.println("The Array is not sorted");
		}
	}
}
