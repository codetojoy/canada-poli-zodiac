
package net.codetojoy.system

import org.junit.*
import static org.junit.Assert.*

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.CoreMatchers.is;

// These are really integration tests.
// I found an error and now want to be exhaustive.
// TODO: use parameterized tests

@RunWith(value = Parameterized.class)
class ProvincesTestCase {
    def dataProvince
    def displayProvinceEn
    def displayProvinceFr

    public ProvincesTestCase(def dataProvince, def displayProvinceEn, def displayProvinceFr) {
        this.dataProvince = dataProvince
        this.displayProvinceEn = displayProvinceEn
        this.displayProvinceFr = displayProvinceFr
    }

    @Parameters(name = "#{index}  __  dataProvince: {0}  __  displayProvinceEn {1}  __  displayProvinceFr: {2}")
    public static Collection<Object[]> data() {
        def objects = new ArrayList<Object>()
        objects << new Object[]{"Alberta", "Alta.", "Alb."}
        objects << new Object[]{"British Columbia", "B.C.", "C.-B."}
        objects << new Object[]{"Manitoba", "Man.", "Man."}
        objects << new Object[]{"New Brunswick", "N.B.", "N.-B."}
        objects << new Object[]{"Newfoundland and Labrador", "N.L.", "T.-N.-L."}
        objects << new Object[]{"Northwest Territories", "N.W.T.", "T.N.-O."}
        objects << new Object[]{"Nunavut", "Nvt.", "Nt"}
        objects << new Object[]{"Nova Scotia", "N.S.", "N.-É."}
        objects << new Object[]{"Ontario", "Ont.", "Ont."}
        objects << new Object[]{"Prince Edward Island", "P.E.I.", "Î.-P.-É."}
        objects << new Object[]{"Quebec", "Que.", "Qc"}
        objects << new Object[]{"Saskatchewan", "Sask.", "Sask."}
        objects << new Object[]{"Yukon", "Y.T.", "Yn"}
        return objects
    }

    @Test
    void testGetDisplayProvinceAndLocale() {
        def provinces = new Provinces()
        def enLocale = new Locale("en")
        def frLocale = new Locale("fr")

        // test
        def result = provinces.getDisplayProvince(dataProvince)
        def resultEn = enLocale.get(result)
        def resultFr = frLocale.get(result)

        assertEquals(displayProvinceEn, result)
        assertEquals(displayProvinceEn, resultEn)
        assertEquals(displayProvinceFr, resultFr)
    }
}
