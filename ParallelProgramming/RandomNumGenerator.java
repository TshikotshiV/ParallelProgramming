package asigments;

import java.util.Random;

public class RandomNumGenerator {
	public static int[] createRandomArray(int sizeOfArray) {
		Random randomNumbers = new Random(123L);
		int[] num = new int[sizeOfArray];
		for (int i = 0; i < num.length; i++) {
			num[i] = randomNumbers.nextInt();
		}
		return num;
	}

	//check if the array is sorted
	public static boolean isArraySorted(int[] arr,int lo,int hi){
		int minV = Integer.MIN_VALUE;
		/*for (int value : data) {
			if (value < last) {
				return false;
			}
			last = value;
		}
		return true;*/
		for(int i =lo;i<hi;i++){
			if(arr[i] < minV){
				return false;
			}
			minV = arr[i];
		}
		return true;
	}

}
