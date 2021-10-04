
package net.codetojoy.system

class Provinces {
    static final def AB_DISPLAY = "Alta."
    static final def BC_DISPLAY = "B.C."
    static final def MB_DISPLAY = "Man."
    static final def NB_DISPLAY = "N.B."
    static final def NL_DISPLAY = "N.L."
    static final def NWT_DISPLAY = "N.W.T."
    static final def NS_DISPLAY = "N.S."
    static final def NU_DISPLAY = "Nvt."
    static final def ON_DISPLAY = "Ont."
    static final def PEI_DISPLAY = "P.E.I."
    static final def QC_DISPLAY = "Que."
    static final def SK_DISPLAY = "Sask."
    static final def YT_DISPLAY = "Y.T."

    static final def DISPLAY_PROVINCES = [
        AB_DISPLAY,
        BC_DISPLAY,
        MB_DISPLAY,
        NB_DISPLAY,
        NL_DISPLAY,
        NWT_DISPLAY,
        NS_DISPLAY,
        NU_DISPLAY,
        ON_DISPLAY,
        PEI_DISPLAY,
        QC_DISPLAY,
        SK_DISPLAY,
        YT_DISPLAY,
    ]

    static final def AB = "Alberta"
    static final def BC = "British Columbia"
    static final def MB = "Manitoba"
    static final def NB = "New Brunswick"
    static final def NL = "Newfoundland and Labrador"
    static final def NT = "Northwest Territories"
    static final def NS = "Nova Scotia"
    static final def NU = "Nunavut"
    static final def ON = "Ontario"
    static final def PEI = "Prince Edward Island"
    static final def QC = "Quebec"
    static final def SK = "Saskatchewan"
    static final def YT = "Yukon"

    static final def DATA_PROVINCES = [AB, BC, MB, NB, NL, NS, NT, NU, ON, PEI, QC, SK, YT]

    def getDisplayProvince(def dataProvince) {
        def target = dataProvince.trim().toUpperCase()

        def index = DATA_PROVINCES.findIndexOf{ it.trim().toUpperCase() == target }

        if (index >= 0) {
            return DISPLAY_PROVINCES[index]
        } else {
            throw new IllegalStateException("internal error for '$dataSign' : $index")
        }
    }
}
