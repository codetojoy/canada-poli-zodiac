
package net.codetojoy.system.json

import net.codetojoy.system.*

import groovy.json.*

class JsonUnknownBuilder extends JsonProvinceBuilder {

    def buildForUnknown(def infos, def locale) {
        def unknownRows = infos.infoMap[Signs.UNKNOWN_DATA_SIGN]
        def unknownInfos = new Infos()
        unknownInfos.populate(unknownRows)

        def abChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.AB], Provinces.AB, true)
        def bcChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.BC], Provinces.BC, true)
        def mbChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.MB], Provinces.MB, true)
        def nbChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.NB], Provinces.NB, true)
        def nlChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.NL], Provinces.NL, true)
        def nsChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.NS], Provinces.NS, true)
        def onChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.ON], Provinces.ON, true)
        def nuChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.NU], Provinces.NU, true)

        def ntChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.NT], Provinces.NT, true)
        assert ntChildren.isEmpty()

        def peiChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.PEI], Provinces.PEI, true)
        assert peiChildren.isEmpty()

        def qcChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.QC], Provinces.QC, true)
        def skChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.SK], Provinces.SK, true)

        def ytChildren = buildChildrenWithProvince(unknownInfos.infoMap[Provinces.YT], Provinces.YT, true)

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
