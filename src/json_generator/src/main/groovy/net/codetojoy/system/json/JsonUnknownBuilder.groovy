
package net.codetojoy.system.json

import net.codetojoy.system.*

import groovy.json.*

class JsonUnknownBuilder extends JsonProvinceBuilder {

    def buildForUnknown(def infos) {
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
                ["name": Provinces.AB, "children" : abChildren],
                ["name": Provinces.BC, "children" : bcChildren],
                ["name": Provinces.MB, "children" : mbChildren],
                ["name": Provinces.NB, "children" : nbChildren],
                ["name": Provinces.NL_DISPLAY, "children" : nlChildren],
                ["name": Provinces.NS, "children" : nsChildren],
                ["name": Provinces.NU, "children" : nuChildren],
                // ["name": Provinces.NWT_DISPLAY, "children" : ntChildren],
                ["name": Provinces.ON, "children" : onChildren],
                // ["name": Provinces.PEI_DISPLAY, "children" : peiChildren],
                ["name": Provinces.QC, "children" : qcChildren],
                ["name": Provinces.SK, "children" : skChildren],
                ["name": Provinces.YT, "children" : ytChildren],
            ]
        ]
        def json = JsonOutput.toJson(jsonMap)
        return JsonOutput.prettyPrint(json)
    }
}
