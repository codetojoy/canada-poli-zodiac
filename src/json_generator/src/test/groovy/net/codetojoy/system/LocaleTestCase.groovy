
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
class LocaleTestCase {
    def input
    def expectedEn
    def expectedFr

    public LocaleTestCase(def input, def expectedFr) {
        this.input = input
        this.expectedFr = expectedFr
        this.expectedEn = input
    }

    @Parameters(name = "#{index}  __  input: {0}  __  expectedFr {1} ")
    public static Collection<Object[]> data() {
        def objects = new ArrayList<Object>()
        objects << new Object[]{"Alta.", "Alb."}
        objects << new Object[]{"B.C.", "C.-B."}
        objects << new Object[]{"Man.", "Man."}
        objects << new Object[]{"N.B.", "N.-B."}
        objects << new Object[]{"N.L.", "T.-N.-L."}
        objects << new Object[]{"N.W.T.", "T.N.-O."}
        objects << new Object[]{"Nvt.", "Nt"}
        objects << new Object[]{"N.S.", "N.-É."}
        objects << new Object[]{"Ont.", "Ont."}
        objects << new Object[]{"P.E.I.", "Î.-P.-É."}
        objects << new Object[]{"Que.", "Qc"}
        objects << new Object[]{"Sask.", "Sask."}
        objects << new Object[]{"Y.T.", "Yn"}

        objects << new Object[]{"Aries","Bélier"}
        objects << new Object[]{"Taurus","Taureau"}
        objects << new Object[]{"Gemini","Gémeaux"}
        objects << new Object[]{"Cancer","Cancer"}
        objects << new Object[]{"Leo","Lion"}
        objects << new Object[]{"Virgo","Vierge"}
        objects << new Object[]{"Libra","Balance"}
        objects << new Object[]{"Scorpio","Scorpion"}
        objects << new Object[]{"Sagittarius","Sagittaire"}
        objects << new Object[]{"Capricorn","Capricorne"}
        objects << new Object[]{"Aquarius","Verseau"}
        objects << new Object[]{"Pisces","Poissons"}

        objects << new Object[]{"Fire","Feu"}
        objects << new Object[]{"Water","Eau"}
        objects << new Object[]{"Earth","Terre"}
        objects << new Object[]{"Air","Air"}

        return objects
    }

    @Test
    void testFrenchLocale() {
        def frLocale = new Locale("fr")

        // test
        def resultFr = frLocale.get(input)
        def resultEn = frLocale.getInverse(resultFr)

        assertEquals(expectedEn, resultEn)
        assertEquals(expectedFr, resultFr)
    }
}
