// ----------

const NORMAL_JSON_FILE = "./zodiac_federal_mp.json";
const ELEMENTS_JSON_FILE = "./zodiac_federal_mp_elements.json";
const PROVINCES_JSON_FILE = "./zodiac_federal_mp_provinces.json";
const UNKNOWN_JSON_FILE = "./zodiac_federal_mp_unknown.json";

const NORMAL_JSON_FR_FILE = "./zodiac_federal_mp_fr.json";
const ELEMENTS_JSON_FR_FILE = "./zodiac_federal_mp_elements_fr.json";
const PROVINCES_JSON_FR_FILE = "./zodiac_federal_mp_provinces_fr.json";
const UNKNOWN_JSON_FR_FILE = "./zodiac_federal_mp_unknown_fr.json";

const BACKGROUND_LIGHT = "hsl(61,80%,80%)";
const BACKGROUND_DARK = "hsl(80,30%,40%)";
const BACKGROUND_RANGE = [BACKGROUND_LIGHT, BACKGROUND_DARK];

const GREEN_PARTY = "Green Party";
const LIBERAL_PARTY = "Liberal";
const NDP_PARTY = "NDP";
const CONSERVATIVE_PARTY = "Conservative";
const BLOC_QUEBECOIS_PARTY = "Bloc Québécois";
const INDEPENDENT = "Independent";
const UNKNOWN_PARTY = "Unknown";

const GREEN_PARTY_LEGEND_FR = "Vert";
const LIBERAL_PARTY_LEGEND_FR = "Libéral";
const NDP_PARTY_LEGEND_FR = "NPD";
const CONSERVATIVE_PARTY_LEGEND_FR = "PCC";
const BLOC_QUEBECOIS_PARTY_LEGEND_FR = "BQ";
const INDEPENDENT_LEGEND_FR = "Ind.";

const GREEN_PARTY_LEGEND = "Green";
const LIBERAL_PARTY_LEGEND = "Liberal";
const NDP_PARTY_LEGEND = "NDP";
const CONSERVATIVE_PARTY_LEGEND = "CPC";
const BLOC_QUEBECOIS_PARTY_LEGEND = "BQ";
const INDEPENDENT_LEGEND = "Ind.";

const NUM_SIBLINGS_FOR_SMALL_TEXT = 4;
const NUM_SIBLINGS_FOR_TINY_TEXT = 10;
const NUM_SIBLINGS_FOR_MICRO_TEXT = 16;
const NOT_MANY_SIBLINGS = 4;
const NUM_CHARS_FOR_TINY_TEXT = 10;

const MODE_NORMAL = "normal";
const MODE_ELEMENTS = "elements";
const MODE_PROVINCES = "provinces";
const MODE_UNKNOWN = "unknown";
const MODE_ALT_COLORS = "alt-colors";

const MODE_MAP = {
  [MODE_NORMAL]: NORMAL_JSON_FILE,
  [MODE_ELEMENTS]: ELEMENTS_JSON_FILE,
  [MODE_PROVINCES]: PROVINCES_JSON_FILE,
  [MODE_UNKNOWN]: UNKNOWN_JSON_FILE,
};

// from: https://newsinteractives.cbc.ca/elections/federal/2021/results/
const PARTY_COLOR_MAP = {
  [BLOC_QUEBECOIS_PARTY]: d3.rgb(19, 130, 157), // 13 82 9D
  [CONSERVATIVE_PARTY]: d3.rgb(18, 76, 154), // 12 4C 9A
  [GREEN_PARTY]: d3.rgb(50, 134, 43), // 32 86 2B
  [INDEPENDENT]: d3.rgb(103, 103, 103), // 67 67 67
  [LIBERAL_PARTY]: d3.rgb(163, 15, 21), // A3 0F 15
  [NDP_PARTY]: d3.rgb(216, 62, 24), // D8 3E 18
};

const PARTY_SORT_MAP = {
  [LIBERAL_PARTY]: { sortOrder: 0 },
  [CONSERVATIVE_PARTY]: { sortOrder: 1 },
  [BLOC_QUEBECOIS_PARTY]: { sortOrder: 2 },
  [NDP_PARTY]: { sortOrder: 3 },
  [GREEN_PARTY]: { sortOrder: 4 },
  [INDEPENDENT]: { sortOrder: 5 },
};

// from: https://medialab.github.io/iwanthue/
const PARTY_ALT_COLOR_MAP = {
  [BLOC_QUEBECOIS_PARTY]: d3.rgb(151, 100, 201),
  [CONSERVATIVE_PARTY]: d3.rgb(18, 76, 154),
  [GREEN_PARTY]: d3.rgb(101, 159, 96),
  [INDEPENDENT]: d3.rgb(172, 165, 53),
  [LIBERAL_PARTY]: d3.rgb(198, 92, 138),
  [NDP_PARTY]: d3.rgb(197, 100, 69),
};

const LEGEND_MAP = {
  [BLOC_QUEBECOIS_PARTY_LEGEND]: { delta: 105, code: [BLOC_QUEBECOIS_PARTY], fr: [BLOC_QUEBECOIS_PARTY_LEGEND_FR] },
  [CONSERVATIVE_PARTY_LEGEND]: { delta: 105, code: [CONSERVATIVE_PARTY], fr: [CONSERVATIVE_PARTY_LEGEND_FR] },
  [GREEN_PARTY_LEGEND]: { delta: 105, code: [GREEN_PARTY], fr: [GREEN_PARTY_LEGEND_FR] },
  [INDEPENDENT_LEGEND]: { delta: 105, code: [INDEPENDENT], fr: [INDEPENDENT_LEGEND_FR] },
  [LIBERAL_PARTY_LEGEND]: { delta: 105, code: [LIBERAL_PARTY], fr: [LIBERAL_PARTY_LEGEND_FR] },
  [NDP_PARTY_LEGEND]: { delta: 105, code: [NDP_PARTY], fr: [NDP_PARTY_LEGEND_FR] },
};

const ALT_COLOR_SCHEME = "zodiac_alt_colors";

// ----------

function getColorMap() {
  console.log(`TRACER gCM cp 0`);

  if (isAltColorScheme()) {
    console.log(`TRACER gCM alt cp 1`);
    return PARTY_ALT_COLOR_MAP;
  } else {
    return PARTY_COLOR_MAP;
  }
}

function isAltColorScheme() {
  let result = false;
  if (sessionStorage.getItem(ALT_COLOR_SCHEME)) {
    let value = sessionStorage.getItem(ALT_COLOR_SCHEME);
    result = value === "true";
  }
  return result;
}

function setAltColorScheme(value) {
  console.log(`TRACER sACS cp 1`);
  sessionStorage.setItem(ALT_COLOR_SCHEME, "true");
}

function clearAltColorScheme(value) {
  sessionStorage.removeItem(ALT_COLOR_SCHEME);
}

function getTextForLegend(legend) {
  if (isFrenchMode()) {
    return LEGEND_MAP[legend].fr;
  } else {
    return legend;
  }
}

function getDeltaForLegend(legend) {
  return LEGEND_MAP[legend].delta;
}

function getColorForLegend(legend) {
  const colorMap = getColorMap();
  return colorMap[LEGEND_MAP[legend].code];
}

function isFrenchMode() {
  const fileName = location.href.split("/").slice(-1);
  const isFrench = fileName == "index_fr.html";
  return isFrench;
}

function getLocalizedJsonFile(jsonFile) {
  let result = jsonFile;
  if (isFrenchMode()) {
    switch (jsonFile) {
      case NORMAL_JSON_FILE:
        result = NORMAL_JSON_FR_FILE;
        break;
      case ELEMENTS_JSON_FILE:
        result = ELEMENTS_JSON_FR_FILE;
        break;
      case PROVINCES_JSON_FILE:
        result = PROVINCES_JSON_FR_FILE;
        break;
      case UNKNOWN_JSON_FILE:
        result = UNKNOWN_JSON_FR_FILE;
        break;
      default:
        result = jsonFile;
        break;
    }
  }
  // console.log(`TRACER fileName '${fileName}' isFrench ${isFrench} jsonFile ${result}`);
  return result;
}

function getFillColor(d) {
  let result = null;
  if (d.children) {
    if (d.data.name === UNKNOWN_PARTY) {
      result = d3.color("white");
    } else {
      let color = d3.scaleLinear().domain([-1, 5]).range(BACKGROUND_RANGE).interpolate(d3.interpolateHcl);
      result = color(d.depth);
    }
  } else {
    // leaf
    const colorMap = getColorMap();
    result = colorMap[d.data.party];
  }
  return result;
}

function getNumChildren(d) {
  let result = 0;
  let isLeaf = d.data.children == null;
  if (isLeaf && d.parent && d.parent.data && d.parent.data.children) {
    result = d.parent.data.children.length;
  }
  return result;
}

function getTextClass(d) {
  let result = "label";
  const numChildren = getNumChildren(d);
  let nameLen = 0;
  if (d.data && d.data.name) {
    nameLen = d.data.name.length;
  }
  if (numChildren >= NUM_SIBLINGS_FOR_MICRO_TEXT) {
    result = "label-micro";
  } else if (numChildren >= NUM_SIBLINGS_FOR_TINY_TEXT) {
    result = "label-tiny";
  } else if (numChildren >= NUM_SIBLINGS_FOR_SMALL_TEXT) {
    result = "label-small";
  } else if (numChildren > NOT_MANY_SIBLINGS && nameLen >= NUM_CHARS_FOR_TINY_TEXT) {
    result = "label-tiny";
  }
  return result;
}

function nameSort(a, b) {
  let result = 0;
  if (a.data && b.data && a.data.name && b.data.name) {
    if (a.data.name < b.data.name) {
      result = -1;
    }
    if (a.data.name > b.data.name) {
      result = 1;
    }
    // console.log(`TRACER ${result} A ${partyA} B ${partyB}`);
  } else {
    result = b.value - a.value;
  }
  return -1 * result;
}

function partySort(a, b) {
  let result = 0;
  if (a.data && b.data && a.data.party && b.data.party) {
    const partyA = a.data.party;
    const partyB = b.data.party;

    const valueA = PARTY_SORT_MAP[partyA].sortOrder;
    const valueB = PARTY_SORT_MAP[partyB].sortOrder;
    if (valueA < valueB) {
      result = -1;
    }
    if (valueA > valueB) {
      result = 1;
    }
    console.log(`TRACER ${result} A ${partyA} B ${partyB}`);
  } else {
    result = b.value - a.value;
  }
  return result;
}

// ----------

function updateNormalMode() {
  checkAltColorsForUI();
  drawCircle(NORMAL_JSON_FILE);
}

function drawHorizontalLegend() {
  let svg = d3.select("#legend");
  svg.append("rect").attr("height", "100%").attr("width", "100%").attr("fill", d3.color(BACKGROUND_LIGHT));

  let keys = [
    GREEN_PARTY_LEGEND,
    LIBERAL_PARTY_LEGEND,
    NDP_PARTY_LEGEND,
    CONSERVATIVE_PARTY_LEGEND,
    BLOC_QUEBECOIS_PARTY_LEGEND,
    INDEPENDENT_LEGEND,
  ];

  const firstDotX = 20;
  const firstDotY = 20;
  // const delta = 80;
  const radius = 7;

  svg
    .selectAll("mydots")
    .data(keys)
    .enter()
    .append("circle")
    .attr("cx", function (d, i) {
      return firstDotX + i * getDeltaForLegend(d);
    })
    .attr("cy", firstDotY)
    .attr("r", radius)
    .style("fill", function (d) {
      return getColorForLegend(d);
    });

  const firstLabelX = firstDotX + 20;
  const firstLabelY = firstDotY + 7;

  svg
    .selectAll("mylabels")
    .data(keys)
    .enter()
    .append("text")
    .attr("x", function (d, i) {
      return firstLabelX + i * getDeltaForLegend(d);
    })
    .attr("y", firstLabelY)
    .style("fill", function (d) {
      return getColorForLegend(d);
    })
    .text(function (d) {
      return getTextForLegend(d);
    })
    .attr("text-anchor", "left")
    .style("alignment-baseline", "middle");
}

function drawCircle(jsonFile) {
  // const circleWidth = 660;
  // const diameter = circleWidth;
  // const translateX = diameter / 2;
  let svg = d3.select("#known"),
    margin = 20,
    diameter = +svg.attr("width"),
    g = svg.append("g").attr("transform", "translate(" + diameter / 2 + "," + diameter / 2 + ")");

  // clear any previous graph, esp. if zoomed in
  svg.selectAll("circle,text").remove();

  let color = d3.scaleLinear().domain([-1, 5]).range(BACKGROUND_RANGE).interpolate(d3.interpolateHcl);

  let pack = d3
    .pack()
    .size([diameter - margin, diameter - margin])
    .padding(2);

  d3.json(getLocalizedJsonFile(jsonFile), function (error, root) {
    if (error) throw error;

    root = d3
      .hierarchy(root)
      .sum(function (d) {
        return d.size;
      })
      .sort(function (a, b) {
        return partySort(a, b);
      });

    let focus = root,
      nodes = pack(root).descendants(),
      view;

    let circle = g
      .selectAll("circle")
      .data(nodes)
      .enter()
      .append("circle")
      .attr("class", function (d) {
        return d.parent ? (d.children ? "node" : "node node--leaf") : "node node--root";
      })
      .style("fill", getFillColor)
      .on("click", function (d) {
        if (focus !== d) zoom(d), d3.event.stopPropagation();
      });

    let text = g
      .selectAll("text")
      .data(nodes)
      .enter()
      .append("text")
      .attr("class", getTextClass)
      .style("fill", "white")
      .style("fill-opacity", function (d) {
        return d.parent === root ? 1 : 0;
      })
      .style("display", function (d) {
        return d.parent === root ? "inline" : "none";
      })
      .text(function (d) {
        return d.data.name;
      });

    drawHorizontalLegend();

    let node = g.selectAll("circle,text");

    svg.style("background", color(-1)).on("click", function () {
      zoom(root);
    });

    zoomTo([root.x, root.y, root.r * 2 + margin]);

    function zoom(d) {
      focus = d;

      let transition = d3
        .transition()
        .duration(d3.event.altKey ? 7500 : 750)
        .tween("zoom", function (d) {
          let i = d3.interpolateZoom(view, [focus.x, focus.y, focus.r * 2 + margin]);
          return function (t) {
            zoomTo(i(t));
          };
        });

      transition
        .selectAll("text")
        .filter(function (d) {
          return d.parent === focus || this.style.display === "inline";
        })
        .style("fill-opacity", function (d) {
          return d.parent === focus ? 1 : 0;
        })
        .on("start", function (d) {
          if (d.parent === focus) this.style.display = "inline";
        })
        .on("end", function (d) {
          if (d.parent !== focus) this.style.display = "none";
        });
    }

    function zoomTo(v) {
      const k = diameter / v[2];
      view = v;
      node.attr("transform", function (d) {
        return "translate(" + (d.x - v[0]) * k + "," + (d.y - v[1]) * k + ")";
      });
      circle.attr("r", function (d) {
        return d.r * k;
      });
    }
  });
} // drawCircle

// ----------------- DOM/event handlers

let currentMode = MODE_NORMAL;

function modeCheckboxHandler(event) {
  const mode = event.target.value;
  currentMode = mode;
  drawCircle(MODE_MAP[mode]);
}

function checkAltColorsForUI() {
  const value = isAltColorScheme();
  let cb = document.getElementById("checkbox-alt-colors");
  cb.checked = value;
}

function altColorsHandler(cb) {
  if (cb.target.checked) {
    console.log(`TRACER handler checked`);
    setAltColorScheme();
  } else {
    clearAltColorScheme();
  }
  drawCircle(MODE_MAP[currentMode]);
}

document.getElementById("checkbox-normal").addEventListener("change", modeCheckboxHandler);
document.getElementById("checkbox-elements").addEventListener("change", modeCheckboxHandler);
document.getElementById("checkbox-provinces").addEventListener("change", modeCheckboxHandler);
document.getElementById("checkbox-unknown").addEventListener("change", modeCheckboxHandler);
document.getElementById("checkbox-alt-colors").addEventListener("click", altColorsHandler);

const backdrop = document.querySelector(".backdrop");
const toggleButton = document.querySelector(".toggle-button");
const sideNav = document.querySelector(".side-nav");

toggleButton.addEventListener("click", function () {
  sideNav.classList.add("open");
  backdrop.style.display = "block";
  setTimeout(function () {
    backdrop.classList.add("open");
  }, 10);
});

backdrop.addEventListener("click", function () {
  sideNav.classList.remove("open");
  closeModal();
});

function closeModal() {
  backdrop.classList.remove("open");
  setTimeout(function () {
    backdrop.style.display = "none";
  }, 200);
}
