import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

public class WordCounter {

    // Method to find the count of each words passed into the args
    public static int[] counter(int searchArgsLength, String filename, String[] searchTerms) {
        try {
            int[] count = new int[searchArgsLength];
            File textFile = new File(filename);
            Scanner textScanner = new Scanner(textFile);

            while (textScanner.hasNextLine()) {
                String word = textScanner.next();
                for (int i = 0; i < searchArgsLength; i++) {
                    if (word.equals(searchTerms[i])) {
                        count[i] += 1;
                    }
                }
            }

            textScanner.close();

            return count;

        } catch (FileNotFoundException e) {
            System.out.println("File not found...." + filename);
            return new int[0];
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            int searchArgsLength = args.length - 1;
            int[] count = new int[searchArgsLength];
            String filename = args[0];
            String[] searchTerms = new String[searchArgsLength];

            for (int i = 0; i < searchArgsLength; i++) {
                searchTerms[i] = args[i + 1];
            }

            // condition for when args length is '1'
            if (searchArgsLength == 1) {
                System.out.println("First condition");
                count = counter(searchArgsLength, filename, searchTerms);
                System.out.println("The word '" + searchTerms[0] + "' appears " + count[0] + " times.");

            } else if (searchArgsLength > 1) {     // condition for "more than 1" args
                System.out.println("Second condition");

                count = counter(searchArgsLength, filename, searchTerms);

                System.out.println("|---------------|---------------|");
                System.out.println("|     WORD      |      COUNT    |");
                for (int i = 0; i < searchArgsLength; i++) {
                    System.out.println("|---------------|---------------|");
                    System.out.println("       " + searchTerms[i] + "             " + count[i] + "      ");
                }
                System.out.println("|---------------|---------------|");
            }
        } else {
            System.out.println("No input given in the CLI...");
        }
    }
}