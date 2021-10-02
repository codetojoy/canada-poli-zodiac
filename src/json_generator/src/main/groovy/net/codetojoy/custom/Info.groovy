
package net.codetojoy.custom

import net.codetojoy.utils.Utils

class Info {
    def name = ""
    def zodiac = ""
    def riding = ""
    def party = ""
    def province = ""

    static def utils = new Utils()

    static String getHeader() {
        utils.buildList(["Name","Zodiac","Province","Riding","Party"])
    }

    String toString() {
        utils.buildList([name, zodiac, province, riding, party])
    }
}
