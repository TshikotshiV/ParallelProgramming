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

		int pivIndex = start + (end-start)/2;
		int pivotNumber = arr[pivIndex];
		
		// Swap pivot with last element in range
		tempVar = arr[pivIndex];
		arr[pivIndex] = arr[end-1];
		arr[end-1] = tempVar;
		

		//Building up the partitions,staring from the end of the list(both)
		int x = start;
		int y = end - 2;
		while (x <= y) {
			if (arr[x] < pivotNumber) {
				//increasing the left sublist size
				x++;
			} else if (arr[y] >= pivotNumber) {
				//increase size of the righ sublist
				y--;  
			} else {
				 //increase the size of both sublists and swap
				tempVar = arr[x];
				arr[x] = arr[y];
				arr[j] = tempVar;
				x++;
				y++;
			}
		}
		//X will be the first element of the right sublist and the pivot must be put there(right place)
		tempVar = arr[x];
		arr[x] = arr[end-1];
		arr[end-1] = tempVar;
		//new pivot position
		return x;
	}
}
