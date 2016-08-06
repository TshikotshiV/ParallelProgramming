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
	
	public static void main (String args[]){
		//testMode 0 =merge sort,testMode 1 = quick sort
		int mode = 0;// Integer.parseInt(args[0]);
		final int threshold = 9000;
		int lo = 0;//start value,can be changed
		int min = 1000000;//Integer.parseInt(args[1]);//arrayMin
		int incre = 10000;//Integer.parseInt(args[3])//arrayIncrement
		int max = 2000000;//Integer.parseInt(args[2]);//arrayMax
		float timeP = 0;//time it takes to sort using parallel quick/merge sort
		float timeS = 0;//time it takes to sort using array.sort
		int numThreads = 0;//optimal number of threads
		String sortType = null;//to assigned depending on test mode
		
		try{
			final FileWriter output = new FileWriter(sortType+".txt");//args[4].trim()+".txt");
			for(int size = min;size<max;size+=incre){
				float speedup = 0;
				float bestSpeedup = 0;
				for(int j = 0;j<=5;j++){//for each size loop(sort) 5 times then increment the size of the array
					int arrayTest[] = RandomNumGenerator.createRandomArray(size);
					int newArray[] = Arrays.copyOfRange(arrayTest,0,arrayTest.length);
					int hi = arrayTest.length;//end value...can be changed
					numThreads = arrayTest.length/threshold;
					
					ParallelMergeSort sort1 = new ParallelMergeSort(arrayTest,lo,hi,threshold);
					QuickSortParallel sort2 = new QuickSortParallel (arrayTest,lo,hi,threshold);
					ForkJoinPool pool = new ForkJoinPool();
					
					System.gc();//get rid of the garbage that might run before measuring time
					tick();
					Arrays.sort(newArray,lo,hi);
					timeS = toc()*1000;//convert to milliseconds
					
					if (mode == 0){
						System.gc();//get rid of the garbage that might run before measuring time
						tick();
						pool.invoke(sort1);
						timeP = toc() * 1000;
						sortType = "MergeSort";
					}
					else{
						System.gc();//get rid of the garbage that might run before measuring time
						tick();
						pool.invoke(sort2);
						timeP = toc()*1000;
						sortType = "QuickSort";
					}
					speedup = timeS/timeP;
					if(speedup<bestSpeedup | bestSpeedup == 0){//get the best speedup
						bestSpeedup = speedup;//update the speed to get the best
					}
					//COMMENT TWO LINES BELOW TO VERIFY IF IT IS TAKING THE BEST OUT OF FIVE SPEEDUPS
					//System.out.println(size+" "+numThreads+" "+sortType+" "+bestSpeedup + " timeS/p " +timeS+" "+timeP+" speed " + speedup);
					//System.out.println();
				}	
				output.write(size+" "+numThreads+" "+sortType+" "+bestSpeedup+" " +timeP + "\n");
				System.out.println(size+" "+numThreads+" "+sortType+" "+bestSpeedup + " "+timeP + " "+timeS);
			}
			output.close();//close the file immediately after writing to it
		}
		catch(IOException e){
			System.out.println("Problems writing to a file");
		}
	}
	static long startTime = 0;
	private static void tick(){startTime = System.currentTimeMillis();}
	private static float toc(){return (System.currentTimeMillis() - startTime) / 1000.0f; }
}
