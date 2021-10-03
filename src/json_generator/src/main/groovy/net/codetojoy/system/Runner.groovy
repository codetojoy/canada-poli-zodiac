
package net.codetojoy.system

import net.codetojoy.custom.Config

class Runner {
    static final def MODE_NORMAL = "normal"
    static final def MODE_ELEMENTS = "elements"
    static final def MODE_PROVINCES = "provinces"

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

    def generateJson(def mode, def infos, def outputFile) {
        def json = null
        def jsonBuilder = new JsonBuilder()

        if (mode.trim().toLowerCase() == MODE_NORMAL) {
            json = jsonBuilder.buildNormal(infos.infos)
        } else if (mode.trim().toLowerCase() == MODE_ELEMENTS) {
            json = jsonBuilder.buildWithElements(infos)
        } else if (mode.trim().toLowerCase() == MODE_PROVINCES) {
            json = jsonBuilder.buildWithProvinces(infos)
        } else {
            throw new IllegalStateException("internal error")
        }

        new File(outputFile).withWriter { writer ->
            writer.write(json)
            writer.write("\n")
        }
    }

    def run(def mode, def infile, def outfile) {
        def trimMode = mode.trim().toLowerCase()
        if (trimMode == MODE_NORMAL || trimMode == MODE_ELEMENTS || trimMode == MODE_PROVINCES) {
                // ok
        } else {
            throw new IllegalArgumentException("unknown mode: $mode")
        }
        def infoRows = buildInfos(infile)
        def infos = new Infos()
        infos.populate(infoRows)
        generateJson(mode, infos, outfile)
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

        new Runner().run(mode, infile, outfile)
    }
}
