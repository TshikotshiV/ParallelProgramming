package asigments;

import java.util.Arrays;

import java.util.concurrent.RecursiveAction;

/*
 * NAME:TSHIKOTSHI VHUTALI
 * STD NO:TSHVHU003
 * CSC2002S ASSIGMENT 1
 */


public class ParallelMergeSort extends RecursiveAction {
	private int[] arr;
    private int start, end;
    private int threshold;

    //Parameterized constructor
    public ParallelMergeSort(int[] arr, int start, int end, int threshold) {
            this.arr = arr;
            this.start = start;
            this.end = end;
            this.threshold = threshold;
    }

    @Override
    //override compute
    protected void compute() {
            if (end - start <= threshold) {
                    // sequential sort
                    Arrays.sort(arr, start, end);
                    return;
            }

            // Sort halves in parallel
            int mid = start + (end-start) / 2;
          invokeAll(//execute two or more tasks in parallel
                   new ParallelMergeSort(arr, start, mid, threshold),
                   new ParallelMergeSort(arr, mid, end, threshold) );
            
          /*  ParallelMergeSort left  =   new ParallelMergeSort(arr, start, mid, threshold);
            ParallelMergeSort right =  new ParallelMergeSort(arr, mid, end, threshold) ;
            left.fork();
            right.compute();
            left.join();*/
            // sequential merge,i.e.merge the sorted halves into a whole
            sequentialMerge(mid);
    }

    private void sequentialMerge(int mid) {
         //checking if the array is sorted,if so,then skip the merging process
    	int index1 = mid;
    	int index2 = mid - 1;
    	int sizeNew = end - start;//size of the new array
    	int[] newArray = new int[sizeNew];
    	if (arr[index2] < arr[index1]) {
            return; 
        }
        /*
         * Copy array from arr to newArray
         * @param1=source array
         * @param2=source position
         * @param3=destination array
         * @param4=start index(dest array)
         * @param5=end index(dest array)
         * 
         */
        System.arraycopy(arr, start, newArray, 0, newArray.length);
        
        int newArrayStart = 0;
        int newArrayEnd = end - start;
        int newArrayMid = mid - start;

        for (int i = start, x = newArrayStart, y = newArrayMid; i < end; i++) {
            if (y >= newArrayEnd || (x < newArrayMid && newArray[x] < newArray[y]) ) {
                arr[i] = newArray[x++];//post increment
            } else {
                arr[i] = newArray[y++];//post increment
            }
        }
   
    }

}
