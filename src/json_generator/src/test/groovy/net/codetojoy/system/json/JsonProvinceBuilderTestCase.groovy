package net.codetojoy.system.json

import net.codetojoy.system.*
import net.codetojoy.custom.Info

import groovy.json.*

import org.junit.*
import static org.junit.Assert.*

class JsonProvinceBuilderTestCase {
    def jsonProvinceBuilder = new JsonProvinceBuilder()

    @Test
    void testBuildWithProvinces_en() {
        def name = "Mozart"
        def zodiac = "Aquarius"
        def province = "Prince Edward Island"
        def riding = "Salzburg"
        def party = "Independent"     // which party would Mozart join?
        def source = "Wikipedia"

        def info = new Info(name: name, zodiac: zodiac,
                            province: province, riding: riding,
                           party: party, source: source, lastName: name)
        def infoRows = []
        infoRows << info
        def infos = new Infos()
        infos.populate(infoRows)

        def locale = new Locale("en")

        // test
        def result = jsonProvinceBuilder.buildWithProvinces(infos)

        println "TRACER JPB english"
        println result

        // this is ridiculous, but YOLO:
        def jsonSlurper = new JsonSlurper()
        def resultMap = jsonSlurper.parseText(result)
        def topLevelChildren = resultMap["children"]

        assertEquals(resultMap["name"], "zodiac")
        assertEquals(topLevelChildren.size(), 11)

        def i = 0
        def abNode = topLevelChildren[i++]
        def bcNode = topLevelChildren[i++]
        def mbNode = topLevelChildren[i++]
        def nbNode = topLevelChildren[i++]
        def nlNode = topLevelChildren[i++]
        def nsNode = topLevelChildren[i++]
        def ntNode = topLevelChildren[i++]
        def onNode = topLevelChildren[i++]
        def peiNode = topLevelChildren[i++]
        def qcNode = topLevelChildren[i++]
        def skNode = topLevelChildren[i++]

        assertEquals(abNode["name"], Provinces.AB)
        assertEquals(bcNode["name"], Provinces.BC)
        assertEquals(mbNode["name"], Provinces.MB)
        assertEquals(nbNode["name"], Provinces.NB)
        assertEquals(nlNode["name"], Provinces.NL_DISPLAY)
        assertEquals(nsNode["name"], Provinces.NS)
        assertEquals(ntNode["name"], Provinces.NWT_DISPLAY)
        assertEquals(onNode["name"], Provinces.ON)
        assertEquals(peiNode["name"], Provinces.PEI_DISPLAY)
        assertEquals(qcNode["name"], Provinces.QC)
        assertEquals(skNode["name"], Provinces.SK)

        abNode["children"].each { child -> assertUnknownChild(child) }
        bcNode["children"].each { child -> assertUnknownChild(child) }
        mbNode["children"].each { child -> assertUnknownChild(child) }
        nbNode["children"].each { child -> assertUnknownChild(child) }
        nlNode["children"].each { child -> assertUnknownChild(child) }
        nsNode["children"].each { child -> assertUnknownChild(child) }
        onNode["children"].each { child -> assertUnknownChild(child) }
        // peiNode["children"].each { child -> assertUnknownChild(child) }
        qcNode["children"].each { child -> assertUnknownChild(child) }
        skNode["children"].each { child -> assertUnknownChild(child) }

        peiNode["children"].each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
            assertEquals(child["children"].size(), 1)

            def grandChild = child["children"][0]
            assertEquals(grandChild["name"], "Mozart")
            assertEquals(grandChild["party"], "Independent")
            assertEquals(grandChild["size"], 1000)
        }
    }

    def assertUnknownChild(def child) {
        assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
        assertEquals(child["children"].size(), 1)
        def grandChild = child["children"][0]

        assertEquals(grandChild["name"], "None identified (yet)")
        assertEquals(grandChild["party"], "unknown")
        assertEquals(grandChild["size"], 1000)
    }
}
