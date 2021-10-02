#!/bin/bash

set -e

SRC_CSV=../../zodiac_federal_mp.csv

stat $SRC_CSV > /dev/null 2>&1

groovy MyTranslator.groovy $SRC_CSV 
