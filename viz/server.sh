#!/bin/bash

set -e

cp ../zodiac_federal_mp.json . 
cp ../zodiac_federal_mp_elements.json . 

python3 -m http.server
