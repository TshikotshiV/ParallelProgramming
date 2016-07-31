package asigments;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class DriverSort {
	static long startTime = 0;
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	private static float toc(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	 
	
	public static void main(String args[]){
		
		final int max = 4000000;
		int randomNumArray[] = new int[max];
		final int numOfCores = Runtime.getRuntime().availableProcessors();//get the numbers of processors
		final int threshold = randomNumArray.length/numOfCores;
		System.out.println("Sequential cutoff "+ threshold);
		
		RandomNumGenerator rand = new RandomNumGenerator();
		randomNumArray = RandomNumGenerator.createRandomArray(max);
		
		 
	
		QuickSortParallel sot = new QuickSortParallel(randomNumArray, 20,2000000,threshold);
		ParallelMergeSort sort = new ParallelMergeSort(randomNumArray, 20,2000000 , threshold);
		ForkJoinPool pool = new ForkJoinPool();
		
		
		System.gc() ;//incase if the garbage collector runs
		tick();
		pool.invoke(sot);
		float time1 = toc();
		System.out.println("Run took "+time1*1000+" milliseconds for QuickSort.");
		
		tick();
		//sort.compute();
		pool.invoke(sort);
		float time = toc();
		System.out.println("Run took "+time*1000+" milliseconds for MergeSort.");
		for(int i =0;i<randomNumArray.length;i++){
			//System.out.print(randomNumArray[i]+" ");
		}
		boolean checkSort = RandomNumGenerator.isArraySorted(randomNumArray,20,10000);
		if(checkSort == true){
			System.out.println("The Array is sorted");
		}
		else{
			System.out.println("The Array is not sorted");
		}
	}
}
