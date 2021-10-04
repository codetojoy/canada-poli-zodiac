
package net.codetojoy.system.json

import net.codetojoy.system.*

import groovy.json.*

class JsonProvinceBuilder extends BaseBuilder {

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

    def buildChildrenWithProvince(def infos, def province, def doIncludeUnknown = false) {
        def children = []
        def provinces = new Provinces()
        println "TRACER cp 1 JPB hello ${province}"
        Signs.DISPLAY_SIGNS.each { sign ->
            if ((doIncludeUnknown) || sign != Signs.UNKNOWN_DISPLAY_SIGN) {
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
