public class InsertionSort extends SortingAlgorithm {
    @Override
    public void sort(int[] arr, String optimizeType) {
        if(optimizeType.equals("reduceCopy"))
            insertionSort_reduceCopy(arr);
        else
            insertionSort(arr);
    }

    public void insertionSort(int[] arr){
        for(int i = 1; i < arr.length; i++){
            //find the right position of arr[i] in [0, i]
            for(int j = i; j > 0 && arr[j] < arr[j - 1]; j--)
                swap(arr, j, j - 1);
//            for(int j = i ; j >= 1; j--){
//                if(arr[j] < arr[j - 1])
//                    swap(arr, j, j - 1);
//                else
//                    break;
//            }
        }
    }

    public void insertionSort_reduceCopy(int[] arr) {
        for(int i = 1; i < arr.length; i++){
            //find the right position of arr[i] in [0, i]
            int targetItem = arr[i];
            int j;
            for(j = i; j > 0 && arr[j - 1] > targetItem; j--)
                arr[j] = arr[j - 1];
            arr[j] = targetItem;
        }
    }

    public void sortPartOfArray(int[] arr, int l, int r){
        for(int i = l + 1; i <= r; i++){
            int targetItem = arr[i];
            int j;
            for(j = i; j > l && arr[j - 1] > targetItem; j--)
                arr[j] = arr[j - 1];
            arr[j] = targetItem;
        }
    }
}
