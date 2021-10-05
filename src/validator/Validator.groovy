
@Grab(group='org.mod4j.org.apache.commons', module='lang', version='2.1.0')

import org.apache.commons.lang.StringEscapeUtils

class DummyForGrab {}

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
        NS_DISPLAY,
        NWT_DISPLAY,
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

    def translationMap = [:]

    def Provinces() {
        translationMap[AB] = AB_DISPLAY
        translationMap[BC] = BC_DISPLAY
        translationMap[MB] = MB_DISPLAY
        translationMap[NB] = NB_DISPLAY
        translationMap[NL] = NL_DISPLAY
        translationMap[NS] = NS_DISPLAY
        translationMap[NT] = NWT_DISPLAY
        translationMap[NU] = NU_DISPLAY
        translationMap[ON] = ON_DISPLAY
        translationMap[PEI] = PEI_DISPLAY
        translationMap[QC] = QC_DISPLAY
        translationMap[SK] = SK_DISPLAY
        translationMap[YT] = YT_DISPLAY
    }

    def getDisplayProvince(def dataProvince) {
        if (translationMap.keySet().contains(dataProvince)) {
            return translationMap.get(dataProvince)
        } else {
            throw new IllegalStateException("internal error for '$dataSign' : $index")
        }
    }
}

class Locale {
    def mode
    def translationMap = [:]

    Locale(def mode) {
        this.mode = mode
        assert (this.mode == "en" || this.mode == "fr")

        translationMap["Aries"] = "Bélier"
        translationMap["Taurus"] = "Taureau"
        translationMap["Gemini"] = "Gémeaux"
        translationMap["Cancer"] = "Cancer"
        translationMap["Leo"] = "Lion"
        translationMap["Virgo"] = "Vierge"
        translationMap["Libra"] = "Balance"
        translationMap["Scorpio"] = "Scorpion"
        translationMap["Sagittarius"] = "Sagittaire"
        translationMap["Capricorn"] = "Capricorne"
        translationMap["Aquarius"] = "Verseau"
        translationMap["Pisces"] = "Poissons"

        translationMap[Provinces.AB_DISPLAY] = "Alb."
        translationMap[Provinces.BC_DISPLAY] = "C.-B."
        translationMap[Provinces.MB_DISPLAY] = "Man."
        translationMap[Provinces.NB_DISPLAY] = "N.-B."
        translationMap[Provinces.NL_DISPLAY] = "T.-N.-L."
        translationMap[Provinces.NWT_DISPLAY] = "T.N.-O."
        translationMap[Provinces.NS_DISPLAY] = "N.-É."
        translationMap[Provinces.NU_DISPLAY] = "Nt"
        translationMap[Provinces.ON_DISPLAY] = "Ont."
        translationMap[Provinces.PEI_DISPLAY] = "Î.-P.-É."
        translationMap[Provinces.QC_DISPLAY] = "Qc"
        translationMap[Provinces.SK_DISPLAY] = "Sask."
        translationMap[Provinces.YT_DISPLAY] = "Yn"
    }

    def getInverse(def t) {
        def result = null
        def isDone = false
        translationMap.each { k, v ->
            if ((! isDone) && v == t) {
                result = k
                isDone = true
            }
        }
        return result
    }

    def get(def s) {
        return (mode == "en") ? s : translate(s)
    }

    def translate(def s) {
        def result = "FR: UNKNOWN"
        if (translationMap.keySet().contains(s)) {
            result = translationMap.get(s)
        }
        return result
    }
}

def parseLine(def line) {
    def result = new Expando()

    result.line = line
    def tokens = line.split(",")

    // ,Dean,Allison,Niagara West,Ontario,Conservative,2021-09-20 12:00:00 AM,,"Aquarius","Wikipedia"
    result.firstName = tokens[1]
    result.lastName = tokens[2]
    result.province = tokens[4]
    result.source = tokens[-1]
    result.sign = tokens[-2].replaceAll('"', '')

    return result
}

def isMatch(def line, def forKnown) {
    def obj = parseLine(line)

    def result = (obj.sign != '')
    if (forKnown && result) {
        assert obj.source != '""'
    }
    // println "${obj.firstName} ${obj.lastName} ${obj.sign} ${obj.source}"
    // ,Dean,Allison,Niagara West,Ontario,Conservative,2021-09-20 12:00:00 AM,,"Aquarius","Wikipedia"

    return result
}

def collectEntries(def file, def forKnown) {
    def result = []
    def isHeader = true

    file.eachLine { line ->
        if (! isHeader) {
            def trimLine = line.trim()
            if (forKnown) {
                if (isMatch(trimLine, forKnown)) {
                    result << parseLine(trimLine)
                }
            } else {
                if (! isMatch(trimLine, forKnown)) {
                    result << parseLine(trimLine)
                }
            }
        } else {
            isHeader = false
        }
    }

    return result
}

def findSignForLine(def signs, def line, def locale) {
    def result = signs.find { sign ->
        def translatedSign = locale.get(sign)
        def pureLine = StringEscapeUtils.unescapeJava(line)
        return pureLine.indexOf("\"${translatedSign}\"") != -1
    }

    return result
}

def findProvinceForLine(def provinces, def line, def locale) {
    def result = provinces.find { province ->
        def translatedProvince = locale.get(province)
        def pureLine = StringEscapeUtils.unescapeJava(line)
        // println "TRACER fPFL p: ${province} tp: ${translatedProvince}"
        return pureLine.indexOf("\"${translatedProvince}\"") != -1
    }

    return result
}

def findProvince(def lines, def index, def locale) {
    def result = null

    def isFound = false

    for (def i = index; (!isFound) && i >= 0; i--) {
        def line = lines[i]
        def province = findProvinceForLine(Provinces.DISPLAY_PROVINCES, line, locale)
        if (province != null) {
            result = province
            isFound = true
        }
    }

    return result
}

def findSign(def lines, def index, def locale) {
    def result = null

    def SIGNS = [
        "Aries",
        "Taurus",
        "Gemini",
        "Cancer",
        "Leo",
        "Virgo",
        "Libra",
        "Scorpio",
        "Sagittarius",
        "Capricorn",
        "Aquarius",
        "Pisces",
    ]

    def isFound = false

    for (def i = index; (!isFound) && i >= 0; i--) {
        def line = lines[i]
        def sign = findSignForLine(SIGNS, line, locale)
        if (sign != null) {
            result = sign
            isFound = true
        }
    }

    return result
}

def findEntry(def entry, def lines, def locale, def forKnown) {
    def result = false

    def targetName = "${entry.firstName} ${entry.lastName}"

    for (def i = 0; (! result) && i < lines.size(); i++) {
        def pureLine = StringEscapeUtils.unescapeJava(lines[i])
        def isMatch = pureLine.indexOf(targetName) != -1
        if (isMatch) {
            if (forKnown) {
                def currentSign = findSign(lines, i, locale)
                result = (currentSign == entry.sign)
            } else {
                def currentProvince = findProvince(lines, i, locale)
                def displayProvince = new Provinces().getDisplayProvince(entry.province)
                result = (currentProvince == displayProvince)
            }
        }
    }

    if (!result) {
        println "TRACER could not find '${targetName}'"
        System.exit(-1)
    }

    return result
}

def findEntries(def officialEntries, def file, def locale, def forKnown) {
    def result = true
    def lines =  file.readLines()

    officialEntries.each { entry ->
        def tmpResult = findEntry(entry, lines, locale, forKnown)
        if (! tmpResult) {
            result = false
        }
    }

    return result
}

// ------ main

def srcDir = args[0]
def officialFile = new File("${srcDir}/zodiac_federal_mp.csv")
def officialEntries = collectEntries(officialFile, true)
println "# official entries: " + officialEntries.size()

def file
def locale
def forKnown = true

locale = new Locale("en")
file = new File("${srcDir}/zodiac_federal_mp.json")
assert findEntries(officialEntries, file, locale, forKnown)
file = new File("${srcDir}/zodiac_federal_mp_elements.json")
assert findEntries(officialEntries, file, locale, forKnown)
file = new File("${srcDir}/zodiac_federal_mp_provinces.json")
assert findEntries(officialEntries, file, locale, forKnown)

locale = new Locale("fr")
file = new File("${srcDir}/zodiac_federal_mp_fr.json")
assert findEntries(officialEntries, file, locale, forKnown)
file = new File("${srcDir}/zodiac_federal_mp_elements_fr.json")
assert findEntries(officialEntries, file, locale, forKnown)
file = new File("${srcDir}/zodiac_federal_mp_provinces_fr.json")
assert findEntries(officialEntries, file, locale, forKnown)

def officialUnknownEntries = collectEntries(officialFile, false)
println "# official unknown entries: " + officialUnknownEntries.size()

forKnown = false

locale = new Locale("en")
file = new File("${srcDir}/zodiac_federal_mp_unknown.json")
assert findEntries(officialUnknownEntries, file, locale, forKnown)

locale = new Locale("fr")
file = new File("${srcDir}/zodiac_federal_mp_unknown_fr.json")
assert findEntries(officialUnknownEntries, file, locale, forKnown)