   package asigments;

import java.util.concurrent.RecursiveAction;
/*
 * NAME:TSHIKOTSHI VHUTALI
 * STD NO:TSHVHU003
 * CSC2002S ASSIGMENT 1
 */

public class QuickSortParallel extends RecursiveAction{
	private int arr[];
	private int startP = 0;
	private int endP = 0;
	private final int threshold;
	
	public QuickSortParallel(int arr[],int start,int end,int threshold){
		this.arr = arr;
		this.startP = start;
		this.endP = end;
		this.threshold = threshold;
	}
	//override compute
	protected void compute(){sequentialQuick(arr,startP,endP);}
	public void sequentialQuick(int arr[],int start,int end){
		//chosen pivot(start)
		int pivotNum = arr[start];
		int left = start;//left pointer
		int right = end;//right pointer
		final int leftP = 1;
		final int rightP = -1;
		int sublist = rightP;//right sublist(below the pivot)
		
		while(left != right){
			if(sublist == rightP ){
				if(arr[right] < pivotNum){
					arr[left] = arr[right];
					left++;
					//Switch to the left sublist
					sublist = leftP;
				}
				else{
					right--;
				}
				
			}
			 
			else if(sublist == leftP ){
				if( arr[left] > pivotNum){
					arr[right] = arr[left];
					right--;
					//switch to the right sublist
					sublist = rightP;
				}
				else{
					left++;
				}
			}
		}
		arr[left] = pivotNum;//left pointer collision
		int diff = left  - start;
		int diff2 = left - start;
		int diff3 = left - 1;
		
		if(  (diff) > 1 ){
			if((diff2) > threshold ){
				//invokeAll(new QuickSortParallel(arr,start,left - 1,threshold));
				int mid = (start + (diff3))/2;
				QuickSortParallel leftThread = new QuickSortParallel(arr,start,mid,threshold);
				QuickSortParallel rightThread = new QuickSortParallel(arr,mid + 1,diff3,threshold);
				//invokeAll(leftThread,rightThread);
				leftThread.fork();
				rightThread.compute();
				leftThread.join();
			}
			else{
				sequentialQuick(arr,start,diff3);
			}
			
		}
		int diff4 = end - left;
		 
		
		if(  (diff4) > 1){
			if( (diff4) > threshold){
				int mid = (left + 1 + end)/2;
				//invokeAll(new QuickSortParallel(arr,left +1,end,threshold));
				QuickSortParallel leftThread = new QuickSortParallel(arr,left + 1,mid,threshold);
				QuickSortParallel rightThread = new QuickSortParallel(arr,mid + 1,end,threshold);
				//invokeAll(leftThread,rightThread);
				leftThread.fork();
				rightThread.compute();
				leftThread.join();
			}
			else{
				sequentialQuick(arr,left + 1,end);
			}
		}
		 
	}
	

}
