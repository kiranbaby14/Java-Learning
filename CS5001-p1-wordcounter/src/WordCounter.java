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
                // Replace all special characters in the string with " "
                word = word.replaceAll("[^A-Za-z0-9]"," ");

                for (int i = 0; i < searchArgsLength; i++) {
                    if (word.equals(searchTerms[i])) {
                        count[i] += 1;
                    } else if(word.contains(searchTerms[i]) && word.contains(" ")) {
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
        if (args.length > 0) {
            int searchArgsLength = args.length - 1;
            int[] count = new int[searchArgsLength];
            int total = 0;
            String filename = args[0];
            String[] searchTerms = new String[searchArgsLength];

            for (int i = 0; i < searchArgsLength; i++) {
                searchTerms[i] = args[i + 1];
            }

            // Condition for when args length is '1'
            if (searchArgsLength == 1) {
                count = counter(searchArgsLength, filename, searchTerms);

                if (count != null)
                    System.out.println("The word '" + searchTerms[0] + "' appears " + count[0] + " times.");

            } else if (searchArgsLength > 1) {     // condition for "more than 1" args
                count = counter(searchArgsLength, filename, searchTerms);

                // Total count
                for(int val: count){
                    total += val;
                }

                if (count != null) {
                    System.out.println("|---------------|---------------|");
                    System.out.println("|     WORD      |      COUNT    |");
                    System.out.println("|---------------|---------------|");
                    for (int i = 0; i < searchArgsLength; i++) {
                        System.out.println("|     " + searchTerms[i] + "      |      " + count[i] + "    |");
                    }
                    System.out.println("|---------------|---------------|");
                    System.out.println("|    Total      |      " + total + "      |");
                    System.out.println("|---------------|---------------|");
                }
            }
        } else {
            System.out.println("Usage: java WordCounter <filename> <searchTerm>");
        }
    }
}