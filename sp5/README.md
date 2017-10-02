Short Project 5
================

The problems set [click here](./sp5-quicksort-2017f.md)

Group Members
-------------

- Binhan Wang (bxw161330@utdallas.edu)
- Hanlin He (hxh160630@utdallas.edu)
- Zheng Gao (zxg170430@utdallas.edu)

Project Structure
-----------------

Deliverable structure is as follow:

    cs6301
    ├── README.md
    └── g16
        ├── ArrayHelper.java                    # Operations of Array
        ├── DualPivotQuickSort.java             # Solution for Problem 2
        ├── QuickSort.java                      # Solution for Problem 1
        ├── QuickSortVsMergeSort.java           # Solution for Problem 4
        └── SelectAlgorithm.java                # Solution for Problem 3


Compile
-------

To compile, run the following commands:

    # Problem 1
    javac cs6301/g16/QuickSort.java

    # Problem 2
    javac cs6301/g16/DualPivotQuickSort.java

    # Problem 3
    javac cs6301/g16/SelectAlgorithm.java

    # Problem 4
    javac cs6301/g16/QuickSortVsMergeSort.java

  


Test Running
------------
-   Problem 1

    Execute the command `java cs6301.g16.QuickSort`.

    The program implement two versions of partition of quick sort, and test their running time and memory
    with randomly and descending order.
    
    Example:

        > java cs6301.g16.QuickSort 
        Test 1 - Shuffled Input Array:
        [2333320 7442905 2258551 9919783 3654082 ... 2928539 1905658 1431271 293565 249048 1015919]
        ==========================
        
        Quick Sort with Partition Algorithm 1:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Time: 2281 msec.
        Memory: 89 MB / 116 MB.
        
        ------------------------
        
        Quick Sort with Partition Algorithm 2:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Time: 2896 msec.
        Memory: 104 MB / 113 MB.
        ==========================
        Test 2 - Reversed Input Array:
        [10000000 9999999 9999998 9999997 9999996 ... 6 5 4 3 2 1]
        ==========================
        
        Quick Sort with Partition Algorithm 1:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Time: 1162 msec.
        Memory: 177 MB / 197 MB.
        
        ------------------------
        
        Quick Sort with Partition Algorithm 2:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Time: 1428 msec.
        Memory: 184 MB / 249 MB.
        
-   Problem 2

    Execute the command `java cs6301.g16.DualPivotQuickSort`.

    The program implement dual pivot partition and its version of quick sort,and compare
    its performance with regular quick sort. Test two version of quick sort with 
    distinct or many duplicates array.
    
    Example:

        > java cs6301.g16.DualPivotQuickSort 
        ====================
        Test with array with duplicate elements:
        
        Input Array:
        [2435340 459723 2380941 4622172 3693769 ... 3499475 4625506 2707785 4831311 1017604 3807044]
        Dual Pivot Quick Sort:
        [1 1 2 2 2 ... 5003894 5003894 5003895 5003896 5003897 5003898]
        Sorting Success
        Time: 2355 msec.
        Memory: 121 MB / 155 MB.
        
        Normal Quick Sort:
        [1 1 2 2 2 ... 5003894 5003894 5003895 5003896 5003897 5003898]
        Sorting Success
        Time: 1904 msec.
        Memory: 117 MB / 152 MB.

        ====================
        Test with array with distinct elements:
        
        Input Array:
        [2259773 5945188 9847958 277804 8689917 ... 8585715 4525157 5105388 1013554 8532081 9951518]
        Dual Pivot Quick Sort:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Sorting Success
        Time: 2391 msec.
        Memory: 241 MB / 264 MB.
        
        Normal Quick Sort:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Sorting Success
        Time: 1841 msec.
        Memory: 253 MB / 263 MB.
        
        

-   Problem 3

    Execute the command `java cs6301.g16.SelectAlgorithm`.

    The program implement 3 versions of the Select algorithm (finding k largest elements) by creating prority queue and recursion technique. Then we
    test with an arbitrary length integer array and compare their performance.
    
    Example:

        > java cs6301.g16.SelectAlgorithm 
        Test 1:
        array size=30000000
        k=22222
        Output:
        test (a):
        Kth element is: 29977778     Running time : 19730ms
        test (b):
        Kth element is: 29977778     Running time : 5184ms
        test (c):
        Kth element is: 29977778     Running time : 90ms
        ------------------------------
        Test 2:
        array size=7000000
        k=66
        Output:
        test (a):
        Kth element is: 6999934     Running time : 2460ms
        test (b):
        Kth element is: 6999934     Running time : 1023ms
        test (c):
        Kth element is: 6999934     Running time : 30ms

        

-   Problem 4

    Execute the command `java cs6301.g16.QuickSortVsMergeSort`.

    The program implement best version of Merge sort implemented before and quick sort that uses dual pivot partition. Compare
    their performance with distinct or many duplicates array.
    
    Example:

        > java cs6301.g16.QuickSortVsMergeSort 
        ====================
        Test with array with distinct elements:
        
        Input Array:
        [7666843 1846369 9478937 8450219 2361577 ... 2457128 4755480 6664826 2617945 8315679 9752214]
        Dual Pivot Quick Sort:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Sorting Success
        Time: 2235 msec.
        Memory: 140 MB / 157 MB.
        
        Merge Sort:
        [1 2 3 4 5 ... 9999995 9999996 9999997 9999998 9999999 10000000]
        Sorting Success
        Time: 1874 msec.
        Memory: 179 MB / 195 MB.
               
        ====================
        Test with array with duplicate elements:
        
        Input Array:
        [886771 1538677 2583747 1862295 1612139 ... 3843858 1029651 1249519 3610601 266402 4661479]
        Dual Pivot Quick Sort:
        [1 1 1 2 3 ... 4999511 4999511 4999511 4999511 4999511 4999512]
        Sorting Success
        Time: 2127 msec.
        Memory: 282 MB / 308 MB.
        
        Merge Sort:
        [1 1 1 2 3 ... 4999511 4999511 4999511 4999511 4999511 4999512]
        Sorting Success
        Time: 1871 msec.
        Memory: 321 MB / 347 MB.
