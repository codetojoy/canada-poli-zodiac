### Zodiac Signs for Canadian Federal MPs (Members of Parliament)

- A silly project, inspired by [this tweet](https://twitter.com/perry_chel/status/1437800478897758212)
  - Uses the amazing [D3.js library](https://d3js.org)
  - Derived, specifically, from [this example](https://gist.github.com/mbostock/4063530)
        - this example is licensed under GPL v 3.0 and this project is compatible: Apache License Version 2.0
- Data-set is [here](./zodiac_federal_mp.csv)
  - derived from [this Open Data file](https://www.ourcommons.ca/en/open-data#CurrentMembers)
  - see License Details below
- All birthdays/signs found on [Wikipedia](https://wikipedia.org) or via public tweets.
  - sources listed in the data file
- Names, titles, ridings from [this page](https://www.assembly.pe.ca/members)
  - current as of 05-OCT-2021

### Technical details

- original data-set is `./zodiac_federal_mp.csv`
- this project requires Gradle and Groovy (and Java JDK)
- `./src/json-generator` produces various `json` files from `./zodiac_federal_mp.csv`
- `./viz` is the project for the [circle-packing visualization](https://codetojoy.github.io/canada-poli-zodiac/index.html)
  - `gh-pages` branch contains publishing scripts

### License details

- [This data file](./zodiac_federal_mp.csv) is licensed under a [Creative Commons Attribution-ShareAlike 4.0 International License](http://creativecommons.org/licenses/by-sa/4.0/).
- The code and website are licensed via [Apache License Version 2.0](./LICENSE).
