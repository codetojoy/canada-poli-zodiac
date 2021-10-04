
package net.codetojoy.system.json

import net.codetojoy.system.*
import net.codetojoy.custom.Info

import groovy.json.*

import org.junit.*
import static org.junit.Assert.*

class JsonBuilderTestCase {
    def jsonBuilder = new JsonBuilder()

    @Test
    void testBuildNormal_en() {
        def name = "Mozart"
        def zodiac = "Aquarius"
        def province = "Prince Edward Island"
        def riding = "Salzburg"
        def party = "Independent" 	// which party would Mozart join?
	def source = "Wikipedia"

        def info = new Info(name: name, zodiac: zodiac,
                            province: province, riding: riding,
                           party: party, source: source, lastName: name)
	def infos = []
	infos << info

	def locale = new Locale("en")

	// test
	def result = jsonBuilder.buildNormal(infos, locale)

	// this is ridiculous, but YOLO:

	def jsonSlurper = new JsonSlurper()
	def resultMap = jsonSlurper.parseText(result)
	def topLevelChildren = resultMap["children"]

	assertEquals(resultMap["name"], "zodiac")
	assertEquals(topLevelChildren.size(), 12)

	topLevelChildren.each { child ->
	    def sign = child["name"]
	    assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
            assertEquals(child["children"].size(), 1)
	    def grandChild = child["children"][0]

            if (sign == "Aquarius") {
		assertEquals(grandChild["name"], "Mozart")
		assertEquals(grandChild["party"], "Independent")
		assertEquals(grandChild["size"], 1000)
	    } else {
		assertEquals(grandChild["name"], "None identified (yet)")
		assertEquals(grandChild["party"], "unknown")
		assertEquals(grandChild["size"], 1000)
	    }
	}
    }

    @Test
    void testBuildNormal_fr() {
        def name = "Mozart"
        def zodiac = "Aquarius"
        def province = "Prince Edward Island"
        def riding = "Salzburg"
        def party = "Independent" 	// which party would Mozart join?
	def source = "Wikipedia"

        def info = new Info(name: name, zodiac: zodiac,
                            province: province, riding: riding,
                           party: party, source: source, lastName: name)
	def infos = []
	infos << info

	def locale = new Locale("fr")

	// test
	def result = jsonBuilder.buildNormal(infos, locale)

	// this is ridiculous, but YOLO:

	def jsonSlurper = new JsonSlurper()
	def resultMap = jsonSlurper.parseText(result)
	def topLevelChildren = resultMap["children"]

	assertEquals(resultMap["name"], "zodiac")
	assertEquals(topLevelChildren.size(), 12)

	topLevelChildren.each { child ->
	    def sign = child["name"]
            assertEquals(child["children"].size(), 1)
	    def grandChild = child["children"][0]

            if (sign == locale.get("Aquarius")) {
		assertEquals(grandChild["name"], "Mozart")
		assertEquals(grandChild["party"], "Independent")
		assertEquals(grandChild["size"], 1000)
	    } else {
		assertEquals(grandChild["name"], "None identified (yet)")
		assertEquals(grandChild["party"], "unknown")
		assertEquals(grandChild["size"], 1000)
	    }
	}
    }
}
