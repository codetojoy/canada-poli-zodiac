#!/bin/bash

set -e

SRC_DIR=../..

stat $SRC_DIR/zodiac_federal_mp.csv > /dev/null 2>&1

stat $SRC_DIR/zodiac_federal_mp.json > /dev/null 2>&1
stat $SRC_DIR/zodiac_federal_mp_elements.json > /dev/null 2>&1
stat $SRC_DIR/zodiac_federal_mp_provinces.json > /dev/null 2>&1
stat $SRC_DIR/zodiac_federal_mp_unknown.json > /dev/null 2>&1

stat $SRC_DIR/zodiac_federal_mp_fr.json > /dev/null 2>&1
stat $SRC_DIR/zodiac_federal_mp_elements_fr.json > /dev/null 2>&1
stat $SRC_DIR/zodiac_federal_mp_provinces_fr.json > /dev/null 2>&1
stat $SRC_DIR/zodiac_federal_mp_unknown_fr.json > /dev/null 2>&1

echo "running..."
groovy Validator.groovy $SRC_DIR

echo "Ready."
