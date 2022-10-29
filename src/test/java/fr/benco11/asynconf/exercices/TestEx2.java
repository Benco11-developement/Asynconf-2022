package fr.benco11.asynconf.exercices;

import fr.benco11.asynconf.utils.InvalidInputException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestEx2 {

    @Test
    void testStarshipProperties() {
        Ex2 exo = new Ex2();
        assertEquals(Map.of("name", "Crystal", "speed", "20000km/h", "price", "400/km"), exo.starshipProperties("name=Crystal;speed=20000km/h;price=400/km"));
        assertEquals(Map.of("name", "Atmos", "speed", "2045km/h", "price", "23/km"), exo.starshipProperties("name=Atmos;speed=2045km/h;price=23/km"));
        assertEquals(Map.of("name", "CircleBurn", "speed", "178547km/h", "price", "3612/km"), exo.starshipProperties("name=CircleBurn;speed=178547km/h;price=3612/km"));
        assertEquals(Map.of("name", "SpaceDestroyer", "speed", "98928423km/h", "price", "9294/km"), exo.starshipProperties("name=SpaceDestroyer;speed=98928423km/h;price=9294/km"));
        assertEquals(Map.of("name", "Miam", "speed", "9km/h", "price", "4/km"), exo.starshipProperties("name=Miam;speed=9km/h;price=4/km"));
        assertThrows(InvalidInputException.class, () -> exo.starshipProperties(""));
    }
}
