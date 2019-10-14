public class sortTestHelper {
    public int[] generateRandomArray(int n, int rangeL, int rangeR){
        assert rangeR >= rangeL;
        int[] arr = new int[n];

        for(int i = 0; i < arr.length; i++)
            arr[i] = (int)(Math.random() * (rangeR - rangeL + 1)) + rangeL;

        return arr;
    }

    public int[] generateNearlyOrderedArray(int n, int swapTimes){
        int[] arr = new int[n];

        for(int i = 0; i < arr.length; i++)
            arr[i] = i;

        for(int i = 0; i < swapTimes; i++){
            int posX = (int)(Math.random() * n);
            int posY = (int)(Math.random() * n);

            int temp = arr[posX];
            arr[posX] = arr[posY];
            arr[posY] = temp;
        }
        return arr;
    }

    public int[] copyIntArray(int[] arr){
        int[] res = new int[arr.length];
        for(int i = 0; i < arr.length; i++)
            res[i] = arr[i];
        return res;
    }

    public boolean isSorted(int arr[]){
        for(int i = 0; i < arr.length - 1; i++)
            if(arr[i] > arr[i+1])
                return false;

        return true;
    }

    public void testSort(int[] arr, ISortingAlgorithm algorithm, String optimizeType){
        int[] arr_copy = copyIntArray(arr);
        long startTime = System.currentTimeMillis();
        algorithm.sort(arr_copy, optimizeType);
        long endTime = System.currentTimeMillis();

//        printArray(arr);
        assert isSorted(arr_copy) : "not sorted";

        System.out.println("time cost for algorithm " + algorithm.getClass() + " " + optimizeType + " is : " + (endTime-startTime) + "ms");
    }

    public void printArray(int[] arr){
        for (int item : arr)
            System.out.print(item + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        sortTestHelper helper = new sortTestHelper();
        int[] arr = helper.generateRandomArray(100000, 1, 1000000);
//        int[] arr = helper.generateNearlyOrderedArray(50000, 10);

        helper.testSort(arr, new SelectionSort(), "noOptimize");

        helper.testSort(arr, new InsertionSort(), "noOptimize");
        helper.testSort(arr, new InsertionSort(), "reduceCopy");

        helper.testSort(arr, new MergeSort(), "noOptimize");
        helper.testSort(arr, new MergeSort(), "reduceMerge");
        helper.testSort(arr, new MergeSort(), "iterative");

        helper.testSort(arr, new QuickSort(), "noOptimize");
        helper.testSort(arr, new MergeSort(), "randomIdx");

    }
}
