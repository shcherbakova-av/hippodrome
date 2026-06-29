import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    @Test
    public void nullArgumentConstructorHippodrome() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void nullArgumentConstructorHippodromeMessage() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null)
        );

        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    public void constructorEmptyListThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.emptyList()));
    }

    @Test
    public void constructorEmptyListThrowsExceptionWithCorrectMessage() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(Collections.emptyList())
        );

        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorsesReturnsSameHorsesInSameOrder() {
        List<Horse> expectedHorses = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            expectedHorses.add(new Horse("Horse_" + i, i + 1, i + 0.5));
        }

        Hippodrome hippodrome = new Hippodrome(expectedHorses);

        List<Horse> actualHorses = hippodrome.getHorses();

        assertEquals(expectedHorses.size(), actualHorses.size());

        for (int i = 0; i < expectedHorses.size(); i++) {
            assertSame(expectedHorses.get(i), actualHorses.get(i));
        }
    }

    @Test
    public void moveCallsMoveForAllHorses() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    public void getWinnerReturnsHorseWithGreatestDistance() {
        Horse horse1 = new Horse("Bella", 3, 10.0);
        Horse horse2 = new Horse("Spirit", 4, 25.0);
        Horse horse3 = new Horse("Lucky", 5, 17.5);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));

        Horse winner = hippodrome.getWinner();

        assertSame(horse2, winner);
    }
}
