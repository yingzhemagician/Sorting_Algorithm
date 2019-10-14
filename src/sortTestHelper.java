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

    public void testSort(int[] arr, ISortingAlgorithm algorithm, String opt){
        long startTime = System.currentTimeMillis();
        if(opt.equals("normal"))
            algorithm.sort(arr);
        else if(opt.equals("optimized"))
            algorithm.sort_opt(arr);
        long endTime = System.currentTimeMillis();

        printArray(arr);
        assert isSorted(arr) : "not sorted";

        System.out.println("time cost for algorithm " + algorithm.getClass() + " " + opt + " is : " + (endTime-startTime) + "ms");
    }

    public void printArray(int[] arr){
        for (int item : arr)
            System.out.print(item + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        sortTestHelper helper = new sortTestHelper();
        int[] arr_1 = helper.generateRandomArray(5000, 1, 100000);
//        int[] arr_1 = helper.generateNearlyOrderedArray(50000, 10);
        int[] arr_2 = helper.copyIntArray(arr_1);
        int[] arr_3 = helper.copyIntArray(arr_1);
        int[] arr_4 = helper.copyIntArray(arr_1);

        helper.testSort(arr_1, new SelectionSort(), "normal");
        helper.testSort(arr_2, new InsertionSort(), "optimized");
        helper.testSort(arr_3, new MergeSort(), "normal");
        helper.testSort(arr_4, new MergeSort(), "optimized");

    }
}
