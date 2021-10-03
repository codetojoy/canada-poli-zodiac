#!/bin/bash

set -e

cp ../zodiac_federal_mp.json . 
cp ../zodiac_federal_mp_elements.json . 
cp ../zodiac_federal_mp_provinces.json . 
cp ../zodiac_federal_mp_unknown.json . 

python3 -m http.server
