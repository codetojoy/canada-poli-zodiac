#!/bin/bash

set -e 

./refresh-zodiac.sh
groovy VersionInfo.groovy 

cd ./federal-mp
mv tmp.index.html index.html 
mv tmp.index_fr.html index_fr.html 
cd ..

git add -u . 
git add ./federal-mp
git commit -m "incremental refresh"
git push origin gh-pages

echo "Ready."
