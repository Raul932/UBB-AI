#!/bin/bash

if [ $# -ne 2 ]; then
	exit 1
fi

num1=$1
num2=$2

sum=$(($num1 + $num2))
diff=$(($num1 - $num2))
prod=$(($num1 * $num2))

echo "Sum: $sum"
echo "Diff: $diff"
echo "Prod: $prod"

