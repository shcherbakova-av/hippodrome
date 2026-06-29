import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test
    public void nullNameException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1,2.0));
    }

    @Test
    public void nullNameMessage() {
        try {
            new Horse(null, 1,2.0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t", "\t\n\t\n ", "\n\n\n"})
    public void blankNameExeption(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 2.3));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void negativeSpeedException() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Duo", -2, 2.9)
        );

        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @Test
    public void negativeDistanceException() {
        IllegalArgumentException e = assertThrows(
              IllegalArgumentException.class,
                () -> new Horse("Asha", 3, -1.3)
        );

        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    public void correctGetName() {
        String name = "Bella";
        Horse horse = new Horse(name, 3, 3.2);
        assertEquals(name, horse.getName());
    }

//    @Test
//    public void correctGetName2() throws NoSuchFieldException, IllegalAccessException {
//        String name = "Bella";
//        Horse horse = new Horse(name, 3, 3.2);
//        Field name2 = Horse.class.getDeclaredField("name");
//        name2.setAccessible(true);
//        String nameValue = (String) name2.get(horse);
//        assertEquals(name, nameValue);
//    }

    @Test
    public void correctGetSpeed() {
        double speed = 3.11;
        Horse horse = new Horse("Neo", speed, 4.6);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    public void correctGetDistance() {
        double distance = 3.1;
        Horse horse = new Horse("lala", 3, distance);
        assertEquals(distance, horse.getDistance());
    }

    @Test
    public void zeroGetWithoutDistance() {
        Horse horse = new Horse("lilo", 2);
        assertEquals(0, horse.getDistance());
    }

    @Test
    public void moveUserGetRandom() {
        try
            (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
                new Horse("lala", 3, 4.2).move();
                mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    public void movemoveChangesDistanceCorrectly(double randomValue) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class, CALLS_REAL_METHODS)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            double speed = 3.0;
            double distance = 4.0;
            double expectedDistance = distance + speed * randomValue;

            Horse horse = new Horse("Bella", speed, distance);

            horse.move();

            assertEquals(expectedDistance, horse.getDistance(), 0.0001);
        }
    }
}

