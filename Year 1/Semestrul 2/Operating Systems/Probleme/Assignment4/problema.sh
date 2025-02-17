#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <cpp_file>"
    exit 1
fi

file=$1

classes=$(grep -o 'class [[:alnum:]_]\+' $file | awk '{print $2}')

main_start=$(grep -n "int main()" $file | cut -d: -f1)

echo "$classes" | while read classname
do
    if [ ! -z "$classname" ]; then
        echo "Class: $classname, Instances:"
        lines=$(awk "/int main()/,/^}/" $file | grep -n "$classname(" | cut -d: -f1)
        if [ ! -z "$lines" ]; then
            echo "$lines" | awk -v start=$main_start '{print "Line: " ($0 + start - 1)}'
        else
            echo "None"
        fi
    fi
done


