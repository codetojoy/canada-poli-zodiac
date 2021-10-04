
package net.codetojoy.system.json

import net.codetojoy.system.*

import groovy.json.*

class JsonElementBuilder extends BaseBuilder {

    def buildChildrenWithElement(def infos, def element, def locale) {
        def children = []
        def signs = new Signs()
        Signs.DISPLAY_SIGNS.each { sign ->
            if (signs.isSignInElement(sign, element)) {
                def childMap = [:]
                childMap[NAME] = locale.get(sign)
                childMap[CHILDREN] = buildChildrenForSign(infos, sign)
                children << childMap
            }
        }
        return children
    }

    def buildWithElements(def infos, def locale) {
        def fireChildren = buildChildrenWithElement(infos.infoMap[Signs.FIRE], Signs.FIRE, locale)
        def waterChildren = buildChildrenWithElement(infos.infoMap[Signs.WATER], Signs.WATER, locale)
        def airChildren = buildChildrenWithElement(infos.infoMap[Signs.AIR], Signs.AIR, locale)
        def earthChildren = buildChildrenWithElement(infos.infoMap[Signs.EARTH], Signs.EARTH, locale)

        def jsonMap = [
            "name" : "zodiac", "children" : [
                ["name": locale.get(Signs.FIRE), "children" : fireChildren],
                ["name": locale.get(Signs.WATER), "children" : waterChildren],
                ["name": locale.get(Signs.AIR), "children" : airChildren],
                ["name": locale.get(Signs.EARTH), "children" : earthChildren],
            ]
        ]
        def json = JsonOutput.toJson(jsonMap)
        return JsonOutput.prettyPrint(json)
    }
}
