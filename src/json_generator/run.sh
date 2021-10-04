#!/bin/bash

set -e

SRC_CSV=../../zodiac_federal_mp.csv

TARGET_ZODIAC=../../zodiac_federal_mp.json
TARGET_ELEMENTS=../../zodiac_federal_mp_elements.json
TARGET_PROVINCES=../../zodiac_federal_mp_provinces.json
TARGET_UNKNOWN=../../zodiac_federal_mp_unknown.json

TARGET_ZODIAC_FR=../../zodiac_federal_mp_fr.json
TARGET_ELEMENTS_FR=../../zodiac_federal_mp_elements_fr.json
TARGET_PROVINCES_FR=../../zodiac_federal_mp_provinces_fr.json
TARGET_UNKNOWN_FR=../../zodiac_federal_mp_unknown_fr.json

stat $SRC_CSV > /dev/null 2>&1

./gradlew clean test installDist

JSON_GEN_EXE=./staging/bin/json_generator
stat $JSON_GEN_EXE > /dev/null 2>&1

$JSON_GEN_EXE normal $SRC_CSV $TARGET_ZODIAC en
$JSON_GEN_EXE elements $SRC_CSV $TARGET_ELEMENTS en
$JSON_GEN_EXE provinces $SRC_CSV $TARGET_PROVINCES en
$JSON_GEN_EXE unknown $SRC_CSV $TARGET_UNKNOWN en

$JSON_GEN_EXE normal $SRC_CSV $TARGET_ZODIAC_FR fr
$JSON_GEN_EXE elements $SRC_CSV $TARGET_ELEMENTS_FR fr
$JSON_GEN_EXE provinces $SRC_CSV $TARGET_PROVINCES_FR fr
$JSON_GEN_EXE unknown $SRC_CSV $TARGET_UNKNOWN_FR fr

echo "Ready."
