Short Project 2
================

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
        ├── ArrayQueue.java                 # Solution for Problem 5
        ├── ReversibleSinglyLinkedList.java # Solution for Problem 4
        ├── SinglyLinkedList.java           # Sample code with some modification.
        ├── SortableList.java               # Solution for Problem 2
        └── SortedSetList.java              # Solution for Problem 1

Compile
-------

To compile, run the following commands:

```bash
# Problem 1
javac cs6301/g16/SortedSetList.java

# Problem 2
javac cs6301/g16/SortableList.java

# Problem 4
javac cs6301/g16/ReversibleSinglyLinkedList.java

# Problem 5
javac cs6301/g16/ArrayQueue.java
```

There is no dependency with file from package `cs6301.g00`.

Test Running
------------

- Problem 1

    Execute the command `java cs6301.g16.SortedSetList [size:default 40]`.

    The program would generate two Sorted Set List from range from 0 to `size`
    with steps 2 and 3, and compute print the intersect/union/difference in
    both direction. Apparently, result of _intersect_ and _union_ should be
    reflexive, and _difference_ would have different result.

    Example:

    ```
    > java cs6301.g16.SortedSetList 20
    List 1: [0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20]
    List 2: [0, 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39]

    Intersection(List1, List2): [0, 6, 12, 18]
    Intersection(List2, List1): [0, 6, 12, 18]

    Union(List1, List2):: [0, 2, 3, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 24, 27, 30, 33, 36, 39]
    Union(List2, List1):: [0, 2, 3, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 24, 27, 30, 33, 36, 39]

    Difference(List1, List2): [2, 4, 8, 10, 14, 16]
    Difference(List2, List1): [3, 9, 15, 24, 27, 30, 33, 36, 39]
    ```

- Problem 4

    Execute the command `java cs6301.g16.ReversibleSinglyLinkedList
    [size:default 40]`.

    The program would generate a list of `range(size)`, first reverse the list
    iteratively, and then reverse the list back to original state in recursion.
    At last reversely print the the list (which has been in original state once
    again) in both recursive way and iterative way.

    Example:

    ```
    java cs6301.g16.ReversibleSinglyLinkedList 20
    20: 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20

    After iterative reversion.
    20: 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1

    After recursive reversion again.
    20: 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20

    (Reverse order)20: 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
    (Reverse order)20: 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
    ```
    
    - Problem 5

    Execute the command `java cs6301.g16.ArrayQueue
    [size:default 16]`.

    The program initial the queue size to be 17, first add and poll a single
    element to test offer/poll/peek/isEmpty operations. Then repeat add or 
    poll elements to show the storage of array. Finally, the result shows array
    elements after double size or halve size through offer and poll operations.

    Example:

    ```
    Default initial size: 16
    Initial size for test: 17
    -------------- Test offer/poll/peek/isEmpty --------------
    Offer element:true
    Get peek element:-1
    Poll element:-1
    Poll null queue:null
    isEmpty():true
    -------------- Initial Queue --------------
    18 null null 4 5 6 7 8 9 10 11 12 13 14 15 16 17 
    Original size: 17
    -------------- Test Double Size --------------
    4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 null null null null null null null null null null null null null null null null null null 
    New size: 34
    -------------- Test Halve Size ---------------
    12 13 14 15 16 17 18 19 null null null null null null null null null 
    New size: 17
    ```
