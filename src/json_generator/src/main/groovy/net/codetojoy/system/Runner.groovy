
package net.codetojoy.system

import net.codetojoy.custom.Config
import net.codetojoy.system.json.*

class Runner {
    static final def MODE_NORMAL = "normal"
    static final def MODE_ELEMENTS = "elements"
    static final def MODE_PROVINCES = "provinces"
    static final def MODE_UNKNOWN = "unknown"

    def parser

    def Runner() {
        parser = new Config().parser
    }

    def buildInfos(def infile) {
        def infos = []
        def isHeader = true
        def header = ""

        new File(infile).eachLine { line ->
            if (isHeader) {
                isHeader = false
                header = line
            } else {
                def text = "${header}\n${line}\n"
                infos = parser.parseFile(text, infos)
            }
        }

        return infos
    }

    def generateJson(def mode, def infos, def outputFile, def locale) {
        def json = null

        if (mode.trim().toLowerCase() == MODE_NORMAL) {
            json = new JsonBuilder().buildNormal(infos.infos, locale)
        } else if (mode.trim().toLowerCase() == MODE_ELEMENTS) {
            json = new JsonElementBuilder().buildWithElements(infos, locale)
        } else if (mode.trim().toLowerCase() == MODE_PROVINCES) {
            json = new JsonProvinceBuilder().buildWithProvinces(infos, locale)
        } else if (mode.trim().toLowerCase() == MODE_UNKNOWN) {
            json = new JsonUnknownBuilder().buildForUnknown(infos, locale)
        } else {
            throw new IllegalStateException("internal error")
        }

        new File(outputFile).withWriter { writer ->
            writer.write(json)
            writer.write("\n")
        }
    }

    def run(def mode, def infile, def outfile, def locale) {
        def trimMode = mode.trim().toLowerCase()
        def modes = [MODE_NORMAL, MODE_ELEMENTS, MODE_PROVINCES, MODE_UNKNOWN]
        if (! modes.contains(trimMode)) {
            throw new IllegalArgumentException("unknown mode: $mode")
        }
        def infoRows = buildInfos(infile)
        def infos = new Infos()
        infos.populate(infoRows)
        generateJson(mode, infos, outfile, locale)
    }

    def static void main(String[] args) {
        if (args.size() < 3) {
            println "Usage: groovy Runner.groovy mode infile outfile"
            System.exit(-1)
        }

        def mode = args[0]
        def infile = args[1]
        assert new File(infile).exists()
        def outfile = args[2]
        def locale = new Locale(args[3])

        new Runner().run(mode, infile, outfile, locale)
    }
}
