
package net.codetojoy.system

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

    def buildChildren(def infos) {
        def children = []
        Signs.DISPLAY_SIGNS.each { sign ->
            def childMap = [:]
            childMap[NAME] = sign
            childMap[CHILDREN] = buildChildrenForSign(infos, sign)
            children << childMap
        }
        return children
    }

    def buildNormal(def infos) {
        def children = buildChildren(infos)
        def jsonMap = ["name" : "zodiac", "children" : children]
        def json = JsonOutput.toJson(jsonMap)
        return JsonOutput.prettyPrint(json)
    }

    def buildChildrenWithElement(def infos, def element) {
        def children = []
        def signs = new Signs()
        Signs.DISPLAY_SIGNS.each { sign ->
            if (signs.isSignInElement(sign, element)) {
                def childMap = [:]
                childMap[NAME] = sign
                childMap[CHILDREN] = buildChildrenForSign(infos, sign)
                children << childMap
            }
        }
        return children
    }

    def buildWithElements(def infos) {
        // this is not efficient, but it's a small list
        def fireChildren = buildChildrenWithElement(infos, "Fire")
        def waterChildren = buildChildrenWithElement(infos, "Water")
        def airChildren = buildChildrenWithElement(infos, "Air")
        def earthChildren = buildChildrenWithElement(infos, "Earth")

        def jsonMap = [
            "name" : "zodiac", "children" : [
                ["name": "Fire", "children" : fireChildren],
                ["name": "Water", "children" : waterChildren],
                ["name": "Air", "children" : airChildren],
                ["name": "Earth", "children" : earthChildren],
            ]
        ]
        def json = JsonOutput.toJson(jsonMap)
        return JsonOutput.prettyPrint(json)
    }

    // by province

    def getSizeForProvince(def infos, def province) {
        def result = DEFAULT_SIZE
        def count = infos.findAll{it.province == province}.size()
        result = result / count
        return result
    }

    def buildChildrenForProvince(def infos, def province, def sign) {
        def children = infos.findResults { info ->
            validate(info)
            def displaySign = new Signs().getDisplaySign(info.zodiac)
            if (info.province == province && sign == displaySign) {
                def person = [:]
                person[NAME] = info.name
                person[PARTY] = info.party
                person[SIZE] = getSizeForProvince(infos, province)
                return person
            } else {
                return null
            }
        }
        return children
    }

    def buildChildrenWithProvince(def infos, def province) {
        def children = []
        def provinces = new Provinces()
        Signs.DISPLAY_SIGNS.each { sign ->
            if (sign != Signs.UNKNOWN_DISPLAY_SIGN) {
                def childrenForSign = buildChildrenForProvince(infos, province, sign)
                if (!childrenForSign.isEmpty()) {
                    def childMap = [:]
                    childMap[NAME] = sign
                    childMap[CHILDREN] = childrenForSign
                    children << childMap
                }
            }
        }
        return children
    }

    def buildWithProvinces(def infos) {
        // this is not efficient, but it's a small list
        def abChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.AB }, Provinces.AB)
        def bcChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.BC }, Provinces.BC)
        def mbChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.MB }, Provinces.MB)
        def nbChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.NB }, Provinces.NB)
        def nlChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.NL }, Provinces.NL)
        def nsChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.NS }, Provinces.NS)
        def onChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.ON }, Provinces.ON)

        def nuChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.NU }, Provinces.NU)
        assert nuChildren.isEmpty()

        def ntChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.NT }, Provinces.NT)
        def peiChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.PEI }, Provinces.PEI)
        def qcChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.QC }, Provinces.QC)
        def skChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.SK }, Provinces.SK)

        def ytChildren = buildChildrenWithProvince(infos.findAll { it.province == Provinces.YT }, Provinces.YT)
        assert ytChildren.isEmpty()

        def jsonMap = [
            "name" : "zodiac", "children" : [
                ["name": Provinces.AB, "children" : abChildren],
                ["name": Provinces.BC, "children" : bcChildren],
                ["name": Provinces.MB, "children" : mbChildren],
                ["name": Provinces.NB, "children" : nbChildren],
                ["name": Provinces.NL_DISPLAY, "children" : nlChildren],
                ["name": Provinces.NS, "children" : nsChildren],
                // ["name": Provinces.NU, "children" : nuChildren],
                ["name": Provinces.NWT_DISPLAY, "children" : ntChildren],
                ["name": Provinces.ON, "children" : onChildren],
                ["name": Provinces.PEI_DISPLAY, "children" : peiChildren],
                ["name": Provinces.QC, "children" : qcChildren],
                ["name": Provinces.SK, "children" : skChildren],
                // ["name": Provinces.YT, "children" : ytChildren],
            ]
        ]
        def json = JsonOutput.toJson(jsonMap)
        return JsonOutput.prettyPrint(json)
    }
}
