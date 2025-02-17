#!/bin/bash

if [ $# -ne 1 ]; then
    echo "Usage: $0 <username>"
    exit 1
fi

username="$1"

full_name=$(getent passwd "$username" | cut -d: -f5 | cut -d, -f1)

if [ -z "$full_name" ]; then
    echo "User not found or invalid username provided."
    exit 1
fi

echo "Full Name: $full_name"

connected_users=$(who | grep -c "^$username ")

if [ $connected_users -gt 0 ]; then
    echo "User $username is connected"
else
    echo "User $username is not connected"
fi
