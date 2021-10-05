
package net.codetojoy.system.json

import net.codetojoy.system.*

import groovy.json.*

class JsonUnknownBuilder extends BaseBuilder {

    def buildChildrenWithProvince(def infos, def province, def locale) {
        return infos.findResults { info ->
            def person = [:]
            person[NAME] = info.name
            person[PARTY] = info.party
            person[SIZE] = getSizeForProvince(infos, province)
            return person
        }
    }

    def buildForUnknown(def infos, def locale) {
        def unknownRows = infos.infoMap[Signs.UNKNOWN_DATA_SIGN]
        def unknownInfos = new Infos()
        unknownInfos.populate(unknownRows)

        def abChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.AB], Provinces.AB, locale)
        def bcChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.BC], Provinces.BC, locale)
        def mbChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.MB], Provinces.MB, locale)
        def nbChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.NB], Provinces.NB, locale)
        def nlChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.NL], Provinces.NL, locale)
        def nsChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.NS], Provinces.NS, locale)
        def onChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.ON], Provinces.ON, locale)
        def nuChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.NU], Provinces.NU, locale)

        def ntChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.NT], Provinces.NT, locale)
        assert ntChildren.isEmpty()

        def peiChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.PEI], Provinces.PEI, locale)
        assert peiChildren.isEmpty()

        def qcChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.QC], Provinces.QC, locale)
        def skChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.SK], Provinces.SK, locale)

        def ytChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.YT], Provinces.YT, locale)

        def jsonMap = [
            "name" : "zodiac", "children" : [
                ["name": locale.get(Provinces.AB_DISPLAY), "children" : abChildren],
                ["name": locale.get(Provinces.BC_DISPLAY), "children" : bcChildren],
                ["name": locale.get(Provinces.MB_DISPLAY), "children" : mbChildren],
                ["name": locale.get(Provinces.NB_DISPLAY), "children" : nbChildren],
                ["name": locale.get(Provinces.NL_DISPLAY), "children" : nlChildren],
                ["name": locale.get(Provinces.NS_DISPLAY), "children" : nsChildren],
                ["name": locale.get(Provinces.NU_DISPLAY), "children" : nuChildren],
                // ["name": Provinces.NWT_DISPLAY, "children" : ntChildren],
                ["name": locale.get(Provinces.ON_DISPLAY), "children" : onChildren],
                // ["name": Provinces.PEI_DISPLAY, "children" : peiChildren],
                ["name": locale.get(Provinces.QC_DISPLAY), "children" : qcChildren],
                ["name": locale.get(Provinces.SK_DISPLAY), "children" : skChildren],
                ["name": locale.get(Provinces.YT_DISPLAY), "children" : ytChildren],
            ]
        ]
        def json = JsonOutput.toJson(jsonMap)
        return JsonOutput.prettyPrint(json)
    }
}
