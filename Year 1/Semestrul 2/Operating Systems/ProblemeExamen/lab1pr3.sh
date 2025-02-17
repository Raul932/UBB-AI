#!/bin/bash

if [ $# -ne 1 ]; then
	exit 1
fi

num=$1

if [ $((num % 2)) -eq 0 ]; then
	exit 1
fi

for ((i=3; i <= $((num / 2)); i=i+2))
do
     if [ $(($num % $i)) -eq 0 ]; then
          echo "Not prime"
          exit 1
     fi
done

echo "Prime"

