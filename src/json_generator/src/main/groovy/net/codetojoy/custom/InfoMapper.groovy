
package net.codetojoy.custom

import net.codetojoy.utils.Utils
import net.codetojoy.system.Signs

// "Honorific Title","First Name","Last Name","Constituency","Province / Territory","Political Affiliation","Start Date","End Date","Zodiac","Source"
class InfoMapper {
    static final def INDEX_HONORIFIC = 0
    static final def INDEX_FIRST_NAME = 1
    static final def INDEX_LAST_NAME = 2
    static final def INDEX_RIDING = 3
    static final def INDEX_PROVINCE = 4
    static final def INDEX_PARTY = 5
    static final def INDEX_ZODIAC = 8
    static final def INDEX_SOURCE = 9
    /*
    static final def INDEX_NAME = 0
    static final def INDEX_ZODIAC = 1
    static final def INDEX_BIRTHDAY = 2
    static final def INDEX_RIDING = 3
    static final def INDEX_PARTY = 4
    */

    def utils = new Utils()

    def mapLine(def line) {
        def info = null

        try {
            def honorific = line.getAt(INDEX_HONORIFIC)
            def firstName = line.getAt(INDEX_FIRST_NAME)
            def lastName = line.getAt(INDEX_LAST_NAME)
            def name
            if (honorific.trim().isEmpty()) {
                name = "${firstName} ${lastName}"
            } else {
                name = "${honorific} ${firstName} ${lastName}"
            }
            def zodiac = line.getAt(INDEX_ZODIAC)
            def riding = line.getAt(INDEX_RIDING)
            def province = line.getAt(INDEX_PROVINCE)
            def party = line.getAt(INDEX_PARTY)
            def source = line.getAt(INDEX_SOURCE)

            if (zodiac) {
                info = new Info(name: name, zodiac: zodiac,
                                riding: riding, party: party, province: province, source: source)
            } else {
                info = new Info(name: name, zodiac: Signs.UNKNOWN_DATA_SIGN,
                                riding: riding, party: party, province: province, source: source)
            }
        } catch(Exception ex) {
            System.err.println("TRACER caught ex : ${ex.message}")
        }

        return info
    }
}

