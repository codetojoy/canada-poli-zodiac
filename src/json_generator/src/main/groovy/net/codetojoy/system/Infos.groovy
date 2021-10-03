
 package net.codetojoy.system

 class Infos {
    def infos
    def infoMap = [:].withDefault { key -> [] }

    def populate(def infos) {
        this.infos = infos
        infos.each { info ->
            infoMap[info.province] << info
            if (info.zodiac.isEmpty()) {
                infoMap[Signs.UNKNOWN_DATA_SIGN] << info
            } else {
                infoMap[info.zodiac] << info
            }

            Signs.ELEMENTS.each { element ->
                if (new Signs().isSignInElement(info.zodiac, element)) {
                        infoMap[element] << info
                }
            }
        }
    }
 }