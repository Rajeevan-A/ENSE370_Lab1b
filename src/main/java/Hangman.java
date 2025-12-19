import java.io.Console;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Hangman {

    public static final int MAX_BAD_GUESSES = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String secret = readSecretWord(scanner).toUpperCase();
        Set<Character> guessed = new HashSet<>();
        int bad = 0;

        System.out.println("=== Hangman ===");

        while (true) {
            System.out.println("Word: " + maskWord(secret, guessed));
            System.out.println("Bad guesses left: " + (MAX_BAD_GUESSES - bad));
            System.out.print("Enter a letter OR guess the whole word: ");

            String input = scanner.nextLine().trim().toUpperCase();
            if (input.isEmpty()) {
                System.out.println("Please enter something.\n");
                continue;
            }

            // Single-letter guess
            if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                char letter = input.charAt(0);

                GuessResult r = applyLetterGuess(secret, guessed, letter);

                if (r == GuessResult.ALREADY_GUESSED) {
                    System.out.println("You already guessed '" + letter + "'.\n");
                    continue;
                }

                if (r == GuessResult.CORRECT) {
                    System.out.println("Good guess!\n");
                    if (isSolved(secret, guessed)) {
                        System.out.println("You win! The word was: " + secret);
                        break;
                    }
                } else {
                    bad++;
                    System.out.println("Wrong letter! '" + letter + "' is not in the word.\n");
                    if (bad >= MAX_BAD_GUESSES) {
                        System.out.println("Game over! Too many bad guesses.");
                        System.out.println("The word was: " + secret);
                        break;
                    }
                }

            } else {
                // Whole-word guess
                if (input.equals(secret)) {
                    System.out.println("Correct! You win! The word was: " + secret);
                } else {
                    System.out.println("Wrong word guess. Game over!");
                    System.out.println("The word was: " + secret);
                }
                break;
            }
        }

        scanner.close();
    }

    public enum GuessResult { CORRECT, WRONG, ALREADY_GUESSED }

    // --- Methods below are unit-testable (for autograding) ---

    public static GuessResult applyLetterGuess(String secret, Set<Character> guessed, char letter) {
        letter = Character.toUpperCase(letter);
        if (guessed.contains(letter)) return GuessResult.ALREADY_GUESSED;
        guessed.add(letter);
        return (secret.toUpperCase().indexOf(letter) >= 0) ? GuessResult.CORRECT : GuessResult.WRONG;
    }

    public static String maskWord(String secret, Set<Character> guessed) {
        String s = secret.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            sb.append(guessed.contains(c) ? c : '*');
        }
        return sb.toString();
    }

    public static boolean isSolved(String secret, Set<Character> guessed) {
        return !maskWord(secret, guessed).contains("*");
    }

    public static boolean isValidWord(String word) {
        if (word == null || word.isEmpty()) return false;
        for (char c : word.toCharArray()) if (!Character.isLetter(c)) return false;
        return true;
    }

    private static String readSecretWord(Scanner scanner) {
        Console console = System.console();
        while (true) {
            String word;
            if (console != null) {
                char[] pwd = console.readPassword("Enter the secret word (hidden): ");
                word = (pwd == null) ? "" : new String(pwd).trim();
            } else {
                System.out.print("Enter the secret word (input visible): ");
                word = scanner.nextLine().trim();
            }
            if (isValidWord(word)) return word;
            System.out.println("Invalid secret word. Letters only, at least 1 character.\n");
        }
    }
}
