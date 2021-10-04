#!/bin/bash

set -e

cp ../zodiac_federal_mp.json . 
cp ../zodiac_federal_mp_elements.json . 
cp ../zodiac_federal_mp_provinces.json . 
cp ../zodiac_federal_mp_unknown.json . 

cp ../zodiac_federal_mp_fr.json . 
cp ../zodiac_federal_mp_elements_fr.json . 
cp ../zodiac_federal_mp_provinces_fr.json . 
cp ../zodiac_federal_mp_unknown_fr.json . 

python3 -m http.server
