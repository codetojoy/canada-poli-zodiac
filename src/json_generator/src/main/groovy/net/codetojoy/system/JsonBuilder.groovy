
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

    // by element

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
        def fireChildren = buildChildrenWithElement(infos.infoMap[Signs.FIRE], Signs.FIRE)
        def waterChildren = buildChildrenWithElement(infos.infoMap[Signs.WATER], Signs.WATER)
        def airChildren = buildChildrenWithElement(infos.infoMap[Signs.AIR], Signs.AIR)
        def earthChildren = buildChildrenWithElement(infos.infoMap[Signs.EARTH], Signs.EARTH)

        def jsonMap = [
            "name" : "zodiac", "children" : [
                ["name": Signs.FIRE, "children" : fireChildren],
                ["name": Signs.WATER, "children" : waterChildren],
                ["name": Signs.AIR, "children" : airChildren],
                ["name": Signs.EARTH, "children" : earthChildren],
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
        def abChildren = buildChildrenWithProvince(infos.infoMap[Provinces.AB], Provinces.AB)
        def bcChildren = buildChildrenWithProvince(infos.infoMap[Provinces.BC], Provinces.BC)
        def mbChildren = buildChildrenWithProvince(infos.infoMap[Provinces.MB], Provinces.MB)
        def nbChildren = buildChildrenWithProvince(infos.infoMap[Provinces.NB], Provinces.NB)
        def nlChildren = buildChildrenWithProvince(infos.infoMap[Provinces.NL], Provinces.NL)
        def nsChildren = buildChildrenWithProvince(infos.infoMap[Provinces.NS], Provinces.NS)
        def onChildren = buildChildrenWithProvince(infos.infoMap[Provinces.ON], Provinces.ON)

        def nuChildren = buildChildrenWithProvince(infos.infoMap[Provinces.NU], Provinces.NU)
        assert nuChildren.isEmpty()

        def ntChildren = buildChildrenWithProvince(infos.infoMap[Provinces.NT], Provinces.NT)
        def peiChildren = buildChildrenWithProvince(infos.infoMap[Provinces.PEI], Provinces.PEI)
        def qcChildren = buildChildrenWithProvince(infos.infoMap[Provinces.QC], Provinces.QC)
        def skChildren = buildChildrenWithProvince(infos.infoMap[Provinces.SK], Provinces.SK)

        def ytChildren = buildChildrenWithProvince(infos.infoMap[Provinces.YT], Provinces.YT)
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
