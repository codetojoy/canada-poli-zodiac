describe("App", function () {
  beforeEach(function () {
    let store = {};
    const mockSessionStorage = {
      getItem: function (key) {
        return store[key];
      },
      setItem: function (key, value) {
        store[key] = `${value}`;
      },
      removeItem: function (key) {
        delete store[key];
      },
      clear: function () {
        store = {};
      },
    };
    spyOn(sessionStorage, "getItem").and.callFake(mockSessionStorage.getItem);
    spyOn(sessionStorage, "setItem").and.callFake(mockSessionStorage.setItem);
    spyOn(sessionStorage, "removeItem").and.callFake(mockSessionStorage.removeItem);
    spyOn(sessionStorage, "clear").and.callFake(mockSessionStorage.clear);
  });

  describe("isAltColorScheme", function () {
    it("should have default value", function () {
      // test
      const result = isAltColorScheme();

      expect(result).toBeFalsy();
      expect(window.sessionStorage.getItem).toHaveBeenCalledWith(ALT_COLOR_SCHEME);
    });
    it("should be enabled by session storage", function () {
      setAltColorScheme();

      // test
      const result = isAltColorScheme();

      expect(result).toBeTruthy();
      expect(window.sessionStorage.getItem).toHaveBeenCalledWith(ALT_COLOR_SCHEME);
    });
  });

  describe("getFillColor", function () {
    it("should handle unknown party", function () {
      const d = {
        children: [1, 2],
        data: {
          name: UNKNOWN_PARTY,
        },
      };

      // test
      const result = getFillColor(d);

      expect(result).toEqual(d3.color("white"));
    });
    it("should handle non-leaf node", function () {
      const d = {
        depth: 0,
        children: [1, 2],
        data: {
          name: "bogus",
        },
      };

      // test
      const result = getFillColor(d);

      expect(result).toEqual("rgb(220, 225, 147)");
    });

    it("should handle leaf node for normal color scheme", function () {
      const d = {
        data: {
          party: BLOC_QUEBECOIS_PARTY,
        },
      };

      // test
      const result = getFillColor(d);

      expect(result).toEqual(d3.rgb(19, 130, 157));
    });

    it("should handle leaf node for alt-color scheme", function () {
      setAltColorScheme();

      const d = {
        data: {
          party: BLOC_QUEBECOIS_PARTY,
        },
      };

      // test
      const result = getFillColor(d);

      expect(result).toEqual(d3.rgb(151, 100, 201));
    });
  });

  describe("isFrenchMode", function () {
    it("should have default value of English mode", function () {
      // test
      const result = isFrenchMode();

      expect(result).toBeFalsy();
    });

    it("should detect French mode", function () {
      spyOn(myLocation, "getCurrentURL").and.returnValue("./index_fr.html");

      // test
      const result = isFrenchMode();

      expect(result).toBeTruthy();
    });
  });

  describe("getNumSiblings", function () {
    it("should have basic functionality", function () {
      const d = {
        data: {},
        parent: {
          data: {
            children: [1, 2, 3, 4, 5, 6, 7],
          },
        },
      };
      // test
      const result = getNumSiblings(d);

      expect(result).toEqual(7);
    });
  });

  describe("getTextClass", function () {
    it("should detect when to use micro size", function () {
      const d = {
        data: {},
        parent: {
          data: {
            children: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16],
          },
        },
      };
      // test
      const result = getTextClass(d);

      expect(result).toEqual("label-micro");
    });

    it("should detect when to use tiny size", function () {
      const d = {
        data: {},
        parent: {
          data: {
            children: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
          },
        },
      };
      // test
      const result = getTextClass(d);

      expect(result).toEqual("label-tiny");
    });

    it("should detect when to use small size", function () {
      const d = {
        data: {},
        parent: {
          data: {
            children: [1, 2, 3, 4],
          },
        },
      };
      // test
      const result = getTextClass(d);

      expect(result).toEqual("label-small");
    });

    it("should have default value", function () {
      const d = {
        data: {
          name: "123456789A",
        },
        parent: {
          data: {
            children: [1, 2],
          },
        },
      };
      // test
      const result = getTextClass(d);

      expect(result).toEqual("label");
    });
  });

  describe("getTextForLegend", function () {
    it("should have value in English", function () {
      // test
      const result = getTextForLegend(LIBERAL_PARTY_LEGEND);

      expect(result).toEqual(LIBERAL_PARTY_LEGEND);
    });

    it("should have value in French", function () {
      spyOn(myLocation, "getCurrentURL").and.returnValue("./index_fr.html");

      // test
      const result = getTextForLegend(LIBERAL_PARTY_LEGEND);

      expect(result).toEqual([LIBERAL_PARTY_LEGEND_FR]);
    });
  });

  describe("getDeltaForLegend", function () {
    it("should have delta for legend key", function () {
      // test
      const result = getDeltaForLegend(LIBERAL_PARTY_LEGEND);

      expect(result).toEqual(105);
    });
  });

  describe("getColorForLegend", function () {
    it("should have color for legend key", function () {
      // test
      const result = getColorForLegend(LIBERAL_PARTY_LEGEND);

      expect(result).toEqual(d3.rgb(163, 15, 21));
    });
  });

  describe("getLocalizedJsonFile", function () {
    it("should reflexive in English mode", function () {
      // test
      const result = getLocalizedJsonFile(ELEMENTS_JSON_FILE);

      expect(result).toEqual(ELEMENTS_JSON_FILE);
    });

    it("should adapt for French mode", function () {
      spyOn(myLocation, "getCurrentURL").and.returnValue("./index_fr.html");

      // test
      const result = getLocalizedJsonFile(ELEMENTS_JSON_FILE);

      expect(result).toEqual(ELEMENTS_JSON_FR_FILE);
    });
  });

  describe("partySort", function () {
    it("should sort A vs B", function () {
      const a = {
        data: {
          party: LIBERAL_PARTY,
        },
      };
      const b = {
        data: {
          party: CONSERVATIVE_PARTY,
        },
      };

      // test
      const result = partySort(a, b);

      expect(result).toEqual(-1);
    });
    it("should sort A vs A", function () {
      const a = {
        data: {
          party: LIBERAL_PARTY,
        },
      };
      const b = {
        data: {
          party: LIBERAL_PARTY,
        },
      };

      // test
      const result = partySort(a, b);

      expect(result).toEqual(0);
    });
    it("should sort B vs A", function () {
      const a = {
        data: {
          party: LIBERAL_PARTY,
        },
      };
      const b = {
        data: {
          party: CONSERVATIVE_PARTY,
        },
      };

      // test
      const result = partySort(b, a);

      expect(result).toEqual(1);
    });
  });
});
