# Sorting_Algorithm
 Implementation of different sorting algorithm using Java

 ## O($N^2$) 级别的算法
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

**因为插入排序的`内层循环可提前中断`的这个特性，经典题目 *"排序一个基本有序的数组用哪种排序?"*的答案就是: `插入排序`，因为只要找到插入位置就停止当前循环，这是很快的。**

```java
int[] arr_1 = helper.generateNearlyOrderedArray(10000, 100);

/*
time cost for algorithm class SelectionSort normal is : 106ms
time cost for algorithm class InsertionSort normal is : 10ms
time cost for algorithm class InsertionSort optimized is : 8ms
*/
```

实际工作中很多时候也用插入排序，因为实际数据很可能已经基本有序。

