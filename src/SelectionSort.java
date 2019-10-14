public class SelectionSort extends SortingAlgorithm {
    @Override
    public void sort(int arr[], String optimizeType){
        selectionSort(arr);
    }

    private void selectionSort(int arr[]){
        for(int i = 0; i < arr.length; i++){
            //find the min item in [i, arr.length)
            int minIndex = i;
            for(int j = i + 1; j < arr.length; j++){
                if(arr[j] < arr[minIndex])
                    minIndex = j;
            }
            swap(arr, i, minIndex);
        }
    }
}
