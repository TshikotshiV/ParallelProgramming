package asigments;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import org.junit.Test;

/*
 * NAME:TSHIKOTSHI VHUTALI
 * STD NO:TSHVHU003
 * CSC2002S ASSIGMENT 1
 */
//this class validates the merge sort algorithm
public class TestMergeSortParallel {

	@Test
	public void mergeSorttest() {
		int parallelTest [] = RandomNumGenerator.createRandomArray(8000000);
		int serialTest [] = new int[parallelTest.length];
		
		System.arraycopy(parallelTest,0,serialTest,0,serialTest.length);//copy numbers to be sorted using array.sort
		Arrays.sort(serialTest,0,serialTest.length);
		ParallelMergeSort sort = new ParallelMergeSort(parallelTest,0,parallelTest.length,2000000);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(sort);
		assertTrue(SortUtil.isArraySorted(serialTest));//check if the sequential sort worked
		assertTrue(SortUtil.isArraySorted(parallelTest));//check if parallel sort worked
		for(int i = 0;i<parallelTest.length;i++){
			/*
			 * @param1 = expected output from parallelSort
			 * @param2 = actual output from arraysort
			 * the method returns true if the expected is the same as the actual output
			 */
			assertEquals(parallelTest[i],serialTest[i]);//check if they contain the same elements
		}
	}

}
