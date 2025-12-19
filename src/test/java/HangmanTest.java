import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HangmanTest {

    @Test
    void maskWord_masksUnknownLetters() {
        Set<Character> guessed = new HashSet<>();
        guessed.add('A');
        assertEquals("A**", Hangman.maskWord("ABC", guessed));
    }

    @Test
    void isValidWord_acceptsLettersOnly() {
        assertTrue(Hangman.isValidWord("SNOOPY"));
        assertFalse(Hangman.isValidWord("DOG1"));
        assertFalse(Hangman.isValidWord(""));
    }

    @Test
    void applyLetterGuess_tracksCorrectWrongAlready() {
        Set<Character> guessed = new HashSet<>();
        assertEquals(Hangman.GuessResult.CORRECT, Hangman.applyLetterGuess("HELLO", guessed, 'H'));
        assertEquals(Hangman.GuessResult.WRONG, Hangman.applyLetterGuess("HELLO", guessed, 'Z'));
        assertEquals(Hangman.GuessResult.ALREADY_GUESSED, Hangman.applyLetterGuess("HELLO", guessed, 'H'));
    }

    @Test
    void isSolved_trueWhenAllLettersGuessed() {
        Set<Character> guessed = new HashSet<>();
        for (char c : "DOG".toCharArray()) guessed.add(c);
        assertTrue(Hangman.isSolved("DOG", guessed));
    }
}
