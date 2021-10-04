
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

def parseLine(def line, def type) {
    def result = new Expando()

    result.line = line
    def tokens = line.split(",")

    if (type == "o") {
        // ,Dean,Allison,Niagara West,Ontario,Conservative,2021-09-20 12:00:00 AM,,"Aquarius","Wikipedia"
        result.firstName = tokens[1]
        result.lastName = tokens[2]
        result.source = tokens[-1]
        result.sign = tokens[-2].replaceAll('"', '')
    } else {
        result.name = tokens[0]
        result.sign = (tokens.size() >= 2) ? tokens[1] : ""
    }

    return result
}

def isMatch(def line, def type) {
    def result = false

    def obj = parseLine(line, type)

    if (type == "o") {
        result = (obj.sign != '')
        if (result) {
            assert obj.source != '""'
        }
        // println "${obj.firstName} ${obj.lastName} ${obj.sign} ${obj.source}"
        // ,Dean,Allison,Niagara West,Ontario,Conservative,2021-09-20 12:00:00 AM,,"Aquarius","Wikipedia"
    } else {
        result = ! obj.sign.isEmpty()
        // println "${obj.name} '${obj.sign}'"
    }

    return result
}

def countEntries(def file, def type) {
    def result = 0
    def isHeader = true

    file.eachLine { line ->
        if (! isHeader) {
            def trimLine = line.trim()
            result += (isMatch(trimLine,type)) ? 1 : 0
        } else {
            isHeader = false
        }
    }

    return result
}

def collectEntries(def file, def type) {
    def result = []
    def isHeader = true

    file.eachLine { line ->
        if (! isHeader) {
            def trimLine = line.trim()
            if (isMatch(trimLine, type)) {
                result << parseLine(trimLine, type)
            }
        } else {
            isHeader = false
        }
    }

    return result
}

def matchEntries(def officialEntries, def workingEntries) {
    def max = 200
    max.times { i ->
        def workingEntry = workingEntries[i]
        def officialEntry = officialEntries[i]

        def workingName = workingEntry.name
        def workingSign = workingEntry.sign

        def officialFirst = officialEntry.firstName
        def officialLast = officialEntry.firstLast
        def officialSign = officialEntry.sign

        if (workingSign != officialSign) {
            println "o: ${officialEntry}"
            println "w: ${workingEntry}"
            System.exit(-1)
        }
    }
}

// ------ main

def officialFile = new File(args[0])
def workingFile = new File(args[1])

def officialCount = countEntries(officialFile, "o")
def workingCount = countEntries(workingFile, "w")

println "official count: " + officialCount
println "working count: " + workingCount

def officialEntries = collectEntries(officialFile, "o")
def workingEntries = collectEntries(workingFile, "w")

println "official # entries: " + officialEntries.size()
println "working # entries: " + workingEntries.size()

matchEntries(officialEntries, workingEntries)