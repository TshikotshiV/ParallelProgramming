package asigments;

import java.util.Arrays;

import java.util.concurrent.RecursiveAction;

/*
 * NAME:TSHIKOTSHI VHUTALI
 * STD NO:TSHVHU003
 * CSC2002S ASSIGMENT 1
 */
public class QuickSortParallel extends RecursiveAction {
	private static final long serialVersionUID = 1L;
	private int start;
	private int end;
	private int threshold;
	private int[] arr;
	
    //constructor,@param:Array,start index,end index,threshold
	public QuickSortParallel(int[] arr, int start, int end, int threshold) {this.arr = arr;this.start = start;this.end = end;this.threshold = threshold;}
	@Override
	protected void compute() {quickSort(arr,start,end,threshold);}
	//@param:array,start index,end index
	public static void quickSort(int[] arr, int start, int end,int threshold) {
		//if number of elements is less than sequential cutoff,sort sequentially
		if (end - start < threshold) {
			// Sequential sort
			Arrays.sort(arr, start, end);
		} else {
			// Sequential partition
			int mid = getSublist(arr, start, end);
			
			//Sort partitions recursively in parallel i.e create two subtasks,fork them and then join them
			QuickSortParallel left = new QuickSortParallel(arr, start, mid,threshold);
			QuickSortParallel right = new QuickSortParallel(arr, mid+1, end,threshold);
			//fork left,compute right then wait
			invokeAll(left,right);
		}
	}
	public static int getSublist(int[] arr, int start, int end) {
		int tmp;

		int pivotIndex = start + (end-start)/2;
		int pivot = arr[pivotIndex];
		
		// Swap pivot with last element in range
		tmp = arr[pivotIndex];
		arr[pivotIndex] = arr[end-1];
		arr[end-1] = tmp;
		
		// Starting from both ends of range, build up partitions
		int i = start, j = end-2;
		while (i <= j) {
			if (arr[i] < pivot) {
				i++; // increase size of left partition
			} else if (arr[j] >= pivot) {
				j--; // increase size of right partition
			} else {
				// swap elements and increase sizes of both partitions
				tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
				i++;
				j++;
			}
		}
		// i now refers to the first element of the right partition,
		// so put the pivot element there to get it in the right place
		tmp = arr[i];
		arr[i] = arr[end-1];
		arr[end-1] = tmp;
		
		// pivot is now at index i
		return i;
	}
}
