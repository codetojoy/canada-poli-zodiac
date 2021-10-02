
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

    def checkParty(def party) {
        def GREEN_PARTY = "Green Party";
        def LIBERAL_PARTY = "Liberal";
        def NDP_PARTY = "NDP";
        def CONSERVATIVE_PARTY = "Conservative";
        def BLOC_QUEBECOIS_PARTY = "Bloc Québécois";
        def INDEPENDENT = "Independent";
        def parties = [GREEN_PARTY, LIBERAL_PARTY, NDP_PARTY, CONSERVATIVE_PARTY, BLOC_QUEBECOIS_PARTY, INDEPENDENT]
        if (!parties.contains(party)) {
            System.err.println "internal error on party : ${party}"
        }
    }

    def buildChildrenForSign(def infos, def displaySign) {
        def children = infos.findResults { info ->
            checkParty(info.party)
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
}
