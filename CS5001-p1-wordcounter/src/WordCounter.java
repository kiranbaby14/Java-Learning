import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class WordCounter {

    // Method to find the count of each words passed into the args
    public static int[] counter(int searchArgsLength, String filename, String[] searchTerms) {
        try {
            int[] count = new int[searchArgsLength];
            File textFile = new File(filename);
            Scanner textScanner = new Scanner(textFile);

            // Iterate through each line of the file
            while (textScanner.hasNext()) {
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
            System.out.println("File not found: " + filename);
            return null;
        }

    }

    public static void main(String[] args) {
        if (args.length > 1) {
            int searchArgsLength = args.length - 1;
            int[] count = new int[searchArgsLength];
            String filename = args[0];
            String[] searchTerms = new String[searchArgsLength];

            for (int i = 0; i < searchArgsLength; i++) {
                searchTerms[i] = args[i + 1];
            }

            // condition for when args length is '1'
            if (searchArgsLength == 1) {
                count = counter(searchArgsLength, filename, searchTerms);

                if (count != null)
                    System.out.println("The word '" + searchTerms[0] + "' appears " + count[0] + " times.");

            } else if (searchArgsLength > 1) {     // condition for "more than 1" args
                count = counter(searchArgsLength, filename, searchTerms);

                if (count != null) {
                    System.out.println("|---------------|---------------|");
                    System.out.println("|     WORD      |      COUNT    |");
                    for (int i = 0; i < searchArgsLength; i++) {
                        System.out.println("|---------------|---------------|");
                        System.out.println("       " + searchTerms[i] + "             " + count[i] + "      ");
                    }
                    System.out.println("|---------------|---------------|");
                }
            }
        } else if (args.length == 0) {
            System.out.println("No input given in the CLI...");
        } else if(args.length == 1) {
            System.out.println("No input arguments given in the CLI...");
        }
    }
}