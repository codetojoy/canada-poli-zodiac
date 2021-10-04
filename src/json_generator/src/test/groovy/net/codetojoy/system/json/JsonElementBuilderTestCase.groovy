package net.codetojoy.system.json

import net.codetojoy.system.*
import net.codetojoy.custom.Info

import groovy.json.*

import org.junit.*
import static org.junit.Assert.*

class JsonElementBuilderTestCase {
    def jsonElementBuilder = new JsonElementBuilder()

    @Test
    void testBuildWithElements_en() {
        def name = "Mozart"
        def zodiac = "Aquarius"
        def province = "Prince Edward Island"
        def riding = "Salzburg"
        def party = "Independent" 	// which party would Mozart join?
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
	def result = jsonElementBuilder.buildWithElements(infos, locale)

	// this is ridiculous, but YOLO:

	def jsonSlurper = new JsonSlurper()
	def resultMap = jsonSlurper.parseText(result)
	def topLevelChildren = resultMap["children"]

	assertEquals(resultMap["name"], "zodiac")
	assertEquals(topLevelChildren.size(), 4)

	def fireNode = topLevelChildren[0]
	def waterNode = topLevelChildren[1]
	def airNode = topLevelChildren[2]
	def earthNode = topLevelChildren[3]

	assertEquals(fireNode["name"], "Fire")
	assertEquals(waterNode["name"], "Water")
	assertEquals(airNode["name"], "Air")
	assertEquals(earthNode["name"], "Earth")

	def fireChildren = topLevelChildren[0]["children"]
	def waterChildren = topLevelChildren[1]["children"]
	def airChildren = topLevelChildren[2]["children"]
	def earthChildren = topLevelChildren[3]["children"]

	fireChildren.each { child ->
	    def sign = child["name"]
	    assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
            assertEquals(child["children"].size(), 1)
	    def grandChild = child["children"][0]

	    assertEquals(grandChild["name"], "None identified (yet)")
	    assertEquals(grandChild["party"], "unknown")
	    assertEquals(grandChild["size"], 1000)
	}

	waterChildren.each { child ->
	    def sign = child["name"]
	    assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
            assertEquals(child["children"].size(), 1)
	    def grandChild = child["children"][0]

	    assertEquals(grandChild["name"], "None identified (yet)")
	    assertEquals(grandChild["party"], "unknown")
	    assertEquals(grandChild["size"], 1000)
	}

	airChildren.each { child ->
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

	earthChildren.each { child ->
	    def sign = child["name"]
	    assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
            assertEquals(child["children"].size(), 1)
	    def grandChild = child["children"][0]

	    assertEquals(grandChild["name"], "None identified (yet)")
	    assertEquals(grandChild["party"], "unknown")
	    assertEquals(grandChild["size"], 1000)
	}
    }

    @Test
    void testBuildWithElements_fr() {
        def name = "Mozart"
        def zodiac = "Aquarius"
        def province = "Prince Edward Island"
        def riding = "Salzburg"
        def party = "Independent" 	// which party would Mozart join?
	def source = "Wikipedia"

        def info = new Info(name: name, zodiac: zodiac,
                            province: province, riding: riding,
                           party: party, source: source, lastName: name)
	def infoRows = []
	infoRows << info
	def infos = new Infos()
	infos.populate(infoRows)

	def locale = new Locale("fr")

	// test
	def result = jsonElementBuilder.buildWithElements(infos, locale)

	// this is ridiculous, but YOLO:

	def jsonSlurper = new JsonSlurper()
	def resultMap = jsonSlurper.parseText(result)
	def topLevelChildren = resultMap["children"]

	assertEquals(resultMap["name"], "zodiac")
	assertEquals(topLevelChildren.size(), 4)

	def fireNode = topLevelChildren[0]
	def waterNode = topLevelChildren[1]
	def airNode = topLevelChildren[2]
	def earthNode = topLevelChildren[3]

	assertEquals(fireNode["name"], locale.get("Fire"))
	assertEquals(waterNode["name"], locale.get("Water"))
	assertEquals(airNode["name"], locale.get("Air"))
	assertEquals(earthNode["name"], locale.get("Earth"))

	def fireChildren = topLevelChildren[0]["children"]
	def waterChildren = topLevelChildren[1]["children"]
	def airChildren = topLevelChildren[2]["children"]
	def earthChildren = topLevelChildren[3]["children"]

	fireChildren.each { child ->
	    def sign = child["name"]
	    assertTrue(Signs.DISPLAY_SIGNS.contains(locale.getInverse(sign)))
            assertEquals(child["children"].size(), 1)
	    def grandChild = child["children"][0]

	    assertEquals(grandChild["name"], "None identified (yet)")
	    assertEquals(grandChild["party"], "unknown")
	    assertEquals(grandChild["size"], 1000)
	}

	waterChildren.each { child ->
	    def sign = child["name"]
	    assertTrue(Signs.DISPLAY_SIGNS.contains(locale.getInverse(sign)))
            assertEquals(child["children"].size(), 1)
	    def grandChild = child["children"][0]

	    assertEquals(grandChild["name"], "None identified (yet)")
	    assertEquals(grandChild["party"], "unknown")
	    assertEquals(grandChild["size"], 1000)
	}

	airChildren.each { child ->
	    def sign = child["name"]
	    assertTrue(Signs.DISPLAY_SIGNS.contains(locale.getInverse(sign)))
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

	earthChildren.each { child ->
	    def sign = child["name"]
	    assertTrue(Signs.DISPLAY_SIGNS.contains(locale.getInverse(sign)))
            assertEquals(child["children"].size(), 1)
	    def grandChild = child["children"][0]

	    assertEquals(grandChild["name"], "None identified (yet)")
	    assertEquals(grandChild["party"], "unknown")
	    assertEquals(grandChild["size"], 1000)
	}
    }
}
