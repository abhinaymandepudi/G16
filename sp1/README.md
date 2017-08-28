Short Project 1
================

Deliverable structure is as follow:


    cs6301
    ├── README.md       # Current File.
    ├── g16
    │   ├── BFS.java    # Solution for problem 2.
    │   └── Sort.java   # Solution for problem 1.
    ├── report_sp1.pdf  # Report file.
    └── test.sh         # Sorting running script.

Compile
-------

To compile, run the following commands:

    javac cs6301/g16/BFS.java
    javac cs6301/g16/Sort.java

The file from package `cs6301.g00` should be accessible.

Test Running
------------

- Problem 1

    The following bash script is in test.sh. Simply execute by command `bash
    test.sh`.

    ```bash
    #!/bin/bash
    types=("phantom" "Generic merge sort." "Int merge sort." "Generic n square sort.")
    for type in 1 2 3
    do
        echo ${types[$type]}
        for size in {1..16}
        do
            echo -n $size ''
            echo "Array size: $size million. " ${types[$type]} >> result.txt
            java cs6301.g16.Sort $type $size >> result.txt
        done
        echo ''
    done
    ```

    The script would call `java cs6301.g16.Sort <sort_type> <array_size>` several
    times for each kind of sorting method and array size from 1M to 16M
    respectively.

    The output of each execution would be saved in `result.txt` in format of:

        Array size: 1 million.  Generic merge sort.
        Time: 386 msec.
        Memory: 41 MB / 1963 MB.
        ...
        Array size: 1 million.  Int merge sort.
        Time: 153 msec.
        Memory: 44 MB / 1963 MB.
        ...
        Array size: 1 million.  Generic n square sort.
        Time: 1878691 msec.
        Memory: 61 MB / 1963 MB.
        ...

    The comparison is shown in report.

- Problem 2

  To execute, run command `java cs6301.g16.BFS [graph_config]`. If not graph
  configuration is specified, the program would read from standard input.

  A sample graph configuration is as follow:

        22 21
        8   4   1
        4   21  1
        7   1   1
        5   1   1
        6   1   1
        21  3   1
        21  2   1
        2   9   1
        15  9   1
        16  9   1
        17  16  1
        22  3   1
        10  22  1
        11  10  1
        11  12  1
        13  11  1
        14  13  1
        18  22  1
        18  20  1
        19  18  1
        21  1   1

    The diameter for this graph is: `[14, 13, 11, 10, 22, 3, 21, 2, 9, 16, 17]`