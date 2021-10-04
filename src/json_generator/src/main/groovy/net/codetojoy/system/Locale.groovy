
package net.codetojoy.system

// TODO: use property files! this is brutal
class Locale {
    def mode
    def translationMap = [:]

    Locale(def mode) {
        this.mode = mode
        assert (this.mode == "en" || this.mode == "fr")

        translationMap["Aries"] = "Bélier"
        translationMap["Taurus"] = "Taureau"
        translationMap["Gemini"] = "Gémeaux"
        translationMap["Cancer"] = "Cancer"
        translationMap["Leo"] = "Lion"
        translationMap["Virgo"] = "Vierge"
        translationMap["Libra"] = "Balance"
        translationMap["Scorpio"] = "Scorpion"
        translationMap["Sagittarius"] = "Sagittaire"
        translationMap["Capricorn"] = "Capricorne"
        translationMap["Aquarius"] = "Verseau"
        translationMap["Pisces"] = "Poissons"

        translationMap["Fire"] = "Feu"
        translationMap["Water"] = "Eau"
        translationMap["Earth"] = "Terre"
        translationMap["Air"] = "Air"

        translationMap[Provinces.AB_DISPLAY] = "Alb."
        translationMap[Provinces.BC_DISPLAY] = "C.-B."
        translationMap[Provinces.MB_DISPLAY] = "Man."
        translationMap[Provinces.NB_DISPLAY] = "N.-B."
        translationMap[Provinces.NL_DISPLAY] = "T.-N.-L."
        translationMap[Provinces.NWT_DISPLAY] = "T.N.-O."
        translationMap[Provinces.NS_DISPLAY] = "N.-É."
        translationMap[Provinces.NU_DISPLAY] = "Nt"
        translationMap[Provinces.ON_DISPLAY] = "Ont."
        translationMap[Provinces.PEI_DISPLAY] = "Î.-P.-É."
        translationMap[Provinces.QC_DISPLAY] = "Qc"
        translationMap[Provinces.SK_DISPLAY] = "Sask."
        translationMap[Provinces.YT_DISPLAY] = "Yn"
    }

    def getInverse(def t) {
        def result = null
        def isDone = false
        translationMap.each { k, v ->
            if ((! isDone) && v == t) {
                result = k
                isDone = true
            }
        }
        return result
    }

    def get(def s) {
        return (mode == "en") ? s : translate(s)
    }

    def translate(def s) {
        def result = "FR: UNKNOWN"
        if (translationMap.keySet().contains(s)) {
            result = translationMap.get(s)
        }
        return result
    }
}