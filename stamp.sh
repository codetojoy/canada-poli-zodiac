#!/bin/bash

set -e 

./refresh-zodiac.sh
groovy VersionInfo.groovy 

cd ./federal-mp
mv tmp.info.html info.html 
mv tmp.info_fr.html info_fr.html 
cd ..

echo "Ready."
