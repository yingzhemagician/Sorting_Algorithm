public class QuickSort extends SortingAlgorithm{
    @Override
    public void sort(int[] arr, String optimizeType) {
        if(optimizeType.equals("randomIdx"))
            quickSort_randomIdx(arr, 0, arr.length - 1);
        else
            quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int l, int r){
        if( l >= r)
            return;

        int p = partition(arr, l, r);
        quickSort(arr, l, p - 1);
        quickSort(arr, p + 1, r);
    }

    private int partition(int[] arr, int l, int r){
        int target = arr[l];

        //arr[l+1...j] < target;  arr[j+1...i) > target   and curIdx is i
        int j = l;
        for(int i = l + 1; i <= r; i++){
            if(arr[i] < target){
                swap(arr, ++j, i);
//                swap(arr, j + 1, i);
//                j++;
            }
        }
        swap(arr, l, j);
        return j;
    }

    private void quickSort_randomIdx(int[] arr, int l, int r){
        if(r - l <= 15){
            InsertionSort insertionSort = new InsertionSort();
            insertionSort.sortPartOfArray(arr, l, r);
            return;
        }

        int p = partition_randomIdx(arr, l, r);
        quickSort(arr, l, p - 1);
        quickSort(arr, p + 1, r);
    }

    private int partition_randomIdx(int[] arr, int l, int r){
        int randomIdx = (int)(Math.random() * (r - l + 1)) + l;
        swap(arr, l, randomIdx);
        int target = arr[l];

        //arr[l+1...j] < target;  arr[j+1...i) > target   and curIdx is i
        int j = l;
        for(int i = l + 1; i <= r; i++){
            if(arr[i] < target){
                swap(arr, ++j, i);
            }
        }
        swap(arr, l, j);
        return j;
    }
}
