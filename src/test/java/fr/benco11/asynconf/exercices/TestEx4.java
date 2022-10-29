package fr.benco11.asynconf.exercices;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestEx4 {
    private static final Ex4.Planet SILOPP = new Ex4.Planet("Silopp", 14924,  194532, 90248452);
    private static final Ex4.Planet TIERRA = new Ex4.Planet("Tierra",  939, 59736, 149597);
    private static final Ex4.Planet KERBOS = new Ex4.Planet("Kerbos", 562432,  25000, 145225685);
    private static final Ex4.Planet ASTRION = new Ex4.Planet("Astrion", 152000,  2194, 149302);
    private static final Ex4.Planet MADRONE = new Ex4.Planet("Madrone", 12,  264521, 56565);
    private static final Ex4.Planet VALENUS = new Ex4.Planet("Valenus", 290450, 195293, 20948593455L);

    @Test
    void testDecodeBase64() {
        Ex4 exo = new Ex4();
        assertEquals("olright $ ça va ?", exo.decodeBase64("b2xyaWdodCAkIMOnYSB2YSA/"));
        assertEquals("""
                           [
                            {
                                "name": "Silopp",
                                "size": 14924,
                                "distanceToStar": 90248452,
                                "mass": 194532
                            },
                            {
                                "name": "Astrion",
                                "size": 152000,
                                "distanceToStar": 149302,
                                "mass": 2194
                            },
                            {
                                "name": "Valenus",
                                "size": 290450,
                                "distanceToStar": 20948593455,
                                "mass": 195293
                            }
                        ]
                        """.trim(), exo.decodeBase64("WwogICAgewogICAgICAgICJuYW1lIjogIlNpbG9wcCIsCiAgICAgICAgInNpemUiOiAxNDkyNCwKICAgICAgICAiZGlzdGFuY2VUb1N0YXIiOiA5MDI0ODQ1MiwKICAgICAgICAibWFzcyI6IDE5NDUzMgogICAgfSwKICAgIHsKICAgICAgICAibmFtZSI6ICJBc3RyaW9uIiwKICAgICAgICAic2l6ZSI6IDE1MjAwMCwKICAgICAgICAiZGlzdGFuY2VUb1N0YXIiOiAxNDkzMDIsCiAgICAgICAgIm1hc3MiOiAyMTk0CiAgICB9LAogICAgewogICAgICAgICJuYW1lIjogIlZhbGVudXMiLAogICAgICAgICJzaXplIjogMjkwNDUwLAogICAgICAgICJkaXN0YW5jZVRvU3RhciI6IDIwOTQ4NTkzNDU1LAogICAgICAgICJtYXNzIjogMTk1MjkzCiAgICB9Cl0="));
        assertEquals("\"C'est incroyable non ? Tu es vraiment en train de lire ça ?\"", exo.decodeBase64("IkMnZXN0IGluY3JveWFibGUgbm9uID8gVHUgZXMgdnJhaW1lbnQgZW4gdHJhaW4gZGUgbGlyZSDDp2EgPyI="));
    }

    @Test
    void testPlanetsListFromJson() {
        Ex4 exo = new Ex4();
        assertEquals(List.of(SILOPP, TIERRA, KERBOS, ASTRION, MADRONE, VALENUS), exo.planetsListFromJson("""
                                                                                                         [
                                                                                                             {
                                                                                                                 "name": "Silopp",
                                                                                                                 "size": 14924,
                                                                                                                 "distanceToStar": 90248452,
                                                                                                                 "mass": 194532
                                                                                                             },
                                                                                                           {
                                                                                                             "name": "Tierra",
                                                                                                             "size": 939,
                                                                                                             "distanceToStar": 149597,
                                                                                                             "mass": 59736
                                                                                                           },
                                                                                                           {
                                                                                                             "name": "Kerbos",
                                                                                                             "size": 562432,
                                                                                                             "distanceToStar": 145225685,
                                                                                                             "mass": 25000
                                                                                                           },
                                                                                                           {
                                                                                                                 "name": "Astrion",
                                                                                                                 "size": 152000,
                                                                                                                 "distanceToStar": 149302,
                                                                                                                 "mass": 2194
                                                                                                             },
                                                                                                           {"name": "Madrone",
                                                                                                           "size":12,
                                                                                                           "distanceToStar": 56565,
                                                                                                           "mass":264521},
                                                                                                           {
                                                                                                                 "name": "Valenus",
                                                                                                                 "size": 290450,
                                                                                                                 "distanceToStar": 20948593455,
                                                                                                                 "mass": 195293
                                                                                                             }
                                                                                                         ]
                                                                                                         """));

        assertEquals(List.of(SILOPP, ASTRION, VALENUS), exo.planetsListFromJson("""
                                                                                                         [
                                                                                                             {
                                                                                                                 "name": "Silopp",
                                                                                                                 "size": 14924,
                                                                                                                 "distanceToStar": 90248452,
                                                                                                                 "mass": 194532
                                                                                                             },
                                                                                                             {
                                                                                                                 "name": "Astrion",
                                                                                                                 "size": 152000,
                                                                                                                 "distanceToStar": 149302,
                                                                                                                 "mass": 2194
                                                                                                             },
                                                                                                             {
                                                                                                                 "name": "Valenus",
                                                                                                                 "size": 290450,
                                                                                                                 "distanceToStar": 20948593455,
                                                                                                                 "mass": 195293
                                                                                                             }
                                                                                                         ]
                                                                                                         """));
        assertNotEquals(List.of(SILOPP, ASTRION), exo.planetsListFromJson("""
                                                                          [
                                                                                                             {
                                                                                                                 "name": "Astrion",
                                                                                                                 "size": 152000,
                                                                                                                 "distanceToStar": 149302,
                                                                                                                 "mass": 2194
                                                                                                             },
                                                                                                             {
                                                                                                                 "name": "Silopp",
                                                                                                                 "size": 14924,
                                                                                                                 "distanceToStar": 90248452,
                                                                                                                 "mass": 194532
                                                                                                             }

                                                                                                         ]
                                                                          """));
    }

    @Test
    void testQuickSort() {
        Ex4 exo = new Ex4();
        assertEquals(List.of(ASTRION, SILOPP, VALENUS), exo.quickSort(new ArrayList<>(List.of(SILOPP, ASTRION, VALENUS))));
        assertEquals(List.of(MADRONE, ASTRION, TIERRA, SILOPP, KERBOS, VALENUS), exo.quickSort(new ArrayList<>(List.of(KERBOS, ASTRION, MADRONE, SILOPP, VALENUS, TIERRA))));
    }

    @Test
    void testPlanetToString() {
        assertEquals("""
                     Nom : Silopp
                     Taille : 14924km
                     Masse : 194532 tonnes
                     Distance à l'étoile : 90248452km
                     """.trim(), SILOPP.toString().trim());
        assertEquals("""
                     Nom : Valenus
                     Taille : 290450km
                     Masse : 195293 tonnes
                     Distance à l'étoile : 20948593455km
                     """.trim(), VALENUS.toString().trim());
        assertEquals("""
                     Nom : Astrion
                     Taille : 152000km
                     Masse : 2194 tonnes
                     Distance à l'étoile : 149302km
                     """.trim(), ASTRION.toString().trim());
    }
}
