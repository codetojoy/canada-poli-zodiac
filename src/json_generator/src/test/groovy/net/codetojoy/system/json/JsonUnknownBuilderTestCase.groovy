package net.codetojoy.system.json

import net.codetojoy.system.*
import net.codetojoy.custom.Info

import groovy.json.*

import org.junit.*
import static org.junit.Assert.*

class JsonUnknownBuilderTestCase {
    def jsonUnknownBuilder = new JsonUnknownBuilder()

    @Test
    void testBuildForUnknown_en() {
        def name = "Mozart"
        def zodiac = "unknown"
        def province = "Ontario"
        def riding = "Salzburg"
        def party = "Independent" 	// which party would Mozart join?
	def source = ""

        def info = new Info(name: name, zodiac: zodiac,
                            province: province, riding: riding,
                           party: party, source: source, lastName: name)
	def infoRows = []
	infoRows << info
	def infos = new Infos()
	infos.populate(infoRows)

	def locale = new Locale("en")

	// test
	def result = jsonUnknownBuilder.buildForUnknown(infos, locale)

	println "TRACER JUB english"
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
	def nuNode = topLevelChildren[i++]
        // def ntNode = topLevelChildren[i++]
        def onNode = topLevelChildren[i++]
        // def peiNode = topLevelChildren[i++]
        def qcNode = topLevelChildren[i++]
        def skNode = topLevelChildren[i++]
	def ytNode = topLevelChildren[i++]

        assertEquals(abNode["name"], locale.get(Provinces.AB_DISPLAY))
        assertEquals(bcNode["name"], locale.get(Provinces.BC_DISPLAY))
        assertEquals(mbNode["name"], locale.get(Provinces.MB_DISPLAY))
        assertEquals(nbNode["name"], locale.get(Provinces.NB_DISPLAY))
        assertEquals(nlNode["name"], locale.get(Provinces.NL_DISPLAY))
        assertEquals(nsNode["name"], locale.get(Provinces.NS_DISPLAY))
        // assertEquals(ntNode["name"], Provinces.NWT_DISPLAY)
        assertEquals(nuNode["name"], locale.get(Provinces.NU_DISPLAY))
        assertEquals(onNode["name"], locale.get(Provinces.ON_DISPLAY))
        // assertEquals(peiNode["name"], Provinces.PEI_DISPLAY)
        assertEquals(qcNode["name"], locale.get(Provinces.QC_DISPLAY))
        assertEquals(ytNode["name"], locale.get(Provinces.YT_DISPLAY))

        assertTrue(abNode["children"].isEmpty())
        assertTrue(bcNode["children"].isEmpty())
        assertTrue(mbNode["children"].isEmpty())
        assertTrue(nbNode["children"].isEmpty())
        assertTrue(nlNode["children"].isEmpty())
        assertTrue(nsNode["children"].isEmpty())
        // assertTrue(ntNode["children"].isEmpty())
        assertTrue(nuNode["children"].isEmpty())
        // assertTrue(onNode["children"].isEmpty())
        // assertTrue(peiNode["children"].isEmpty())
        assertTrue(qcNode["children"].isEmpty())
        assertTrue(skNode["children"].isEmpty())
        assertTrue(ytNode["children"].isEmpty())

        onNode["children"].each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
            assertEquals(child["children"].size(), 1)

            def grandChild = child["children"][0]
            assertEquals(grandChild["name"], "Mozart")
            assertEquals(grandChild["party"], "Independent")
            assertEquals(grandChild["size"], 1000)
        }
    }
}
