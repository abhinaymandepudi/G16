Short Project 8
===============

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
        ├── Combination.java            # Basic combination implementation.
        ├── Heap.java                   # Heap's algorithm implementation.
        ├── KnuthL.java                 # Knuth L algorithm implementation.
        ├── P7Driver.java               # Driver program for problem 7.
        ├── P8Driver.java               # Driver program for problem 8.
        ├── P9Driver.java               # Driver program for problem 9.
        └── Permutation.java            # Basic permutation implementation.

Compile
-------

To compile, run the following commands:

    # P7
    javac cs6301/g16/P7Driver.java
    
    # P8
    javac cs6301/g16/P8Driver.java

    # P9
    javac cs6301/g16/P9Driver.java




Test Running
------------

-   P7

    Specifying `n` and `k` with auto-generated `[0 ... n-1]` sequence, execute
    commands: `java cs6301.g16.Permutation <n> <k>` and `java
    cs6301.g16.Combination <n> <k>`. Program would first output total number of
    permutations/combinations, and then list all answers.

        > java cs6301.g16.Permutation 3 2
        6
        [0, 1]
        [0, 2]
        [1, 0]
        [1, 2]
        [2, 1]
        [2, 0]

        > java cs6301.g16.Combination 3 2
        3
        [0, 1]
        [0, 2]
        [1, 2]

    To specify sequence manually, execute the driver program as follow:

        > java cs6301.g16.P7Driver
        Enter VERBOSE, k and length followed by the integer sequence to compute
        permutation and combination.
        For example: 1 3 5 0 1 2 3 4
        Indicating VERBOSE = 1, k = 3, and the array with length 5 is {0, 1, 2, 3, 4}.
        1 2 5 11 22 33 44 55
        Total number of permutations: 20
        Total number of combinations: 10

        List of permutations:
        [11, 22]
        [11, 33]
        [11, 44]
        [11, 55]
        [22, 11]
        [22, 33]
        [22, 44]
        [22, 55]
        [33, 22]
        [33, 11]
        [33, 44]
        [33, 55]
        [44, 22]
        [44, 33]
        [44, 11]
        [44, 55]
        [55, 22]
        [55, 33]
        [55, 44]
        [55, 11]

        List of combinations:
        [11, 22]
        [11, 33]
        [11, 44]
        [11, 55]
        [22, 33]
        [22, 44]
        [22, 55]
        [33, 44]
        [33, 55]
        [44, 55]

-   P8

    Directly execute `KnuthL` class with length of array, the program would
    generate array `[0 ... n-1]` and output all permutations in lexicographic
    order.

        > java cs6301.g16.KnuthL 3
        [0, 1, 2]
        [0, 2, 1]
        [1, 0, 2]
        [1, 2, 0]
        [2, 0, 1]
        [2, 1, 0]

    Or execute the driver program to specify the array.

        > java cs6301.g16.P8Driver
        Enter length followed by the integer sequence to compute lexicographic permutation.
        For example: 5 0 1 2 3 4
        Indicating the array with length 5 is {0, 1, 2, 3, 4}.
        5 2 3 3 3 3
        Lexicographic order permutations are:
        [2, 3, 3, 3, 3]
        [3, 2, 3, 3, 3]
        [3, 3, 2, 3, 3]
        [3, 3, 3, 2, 3]
        [3, 3, 3, 3, 2]

-   P9

    Directly execute `Heap` class with length of array, the progran would
    generate array `[0 ... n-1]` and output all `n!` permutations.

        > java cs6301.g16.Heap 3
        [0, 1, 2]
        [1, 0, 2]
        [2, 0, 1]
        [0, 2, 1]
        [1, 2, 0]
        [2, 1, 0]

    Or execute the driver program to specify the array.

        > java cs6301.g16.P9Driver
        Enter length followed by the integer sequence to compute lexicographic permutation.
        For example: 5 0 1 2 3 4
        Indicating the array with length 5 is {0, 1, 2, 3, 4}.
        3 100 200 300
        All n! = 6 permutations are:
        [100, 200, 300]
        [200, 100, 300]
        [300, 100, 200]
        [100, 300, 200]
        [200, 300, 100]
        [300, 200, 100]
