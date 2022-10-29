package fr.benco11.asynconf.exercices;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestEx1 {

    @Test
    void testMissionNameBySteps() {
        Ex1 exo = new Ex1();
        assertEquals("", exo.missionNameBySteps(Collections.emptyList()));
        assertEquals("J6T4", exo.missionNameBySteps(List.of("Jupiter", "Terre")));
        assertEquals("J6M3Ju3P5", exo.missionNameBySteps(List.of("Jupiter", "Mars", "Junon", "Pluton")));
        assertEquals("L3T4S5", exo.missionNameBySteps(List.of("Lune", "Terre", "Soleil")));
        assertEquals("T4M3Me5", exo.missionNameBySteps(List.of("Terre", "Mars", "Mercure")));
        assertEquals("P5M6T4Ma2C6", exo.missionNameBySteps(List.of("Pluton", "Mercure", "Terre", "Mars", "Calisto")));
    }
}
