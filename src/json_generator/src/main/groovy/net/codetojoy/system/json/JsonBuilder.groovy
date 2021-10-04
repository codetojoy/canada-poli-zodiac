
package net.codetojoy.system.json

import net.codetojoy.system.*

import groovy.json.*

/*
{
"name": "zodiac",
"children": [
{
  "name": "aries",
  "children": [
    { "name": "Person a-ABC", "size": 1000 },
    { "name": "Person a-DEF", "size": 1000 },
    { "name": "Person a-IJK", "size": 1000 },
    { "name": "Person a-XYZ", "size": 1000 }
  ]
},
*/

class JsonBuilder {
    static final def NAME = "name"
    static final def PARTY = "party"
    static final def SIZE = "size"
    static final def UNKNOWN_SIGN = "unknown"
    static final def CHILDREN = "children"
    static final def ZODIAC = "zodiac"

    static final def NONE = "None identified (yet)"
    static final def DEFAULT_SIZE = 1000

    def getSizeForSign(def infos, def sign) {
        def result = DEFAULT_SIZE
        def count = infos.findAll{ it.zodiac == sign}.size()
        result = result / count
        return result
    }

    def buildUnknown() {
        return ["$NAME": NONE, "$PARTY": UNKNOWN_SIGN, "$SIZE": DEFAULT_SIZE]
    }

    def validateSign(def info) {
        def sign = info.zodiac
        def source = info.source
        if (! sign.isEmpty()) {
            assert Signs.DATA_SIGNS.contains(sign.toLowerCase())
            if (sign != Signs.UNKNOWN_DATA_SIGN) {
                // System.err.println "INTERNAL ERROR: no source name: ${info.name} sign: ${sign}"
                assert Sources.SOURCES.contains(source)
            }
        }
    }

    def validateParty(def party) {
        assert Parties.PARTIES.contains(party)
    }

    def validateProvince(def party) {
        assert Provinces.PROVINCES.contains(party)
    }

    def validate(def info) {
        validateSign(info)
        validateParty(info.party)
        validateProvince(info.province)
    }

    def buildChildrenForSign(def infos, def displaySign) {
        def children = infos.findResults { info ->
            validate(info)
            def dataSign = info.zodiac
            def thisDisplaySign = new Signs().getDisplaySign(dataSign)
            if (thisDisplaySign == displaySign) {
                def person = [:]
                person[NAME] = info.name
                person[PARTY] = info.party
                person[SIZE] = getSizeForSign(infos, dataSign)
                return person
            } else {
                return null
            }
        }
        if (children.isEmpty()) {
            children << buildUnknown()
        }
        return children
    }

    def buildChildren(def infos, def locale) {
        def children = []
        Signs.DISPLAY_SIGNS.each { sign ->
            if (Signs.UNKNOWN_DISPLAY_SIGN != sign) {
                def childMap = [:]
                childMap[NAME] = locale.get(sign)
                childMap[CHILDREN] = buildChildrenForSign(infos, sign)
                children << childMap
            }
        }
        return children
    }

    def buildNormal(def infos, def locale) {
        def children = buildChildren(infos, locale)
        def jsonMap = ["name" : "zodiac", "children" : children]
        def json = JsonOutput.toJson(jsonMap)
        return JsonOutput.prettyPrint(json)
    }
}
