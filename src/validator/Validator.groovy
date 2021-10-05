
@Grab(group='org.mod4j.org.apache.commons', module='lang', version='2.1.0')

import org.apache.commons.lang.StringEscapeUtils

class DummyForGrab {}



def parseLine(def line) {
    def result = new Expando()

    result.line = line
    def tokens = line.split(",")

    // ,Dean,Allison,Niagara West,Ontario,Conservative,2021-09-20 12:00:00 AM,,"Aquarius","Wikipedia"
    result.firstName = tokens[1]
    result.lastName = tokens[2]
    result.source = tokens[-1]
    result.sign = tokens[-2].replaceAll('"', '')

    return result
}

def isMatch(def line ) {
    def obj = parseLine(line)

    def result = (obj.sign != '')
    if (result) {
        assert obj.source != '""'
    }
    // println "${obj.firstName} ${obj.lastName} ${obj.sign} ${obj.source}"
    // ,Dean,Allison,Niagara West,Ontario,Conservative,2021-09-20 12:00:00 AM,,"Aquarius","Wikipedia"

    return result
}

def collectEntries(def file) {
    def result = []
    def isHeader = true

    file.eachLine { line ->
        if (! isHeader) {
            def trimLine = line.trim()
            if (isMatch(trimLine)) {
                result << parseLine(trimLine)
            }
        } else {
            isHeader = false
        }
    }

    return result
}

def findSignForLine(def signs, def line) {
    def result = signs.find { line.indexOf("\"${it}\"") != -1 }

    return result
}

def findSign(def lines, def index) {
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
        def sign = findSignForLine(SIGNS, line)
        if (sign != null) {
            result = sign
            isFound = true
        }
    }

    return result
}

def findEntry(def entry, def lines) {
    def result = false

    def targetName = "${entry.firstName} ${entry.lastName}"

    for (def i = 0; (! result) && i < lines.size(); i++) {
        def pureLine = StringEscapeUtils.unescapeJava(lines[i])
        def isMatch = pureLine.indexOf(targetName) != -1
        if (isMatch) {
            def currentSign = findSign(lines, i)
            // println "TRACER ${targetName} a: ${currentSign} e: ${entry.sign}"
            result = (currentSign == entry.sign)
        }
    }

    if (!result) {
        println "TRACER could not find '${targetName}'"
        System.exit(-1)
    }

    return result
}

def findEntries(def officialEntries, def file) {
    def result = true
    def lines =  file.readLines()

    officialEntries.each { entry ->
        def tmpResult = findEntry(entry, lines)
        if (! tmpResult) {
            result = false
        }
    }

    return result
}

// ------ main

def srcDir = args[0]
def officialFile = new File("${srcDir}/zodiac_federal_mp.csv")
def officialEntries = collectEntries(officialFile)
println "# official entries: " + officialEntries.size()

def file

file = new File("${srcDir}/zodiac_federal_mp.json")
assert findEntries(officialEntries, file)
file = new File("${srcDir}/zodiac_federal_mp_elements.json")
assert findEntries(officialEntries, file)
file = new File("${srcDir}/zodiac_federal_mp_provinces.json")
assert findEntries(officialEntries, file)

file = new File("${srcDir}/zodiac_federal_mp_fr.json")
// assert findEntries(officialEntries, file)
file = new File("${srcDir}/zodiac_federal_mp_elements_fr.json")
// assert findEntries(officialEntries, file)
file = new File("${srcDir}/zodiac_federal_mp_provinces_fr.json")
// assert findEntries(officialEntries, file)