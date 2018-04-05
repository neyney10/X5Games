import java.util.Arrays;

public class Utilities {
	
	public static void main(String[] args)
	{
		performPerformanceTest(1000, 5000, 0, 10000);
	}

	//Integer Binary Search
	/**
	 * The function perform Binary Search on array of Integers with Upper Bound time complexity of O(Log[N])
	 * @param [Type: int[] ] "arr" -  Array to Search in.
	 * @param [Type: int ] "item" - value to search.
	 * @return [Type: int ] the requested value if found, -1 otherwise.
	 */
	public static int BinarySearch(int[] arr,int item)
	{
		return BinarySearchHelper(arr,item,0,arr.length-1);
	}
	private static int BinarySearchHelper(int[] arr,int item, int low,int high)
	{
		if(low<=high)
		{
			int mid = (low+high)/2;
			if(arr[mid] == item)
				return arr[mid];
			
			if(low==high) //if on last iteration and not found. then return not found.
				return -1;
			
			//else if higher, then search the right side >>
			if(item > arr[mid])
				return BinarySearchHelper(arr,item,low=mid+1,high);
			
			//else then must be on the left side <<
				return BinarySearchHelper(arr,item,low,high=mid-1);
		}
		
		return -1;
	}

	//Integer Linear Search
		/**
	 * The function perform Linear Search on array of Integers with Upper Bound time complexity of O(N)
	 * @param [Type: int[] ] "arr" -  Array to Search in.
	 * @param [Type: int ] "item" - value to search.
	 * @return [Type: int ] the requested value if found, -1 otherwise.
	 */
	public static int LinearSearch(int[] arr, int item)
	{
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i] == item)
				return arr[i];
		}
		return -1;
	}
	
	//Integer MergeSort
		/**
	 * The function perform MergeSort on array of Integers with Upper Bound time complexity of O(N*Log[N])
	 * (Note: this sort requires N extra memory space, Probably Faster than QuickSort when used on array of Objects)
	 * @param [Type: int[] ] "arr" -  Array to sort.
	 */
	public static void MergeSort(int[] arr)
	{
		MergeSortHelper(arr, 0, arr.length-1);
	}
	private static void MergeSortHelper(int[] arr,int start,int end)
	{
		if(start == end) //if len is is 1
			return; //stop
		
		int mid = (start+end)/2;
		MergeSortHelper(arr,start,mid); //split
		MergeSortHelper(arr,mid+1,end); //split
		
		//now Merge parts
		Merge(arr,start,mid,end);
	}
	private static void Merge(int[] arr,int start1,int mid, int end2)
	{
		int end1 = mid;
		int start2 = mid+1;
		int startindex = start1; //temp
		
		int size = end2-start1+1;
		int[] temp = new int[size];
		int index = 0;
		
		while(start1 <= end1 && start2 <= end2)
		{
			if(arr[start1] <= arr[start2])
				temp[index++] = arr[start1++];
			else
				temp[index++] = arr[start2++];
		}
		
		while(start1 <= end1)
			temp[index++] = arr[start1++];
		
		while(start2 <= end2)
			temp[index++] = arr[start2++];
		
		
		for(int i = 0; i <size;i++)
			arr[startindex++] = temp[i];
		
	}

	//Integer QuickSort
	 //Lomuto Version
	 	/**
	 * The function perform QuickSort on array of Integers using "Lomuto" partition scheme, with Upper Bound time complexity of O(N*Log[N])
	 * @param [Type: int[] ] "arr" -  Array to Sort.
	 */
	public static void QuickSortLomuto(int[] arr)
	{
		QuickSortHelperLomuto(arr, 0, arr.length-1);
	}
	private static void QuickSortHelperLomuto(int[] arr,int start,int end)
	{
		//Floor | Stopping condition | Recursion's base.
		if(start>=end)
			return;
		
		//save the position for the recursive function later on.
		int startTemp = start;
		
		//Choose a pivot, choose the first element.
		int pivotPos = start;
		
		//run pivot's delegate, find the starting position to start searching for correct place to place the pivot.
		while(start<end && arr[pivotPos]>=arr[start+1])
		{start++;}

		//find Pivot's place and arrange the elements according to pivot's size.
		for(int i = start+1;i<=end;i++)
		{
			if(arr[pivotPos]>=arr[i])
				Swap(arr,++start,i);
		}
		
		//Place pivot in it's place.
		Swap(arr,pivotPos,start); 
		pivotPos = start;
		
		QuickSortHelperLomuto(arr,startTemp,pivotPos-1); //Recurse: sort the left side of the array.
		QuickSortHelperLomuto(arr,pivotPos+1,end); //Recurse: sort the right side of the array.
		
	}
	 //Hoare Version
	 	 	/**
	 * The function perform QuickSort on array of Integers using "Hoare" partition scheme, with Upper Bound time complexity of O(N*Log[N])
	 * @param [Type: int[] ] "arr" -  Array to Sort.
	 */
	public static void QuickSortHoare(int[] arr)
	{
		QuickSortHelperHoare(arr, 0, arr.length-1);
	}
	private static void QuickSortHelperHoare(int[] arr,int start,int end)
	{ // I didnt succesfuly write it.
		if(start>=end)
			return;

		int pivot = arr[start];
		
		int i=start-1, j=end+1;
		while(i<j)
		{
			do
			{
				i++;
			}
			while(pivot > arr[i]);
				
			do
			{
				j--;
			}
			while(pivot < arr[j]);
				

			if(i>=j)
				break;

			Swap(arr, i, j);
				
		}

		QuickSortHelperHoare(arr, start, j);
		QuickSortHelperHoare(arr, j+1, end);
	}


	//Other
		 	/**
	 * The function returns Random array of integers with given values.
	 * @param [Type: int ] "size" -  the size of the array to build.
	 * @param [Type: int ] "minValue" -  the minimal value of stored element in the array,
	 * @param [Type: int ] "maxValue" -  the maximal  value of stored element in the array.
	 * @return [Type: int[] ] the newly created array with custom size, min and max values.
	 */
	public static int[] createRandomIntegerArray(int size, int minValue, int maxValue)
	{
		int[] array = new int[size];
		for(int i=0;i<array.length;i++)
			array[i] = minValue + (int) (Math.random()*(maxValue+1));
		
		return array;
	}
			 	/**
	 * The function performes different types of utlillities with randomized arrays and given test scenarios such as amount of tests and size of arrays.
	 * [MergeSort] [QuickSort (Lomuto)] [QuickSort (Hoare)] [Java's Built-in sort using Dual Pivot QuickSort] [Binary Search] [Linear Search]
	 * @param [Type: int ] "times" - the amount of times to run each test.
	 * @param [Type: int ] "size" -   the size of the array to build.
	 * @param [Type: int ] "minValue" -  the minimal  value of stored element in the array.
	 * @param [Type: int ] "maxValue" -  the maximal  value of stored element in the array.
	 */
	public static void performPerformanceTest(int times,int size,int minValue,int maxValue)
	{
		long starttime,totaltime,avgtime,mintime,maxtime;
		int[] arr;
		int randomElemenet;
		
		mintime = Integer.MAX_VALUE;
		maxtime = 0;
		avgtime = 0;
		for(int i=0;i<times;i++)
		{
			arr = createRandomIntegerArray(size, minValue, maxValue);
			starttime = System.nanoTime();
			MergeSort(arr);
			totaltime = System.nanoTime()-starttime;
			avgtime += totaltime;
			mintime = (totaltime<mintime)? totaltime : mintime;
			maxtime = (totaltime>maxtime)? totaltime : maxtime;
		}
		System.out.println("Average time for MergeSort: "+(avgtime/times) + " | Max: "+maxtime+" | Min: "+mintime);
		
		mintime = Integer.MAX_VALUE;
		maxtime = 0;
		avgtime = 0;
		for(int i=0;i<times;i++)
		{
			arr = createRandomIntegerArray(size, minValue, maxValue);
			starttime = System.nanoTime();
			QuickSortLomuto(arr);
			totaltime = System.nanoTime()-starttime;
			avgtime += totaltime;
			mintime = (totaltime<mintime)? totaltime : mintime;
			maxtime = (totaltime>maxtime)? totaltime : maxtime;
		}
		System.out.println("Average time for QuickSort (Lomuto): "+(avgtime/times) + " | Max: "+maxtime+" | Min: "+mintime);
		
		mintime = Integer.MAX_VALUE;
		maxtime = 0;
		avgtime = 0;
		for(int i=0;i<times;i++)
		{
			arr = createRandomIntegerArray(size, minValue, maxValue);
			starttime = System.nanoTime();
			QuickSortHoare(arr);
			totaltime = System.nanoTime()-starttime;
			avgtime += totaltime;
			mintime = (totaltime<mintime)? totaltime : mintime;
			maxtime = (totaltime>maxtime)? totaltime : maxtime;
		}
		System.out.println("Average time for QuickSort (Hoare): "+(avgtime/times) + " | Max: "+maxtime+" | Min: "+mintime);
		
		mintime = Integer.MAX_VALUE;
		maxtime = 0;
		avgtime = 0;
		for(int i=0;i<times;i++)
		{
			arr = createRandomIntegerArray(size, minValue, maxValue);
			starttime = System.nanoTime();
			Arrays.sort(arr);
			totaltime = System.nanoTime()-starttime;
			avgtime += totaltime;
			mintime = (totaltime<mintime)? totaltime : mintime;
			maxtime = (totaltime>maxtime)? totaltime : maxtime;
		}
		System.out.println("Average time for Built-in java's sort: "+(avgtime/times) + " | Max: "+maxtime+" | Min: "+mintime);
		

		arr = Utilities.createRandomIntegerArray(size, minValue, maxValue);
		QuickSortLomuto(arr);
		
		mintime = Integer.MAX_VALUE;
		maxtime = 0;
		avgtime = 0;
		for(int i=0;i<times;i++)
		{
			randomElemenet = arr[(int)(Math.random()*arr.length)];
			starttime = System.nanoTime();
			BinarySearch(arr, randomElemenet);
			totaltime = System.nanoTime()-starttime;
			avgtime += totaltime;
			mintime = (totaltime<mintime)? totaltime : mintime;
			maxtime = (totaltime>maxtime)? totaltime : maxtime;
		}
		System.out.println("Average time for BinarySearch: "+(avgtime/times) + " | Max: "+maxtime+" | Min: "+mintime);
		
		mintime = Integer.MAX_VALUE;
		maxtime = 0;
		avgtime = 0;
		for(int i=0;i<times;i++)
		{
			randomElemenet = arr[(int)(Math.random()*arr.length)];
			starttime = System.nanoTime();
			LinearSearch(arr, randomElemenet);
			totaltime = System.nanoTime()-starttime;
			avgtime += totaltime;
			mintime = (totaltime<mintime)? totaltime : mintime;
			maxtime = (totaltime>maxtime)? totaltime : maxtime;
		}
		System.out.println("Average time for LinearSearch: "+(avgtime/times) + " | Max: "+maxtime+" | Min: "+mintime);
		
	}	
	private static void Swap(int[] arr,int index1,int index2)
	{
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}
}
