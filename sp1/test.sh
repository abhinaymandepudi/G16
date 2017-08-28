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
