import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

public class WordCounter {
    public static void main(String[] args) {
        if (args.length > 0) {
            String filename = args[0];
            String searchTerm = args[1];
            int searchArgsLength = args.length - 1;

            // for 1 arg
            if (searchArgsLength == 1) {
                System.out.println("First condition");
                try {
                    int count = 0;
                    File textFile = new File(filename);
                    Scanner textScanner = new Scanner(textFile);

                    while (textScanner.hasNextLine()) {
                        if (textScanner.next().equals(searchTerm)) {
                            count++;
                        }
                    }

                    System.out.println("The word '" + searchTerm + "' appears " + count + " times.");

                    textScanner.close();

                } catch (FileNotFoundException e) {
                    System.out.println("File not found...." + filename);
                }

            } else if (searchArgsLength > 1) {     // for more than 1 args
                System.out.println("Second condition");

                String[] searchTerms = new String[searchArgsLength];

                for (int i = 0; i < searchArgsLength; i++) {
                    searchTerms[i] = args[i + 1];
                }

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

                    System.out.println("|---------------|---------------|");
                    System.out.println("|     WORD      |      COUNT    |");
                    for(int i = 0; i < searchArgsLength; i++) {
                        System.out.println("|---------------|---------------|");
                        System.out.println("       " + searchTerms[i] + "             " + count[i] + "      ");
                    }
                    System.out.println("|---------------|---------------|");

                } catch (FileNotFoundException e) {
                    System.out.println("File not found...." + filename);
                }

            }
        } else {
            System.out.println("No input given in the CLI...");
        }
    }
}