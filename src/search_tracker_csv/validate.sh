#!/bin/bash

set -e

SRC_CSV=../../zodiac_federal_mp.csv
NAMES_CSV=./names.csv

stat $SRC_CSV > /dev/null 2>&1
stat $NAMES_CSV > /dev/null 2>&1

groovy Validator.groovy $SRC_CSV $NAMES_CSV 

echo "Ready."
