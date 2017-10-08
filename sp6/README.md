Short Project 6
================

The problems set [click here](./sp6-pq-2017f.md)

Group Members
-------------

- Binhan Wang (bxw161330@utdallas.edu)
- Hanlin He (hxh160630@utdallas.edu)
- Zheng Gao (zxg170430@utdallas.edu)

Project Structure
-----------------

Deliverable structure is as follow:

    cs6301
    └── g16
        ├── BinaryHeap.java             # Binary Heap implementation for p5.
        ├── HuffmanCoding.java
        ├── Index.java
        ├── IndexedHeap.java
        ├── KMergeSort.java             # Solution for Problem 1.
        ├── KMerger.java                # Merger Helper class for K-way Merge.
        ├── KruskalMST.java
        ├── P5Driver.java               # Driver class for Problem 5.
        ├── PerfectPower.java
        ├── PrimMST.java
        ├── PrimeFactor.java
        └── README.md

Compile
-------

To compile, run the following commands:

    # Problem 1
    javac cs6301/g16/KMergeSort.java

    # Problem 2

    # Problem 3

    # Problem 4

    # Problem 5
    javac cs6301/g16/P5Driver.java
  


Test Running
------------
-   Problem 1

    Execute the command `java cs6301.g16.KMergeSort`.

    Follow the prumpt, specify the length of array to be generated and k value.

    Example:

        > java cs6301.g16.KMergeSort
        Random generate array, enter size (-1 for automated 5 times random testing): 1000000
        Enter k value for K-way merge sort: 13

        Generating random array and list with length 1000000...........
        K-way Sort with k = 13.
        List<Integer>:
        Time: 823 msec.
        Memory: 44 MB / 256 MB.
        Integer[]:
        Time: 675 msec.
        Memory: 65 MB / 256 MB.

        > java cs6301.g16.KMergeSort
        Random generate array, enter size (-1 for automated 5 times random testing): -1

        Generating random array and list with length 9583233............
        K-way Sort with k = 16.
        List<Integer>:
        Time: 8446 msec.
        Memory: 370 MB / 681 MB.
        Integer[]:
        Time: 8986 msec.
        Memory: 426 MB / 681 MB.

        Generating random array and list with length 7141678............
        K-way Sort with k = 4.
        List<Integer>:
        Time: 7565 msec.
        Memory: 785 MB / 1343 MB.
        Integer[]:
        Time: 6144 msec.
        Memory: 893 MB / 1343 MB.

        Generating random array and list with length 3081433............
        K-way Sort with k = 10.
        List<Integer>:
        Time: 2726 msec.
        Memory: 210 MB / 1343 MB.
        Integer[]:
        Time: 1909 msec.
        Memory: 253 MB / 1343 MB.

        Generating random array and list with length 5440423............
        K-way Sort with k = 8.
        List<Integer>:
        Time: 4811 msec.
        Memory: 641 MB / 1343 MB.
        Integer[]:
        Time: 3786 msec.
        Memory: 724 MB / 1343 MB.

        Generating random array and list with length 1619193............
        K-way Sort with k = 19.
        List<Integer>:
        Time: 1178 msec.
        Memory: 671 MB / 1343 MB.
        Integer[]:
        Time: 1074 msec.
        Memory: 528 MB / 1343 MB.

-   Problem 2


-   Problem 3


-   Problem 4

-   Problem 5

    Execute the command `java cs6301.g16.P5Driver`.

    Follow the prump, specify the length of binary heap to be generated.

    Example:

        > java cs6301.g16.P5Driver
        Enter length of array to be generated: 1000
        Generating random array within 1000.
        The array is The array is [450,984,997,276,6,915,23,433,591,409,...]

        Sorting the array in ascending order.
        Time: 3 msec.
        Memory: 2 MB / 256 MB.

        The array is The array is [0,1,3,4,4,5,6,9,10,11,...]

        Sorting the array in descending order again.
        Time: 1 msec.
        Memory: 3 MB / 256 MB.

        The array is The array is [999,998,997,996,994,993,990,989,987,985,...]

        > java cs6301.g16.P5Driver
        Enter length of array to be generated: 1000000
        Generating random array within 1000000.
        The array is The array is [358913,703852,330664,560970,526013,326989,601757,365320,399564,164915,...]

        Sorting the array in ascending order.
        Time: 866 msec.
        Memory: 22 MB / 256 MB.

        The array is The array is [0,0,0,0,2,2,3,3,3,5,...]

        Sorting the array in descending order again.
        Time: 596 msec.
        Memory: 23 MB / 256 MB.

        The array is The array is [999998,999996,999996,999995,999994,999993,999992,999992,999991,999989,...]




