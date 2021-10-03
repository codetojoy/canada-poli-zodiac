
import java.text.SimpleDateFormat 

def file = new File("./federal-mp/index.html")
def workingFile = new File("./federal-mp/tmp.index.html")

def pattern = "dd-MM-yyyy HH:mm";
def formatter = new SimpleDateFormat(pattern)
def timestamp = formatter.format(new Date())
def version = "v 1.1.3"

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

println "Ready."
