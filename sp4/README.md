Short Project 4
================

The problems set [click here](./sp4-recursion-2017f.md).

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
        ├── BinarySearch.java                    # Solution for Problem 4 
        ├── BSTreeLinkedList.java                # Solution for Problem 1
        ├── Fibonacci.java                       # Solution for Problem 2
        ├── 
        └── README.md                            # This File 

Compile
-------

To compile, run the following commands:

```bash
# Problem 1
# javac cs6301/g16/BSTreeLinkedList.java

# Problem 2
# javac cs6301/g16/Fibonacci.java

# Problem 3

# Problem 4
# javac cs6301/g16/BinarySearch.java

# Problem 5

# Problem 6
```

There is no dependency with file from package `cs6301.g00`.

Test Running
------------

- Problem 1

    Execute the command `java cs6301.g16.BSTreeLinkedList`.

    The program will perform in-place conversion between a sorted doubly 
    linked list and its corresponding BST. 

    Example:

    ```
     > java cs6301.g16.BSTreeLinkedList 
     
    ---- Input List:
    
    Linked List - [9]: 1 2 3 4 5 6 7 8 9
    
    ---- 1. Convert to BST:
    
    [9] BST with 4 level(s):
            5
        3       8
      2   4   7   9
     1 6
    
    ---- 2. Convert to Sorted List:
    
    Linked List - [9]: 1 2 3 4 5 6 7 8 9
    
    ---- 3. Convert to BST again:
    
    [9] BST with 4 level(s):
            5
        3       8
      2   4   7   9
     1 6

    ```
    

- Problem 4

    Execute the command `java cs6301.g16.BinarySearch`.

    This program will take a sorted list and  Returns index i such that arr[i] <= x < arr[i+1]. 

    Example:

    ```
     > java cs6301.g16.BinarySearch 
     
    -----------
    Input: arr:[1, 7, 12]; x=11;
    Output:1
    -----------
    Input: arr:[1]; x=1;
    Output:0
    -----------
    Input: arr:[1, 2, 3]; x=3;
    Output:2
    -----------
    Input: arr:[1, 3]; x=2;
    Output:0
    -----------
    Input: arr:[1, 3, 8, 10, 22, 80]; x=50;
    Output:4
    ```
