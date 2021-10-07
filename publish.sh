#!/bin/bash

set -e 

./refresh-zodiac.sh
groovy VersionInfo.groovy 

cd ./federal-mp
mv tmp.info.html info.html 
mv tmp.info_fr.html info_fr.html 
cd ..

git add -u . 
git add ./federal-mp
git commit -m "incremental refresh"
git push origin gh-pages

echo "Ready."
