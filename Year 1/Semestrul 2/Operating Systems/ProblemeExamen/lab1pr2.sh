#!/bin/bash

if [ $# -ne 2 ]; then
	exit 1
fi

file1=$1
file2=$2

if [ ! -f "$file1" ] || [ ! -f "$file2" ]; then
	exit 1
fi


