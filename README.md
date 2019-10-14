# Sorting_Algorithm
 Implementation of different sorting algorithm using Java

 ## O(N^2​) 级别的算法
 * 基础
 * 编码简单，易于实现，是一些简单情景的首选
 * 在一些特殊情况下，简单的排序算法更有效
 * 简单的排序算法思想衍生出复杂的排序算法
    * 比如`希尔排序`就是从`插入排序`衍生来的
 * 作为`子过程`来改进更复杂的排序算法

 ### Selection Sort
 每次循环以`排序部分`的后一个位置为基准i，选出后面`未排序部分`的最小值，然后交换i处的值和找到的最小值
 ```java
public void sort(int arr[]){
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
 ```



### Insertion Sort

#### 未优化版

每次循环拿出`未排序`部分的第一个，插入到`已排序`部分的相应位置

```java
public void sort(int[] arr){
  	for(int i = 1; i < arr.length; i++){
      	//find the right position of arr[i] in [0, i]
      	for(int j = i; j > 0 && arr[j] < arr[j - 1]; j--)
          	swap(arr, j, j - 1);
    }
}
```

理论上内层循环可能在中途就break掉，因为已经找到了准确的位置，因此`插入排序`应该比`选择排序`性能好一些，但是在C++的实现中，发现`插入排序`反而慢，这是因为`swap()`耗时，`选择排序`内层循环只调用一次`swap()`，但`插入排序`却调用多次。（但是java的实现中发现不优化已经比`选择排序`快了）

#### 优化版

先把待插入的元素复制一份，然后去找它的相应位置，如果暂时没找到位置，就把arr[j-1]位置的值先后移，其实就是省去了`swap()`中的一次赋值，java版的优化结果确实快了一些

```java
public void sort_opt(int[] arr) {
  	for(int i = 1; i < arr.length; i++){
      	//find the right position of arr[i] in [0, i]
      	int targetItem = arr[i];
     		int j;
      	for(j = i; j > 0 && arr[j - 1] > targetItem; j--)
          	arr[j] = arr[j - 1];
     		arr[j] = targetItem;
    }
}
```

** 因为插入排序的`内层循环可提前中断`的这个特性，经典题目 *"排序一个基本有序的数组用哪种排序?"*的答案就是: `插入排序`，因为只要找到插入位置就停止当前循环，这是很快的。**

```java
int[] arr_1 = helper.generateNearlyOrderedArray(10000, 100);

/*
time cost for algorithm class SelectionSort normal is : 106ms
time cost for algorithm class InsertionSort normal is : 10ms
time cost for algorithm class InsertionSort optimized is : 8ms
*/
```

实际工作中很多时候也用插入排序，因为实际数据很可能已经基本有序。



## O(N*logN) 级别的算法
|               | N^2           | N*logN        | faster
| ------------- | ------------- | ------------- | -------------
| N = 10        | 100           | 33            | 3
| N = 100       | 10000         | 664           | 15
| N = 1000      | 10^6          | 9966          | 100
| N = 10000     | 10^8          | 132877        | 753
| N = 100000    | 10^10         | 1660964       | 6020

### Merge Sort
提高了时间，但是用了额外的空间，因为归并过程中开辟了一个新的临时数组

#### 递归（自顶向下） 未优化版

merge的过程中，新建一个tempArr然后将[l, r]区间的元素复制过去，然后arr内原位置就填上排好序的结果

```java
public void sort(int[] arr){
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
```



#### 递归 （自顶向下）优化版

**有两点优化**

1. 两个mergeSort结束 后，如果左半部分已经完全小于右半部分，就不用再merge了。
2. 当[l...r]区间少于15个元素时，大概率已经是基本有序的，此时借用插入排序

```java
public void sort_opt(int[] arr) {
  	mergeSort_opt(arr, 0, arr.length - 1);
}

private void mergeSort_opt(int[] arr, int l, int r){
  	//1st optimization
  	if(r - l <= 15){
    	InsertionSort insertionSort = new InsertionSort();
    	insertionSort.sortPartOfArray(arr, l, r);
    	return;
  	}

  	int mid = l + (r - l)/2;
  	mergeSort_opt(arr, l, mid);
  	mergeSort_opt(arr, mid + 1, r);
  	//2nd optimization
  	//if left part is smaller than right part, don't need to merge
  	if(arr[mid] > arr[mid + 1])
    	merge(arr, l, mid, r);
}
```



#### 迭代 （自底向上）未优化版

相当于每次按照一个`windowSize`来切分数组，也就是

* 第一次按照windowSize=1来切分，然后每两个window执行merge；

* 第二次按照windowSize=2来切分，然后每两个window执行merge；

* 第三次按照windowSize=4来切分，然后每两个window执行merge；

  ....

* 按照windowSize = 2 * windowSize来切分



再每一次切分里，每个窗口大小是windowSize，将连续的两个窗口merge。因此，i是第一个窗口的起始，i每次循环要增加（跳过）2个windowSize。将`arr[i...i+sz-1]` 和 `arr[i+sz...i+2*sz-1]`合并，但对于末尾的两个数组合并时有问题，因此在内层循环中，i的有效条件是`i + windowSize < arr.length;`也就是至少要保证左半部分存在，右半部分可能不够或者不存在，这种情况，只要把右半部分的末尾设为arr.length就行了，也就是`Math.min(i+2*windowSize-1, arr.length-1)`

```java
public void mergeSortBottomUp(int[] arr){
		for(int windowSize = 1; windowSize < arr.length; windowSize += windowSize){
				for(int i = 0; i + windowSize < arr.length; i += 2*windowSize){
						//merge arr[i...i+sz-1] and arr[i+sz...i+2*sz-1]
						merge(arr, i, i+windowSize-1, Math.min(i+2*windowSize-1, arr.length-1));
        }
    }
}
```

