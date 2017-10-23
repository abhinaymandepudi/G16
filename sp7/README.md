Short Project 7
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
  └── g16
      ├── AVLTree.java
      ├── BST.java
      ├── BSTMap.java
      ├── RedBlackTree.java       # Red Black Tree
      └── SplayTree.java

Compile
-------

To compile, run the following commands:

    # BST

    # AVL Tree

    # Red Black Tree
    javac cs6301/g16/RedBlackTree.java
    
    # Splay Tree
  


Test Running
------------

-   BST


-   AVL Tree



-   Red Black Tree

        > java cs6301.g16.RedBlackTree
        Test 1.Test add(),remove(),toArray()
        1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0
        Add 1 : [1] 1
        Add 3 : [2] 1 3
        Add 5 : [3] 1 3 5
        Add 7 : [4] 1 3 5 7
        Add 9 : [5] 1 3 5 7 9
        Add 2 : [6] 1 2 3 5 7 9
        Add 4 : [7] 1 2 3 4 5 7 9
        Add 6 : [8] 1 2 3 4 5 6 7 9
        Add 8 : [9] 1 2 3 4 5 6 7 8 9
        Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
        Remove -3 : [9] 1 2 4 5 6 7 8 9 10
        Remove -6 : [8] 1 2 4 5 7 8 9 10
        Remove -3 : [8] 1 2 4 5 7 8 9 10
        Final: 1 2 4 5 7 8 9 10
        Test 2.Test Iterator()
        1 2 4 5 7 8 9 10
        t is valid RedBlackTree.
