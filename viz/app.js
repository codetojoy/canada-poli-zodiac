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

const NUM_SIBLINGS_FOR_SMALL_TEXT = 4;
const NUM_CHARS_FOR_TINY_TEXT = 14;

// ----------

function getLocalizedJsonFile(jsonFile) {
  let result = jsonFile;
  const fileName = location.href.split("/").slice(-1);
  const isFrench = fileName == "index_fr.html";
  if (isFrench) {
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
  console.log(`TRACER fileName '${fileName}' isFrench ${isFrench} jsonFile ${result}`);
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
    let party = d.data.party;
    // https://newsinteractives.cbc.ca/elections/federal/2021/results/
    if (party === GREEN_PARTY) {
      // 32 86 2B
      result = d3.rgb(50, 134, 43);
    } else if (party === LIBERAL_PARTY) {
      // A3 0F 15
      result = d3.rgb(163, 15, 21);
    } else if (party === NDP_PARTY) {
      // D8 3E 18
      result = d3.rgb(216, 62, 24);
    } else if (party === CONSERVATIVE_PARTY) {
      // 12 4C 9A
      result = d3.rgb(18, 76, 154);
    } else if (party === BLOC_QUEBECOIS_PARTY) {
      // 13 82 9D
      result = d3.rgb(19, 130, 157);
    } else if (party === INDEPENDENT) {
      result = d3.rgb(192, 192, 192);
    }
  }
  return result;
}

function hasManyChildren(d) {
  let result = false;
  let isLeaf = d.data.children == null;
  if (isLeaf && d.parent && d.parent.data && d.parent.data.children) {
    let numNodes = d.parent.data.children.length;
    result = numNodes >= NUM_SIBLINGS_FOR_SMALL_TEXT;
  }
  return result;
}

function getTextClass(d) {
  let result = "label";

  if (hasManyChildren(d)) {
    if (d.data.name && d.data.name.length >= NUM_CHARS_FOR_TINY_TEXT) {
      result = "label-tiny";
    } else {
      result = "label-small";
    }
  }

  return result;
}

// ----------

function updateNormalMode() {
  drawCircle(NORMAL_JSON_FILE);
}

function drawCircle(jsonFile) {
  let svg = d3.select("#known"),
    margin = 20,
    diameter = +svg.attr("width"),
    g = svg.append("g").attr("transform", "translate(" + diameter / 2 + "," + diameter / 2 + ")");

  // clear any previous graph, esp. if zoomed in
  svg.selectAll("circle,text").remove();

  /*
  let previousNodes = svg.selectAll("circle,text");

  if (previousNodes.empty()) {
    loadNewNodes();
  } else {
    // fade-out previous so the UI doesn't just snap/flicker
    previousNodes
      .transition()
      .duration(500)
      .ease(Math.sqrt)
      .attr("r", 0)
      .style("stroke-opacity", 1e-6)
      .style("fill-opacity", 1e-6)
      .remove()
      .on("end", loadNewNodes);
  }
  */

  // function loadNewNodes() {
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
        return b.value - a.value;
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
      .style("fill-opacity", function (d) {
        return d.parent === root ? 1 : 0;
      })
      .style("display", function (d) {
        return d.parent === root ? "inline" : "none";
      })
      .text(function (d) {
        return d.data.name;
      });

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
  // } // loadNewNodes
} // drawCircle

function modeCheckboxHandler(event) {
  const value = event.target.value;
  if (value === "normal") {
    drawCircle(NORMAL_JSON_FILE);
  } else if (value === "elements") {
    drawCircle(ELEMENTS_JSON_FILE);
  } else if (value === "provinces") {
    drawCircle(PROVINCES_JSON_FILE);
  } else if (value === "unknown") {
    drawCircle(UNKNOWN_JSON_FILE);
  }
}

document.getElementById("checkbox-normal").addEventListener("change", modeCheckboxHandler);
document.getElementById("checkbox-elements").addEventListener("change", modeCheckboxHandler);
document.getElementById("checkbox-provinces").addEventListener("change", modeCheckboxHandler);
document.getElementById("checkbox-unknown").addEventListener("change", modeCheckboxHandler);
