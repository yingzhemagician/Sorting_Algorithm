public class MergeSort extends SortingAlgorithm{
    @Override
    public void sort(int[] arr, String optimizeType) {
        if(optimizeType.equals("reduceMerge")){
            mergeSort_reduceMerge(arr, 0, arr.length - 1);
        }else if(optimizeType.equals("iterative"))
            mergeSortBottomUp(arr);
        else
            mergeSort(arr, 0, arr.length - 1);
    }

    //sort arr[l...r]
    private void mergeSort(int[] arr, int l, int r){
        if(l >= r)
            return;

        int mid = l + (r - l)/2;
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    private void merge(int[] arr, int l, int mid, int r){
        int[] tempArr = new int[r - l + 1];
        for(int i = l; i <= r; i++)
            tempArr[i - l] = arr[i];

        int i = l, j = mid + 1;
        for(int k = l; k <= r; k++){
            if(i > mid){
                arr[k] = tempArr[j - l];
                j++;
            }
            else if(j > r){
                arr[k] = tempArr[i - l];
                i++;
            }
            else if(tempArr[i - l] < tempArr[j - l]){
                arr[k] = tempArr[i - l];
                i++;
            }
            else {
                arr[k] = tempArr[j - l];
                j++;
            }
        }
    }

    private void mergeSort_reduceMerge(int[] arr, int l, int r){
        //1st optimization
        if(r - l <= 15){
            InsertionSort insertionSort = new InsertionSort();
            insertionSort.sortPartOfArray(arr, l, r);
            return;
        }

        int mid = l + (r - l)/2;
        mergeSort_reduceMerge(arr, l, mid);
        mergeSort_reduceMerge(arr, mid + 1, r);
        //2nd optimization
        //if left part is smaller than right part, don't need to merge
        if(arr[mid] > arr[mid + 1])
            merge(arr, l, mid, r);
    }

    public void mergeSortBottomUp(int[] arr){
        for(int windowSize = 1; windowSize < arr.length; windowSize += windowSize){
            for(int i = 0; i + windowSize < arr.length; i += 2*windowSize){
                //merge arr[i...i+sz-1] and arr[i+sz...i+2*sz-1]
                merge(arr, i, i+windowSize-1, Math.min(i+2*windowSize-1, arr.length-1));
            }
        }
    }
}
