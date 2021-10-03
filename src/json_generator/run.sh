#!/bin/bash

set -e

SRC_CSV=../../zodiac_federal_mp.csv
TARGET_ZODIAC=../../zodiac_federal_mp.json
TARGET_ELEMENTS=../../zodiac_federal_mp_elements.json
TARGET_PROVINCES=../../zodiac_federal_mp_provinces.json
TARGET_UNKNOWN=../../zodiac_federal_mp_unknown.json

stat $SRC_CSV > /dev/null 2>&1

./gradlew clean test installDist
stat staging/bin/json_generator > /dev/null 2>&1

./staging/bin/json_generator normal $SRC_CSV $TARGET_ZODIAC
./staging/bin/json_generator elements $SRC_CSV $TARGET_ELEMENTS
./staging/bin/json_generator provinces $SRC_CSV $TARGET_PROVINCES
./staging/bin/json_generator unknown $SRC_CSV $TARGET_UNKNOWN

echo "Ready."
