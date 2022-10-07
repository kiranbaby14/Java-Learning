import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordCounterTest {

    @Test
    void arrayCountShouldReturnHundred() {
        WordCounter wordCounterObj =new WordCounter();
        assertEquals(new int[]{100}, wordCounterObj.counter(1, "a-tale-of-two-cities.txt", new String[]{"Charles"}));
    }

    @Test
    void maxElementInArrayShouldReturnLongest() {
        WordCounter wordCounterObj = new WordCounter();
        assertEquals("Longest", wordCounterObj.maxElementInArray(
                new ArrayList<String>(List.of(
                        "Word", "Longest", "Small"
                ))));
    }

}