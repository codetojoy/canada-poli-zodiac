#!/bin/bash

set -e 

SRC_DIR=../../canada-poli-zodiac
DEST_DIR=./federal-mp

# --------- assert 

stat $SRC_DIR/zodiac_federal_mp.json > /dev/null 2>&1
stat $SRC_DIR/zodiac_federal_mp_elements.json > /dev/null 2>&1
stat $SRC_DIR/zodiac_federal_mp_provinces.json > /dev/null 2>&1
stat $SRC_DIR/zodiac_federal_mp_unknown.json > /dev/null 2>&1

stat $SRC_DIR/viz/index.html > /dev/null 2>&1
stat $SRC_DIR/viz/index_fr.html > /dev/null 2>&1
stat $SRC_DIR/viz/zodiac.css > /dev/null 2>&1
stat $SRC_DIR/viz/app.js > /dev/null 2>&1

stat $SRC_DIR/viz/info.html > /dev/null 2>&1
stat $SRC_DIR/viz/info_fr.html > /dev/null 2>&1
stat $SRC_DIR/viz/info.css > /dev/null 2>&1

# --------- copy 

cp $SRC_DIR/zodiac_federal_mp.json $DEST_DIR/.
cp $SRC_DIR/zodiac_federal_mp_elements.json $DEST_DIR/.
cp $SRC_DIR/zodiac_federal_mp_provinces.json $DEST_DIR/.
cp $SRC_DIR/zodiac_federal_mp_unknown.json $DEST_DIR/.

cp $SRC_DIR/viz/index.html $DEST_DIR/.
cp $SRC_DIR/viz/index_fr.html $DEST_DIR/.
cp $SRC_DIR/viz/zodiac.css $DEST_DIR/.
cp $SRC_DIR/viz/app.js $DEST_DIR/.

cp $SRC_DIR/viz/info.html $DEST_DIR/.
cp $SRC_DIR/viz/info_fr.html $DEST_DIR/.
cp $SRC_DIR/viz/info.css $DEST_DIR/.
