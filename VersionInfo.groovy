
import java.text.SimpleDateFormat 

def doStamp = { file, workingFile ->
    def pattern = "dd-MM-yyyy HH:mm";
    def formatter = new SimpleDateFormat(pattern)
    def timestamp = formatter.format(new Date())
    def version = "v 1.1.1"

    def VERSION_TOKEN = '__ZODIAC_VERSION'
    def TIMESTAMP_TOKEN = '__ZODIAC_TIMESTAMP'

    workingFile.withWriter { writer ->
        file.eachLine { line ->
            def newLine = line
            if (newLine.indexOf(VERSION_TOKEN) != -1) {
                newLine = newLine.replaceAll(VERSION_TOKEN, version)
            }
            if (newLine.indexOf(TIMESTAMP_TOKEN) != -1) {
                newLine = newLine.replaceAll(TIMESTAMP_TOKEN, timestamp)
            }
            workingFile = writer.write(newLine + "\n")
        }
    }
}

// ------- main 

def file
def workingFile 

file = new File("./federal-mp/info.html")
workingFile = new File("./federal-mp/tmp.info.html")
doStamp(file, workingFile) 

file = new File("./federal-mp/info_fr.html")
workingFile = new File("./federal-mp/tmp.info_fr.html")
doStamp(file, workingFile) 

println "Ready."
