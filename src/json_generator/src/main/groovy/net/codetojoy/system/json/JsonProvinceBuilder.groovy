
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

    def buildWithProvinces(def infos, def locale) {
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
                ["name": locale.get(Provinces.AB_DISPLAY), "children" : abChildren],
                ["name": locale.get(Provinces.BC_DISPLAY), "children" : bcChildren],
                ["name": locale.get(Provinces.MB_DISPLAY), "children" : mbChildren],
                ["name": locale.get(Provinces.NB_DISPLAY), "children" : nbChildren],
                ["name": locale.get(Provinces.NL_DISPLAY), "children" : nlChildren],
                ["name": locale.get(Provinces.NS_DISPLAY), "children" : nsChildren],
                // ["name": Provinces.NU, "children" : nuChildren],
                ["name": locale.get(Provinces.NWT_DISPLAY), "children" : ntChildren],
                ["name": locale.get(Provinces.ON_DISPLAY), "children" : onChildren],
                ["name": locale.get(Provinces.PEI_DISPLAY), "children" : peiChildren],
                ["name": locale.get(Provinces.QC_DISPLAY), "children" : qcChildren],
                ["name": locale.get(Provinces.SK_DISPLAY), "children" : skChildren],
                // ["name": Provinces.YT, "children" : ytChildren],
            ]
        ]
        def json = JsonOutput.toJson(jsonMap)
        return JsonOutput.prettyPrint(json)
    }
}
