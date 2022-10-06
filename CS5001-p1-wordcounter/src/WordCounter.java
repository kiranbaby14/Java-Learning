import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * WordCounter
 * Program to get the "count of words" given as input from an already existing file.
 *
 * @author: Kiran Baby, 220015821
 */

public class WordCounter {

    /**
     * Method to find the count of each words passed into the args.
     *
     * @param searchArgsLength the number of arguments to be searched for
     * @param filename location of the file
     * @param searchTerms an array containing the arguments to be searched for
     * @return the count of all arguments
     */
    public static int[] counter(int searchArgsLength, String filename, String[] searchTerms) {
        try {
            int[] count = new int[searchArgsLength];
            File textFile = new File(filename);
            Scanner textScanner = new Scanner(textFile);

            // Iterate through each line of the file
            while (textScanner.hasNext()) {
                String word = textScanner.next();
                // Remove special characters in the string and store the o/p to an array
                String[] arrOfStr = word.split("[^A-Za-z0-9]");

                for (int i = 0; i < searchArgsLength; i++) {
                    for (String str : arrOfStr) {
                        if (str.equals(searchTerms[i])) {
                            count[i] += 1;
                        }
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

    /**
     * Table Outline.
     *
     * @param leftSize the space size to be given in the left column of the table
     * @param rightSize the space size to be given in the right column of the table
     */
    public static void tabeleOutline(int leftSize, int rightSize) {
        // Pattern
        System.out.print("|");
        for (int i = 0; i < leftSize + 2; i++) {
            System.out.print('-');
        }
        System.out.print('|');
        for (int i = 0; i < rightSize + 2; i++) {
            System.out.print('-');
        }
        System.out.println('|');
    }

    /**
     * Returns largest element in the arraylist.
     *
     * @param arr an arraylist
     * @return the index of the largest elemrnt in the arraylist
     */
    public static String maxElementInArray(ArrayList<String> arr) {
        int index = 0;
        int elementLength = arr.get(0).length();
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).length() > elementLength) {
                index = i;
                elementLength = arr.get(i).length();
            }
        }
        return arr.get(index);
    }

    /**
     * Main function.
     *
     * @param args the arguments taken from the CLI
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            int totalCount = 0;
            int searchArgsLength = args.length - 1; // 1 is subtracted because the first argument is the filename
            int[] count = new int[searchArgsLength]; //an array to keep track of the argument counts
            String filename = args[0];
            String[] searchTerms = new String[searchArgsLength]; // stores all the searchterms into an array

            for (int i = 0; i < searchArgsLength; i++) {
                searchTerms[i] = args[i + 1]; // "i + 1" as the first argument is the filename
            }

            count = counter(searchArgsLength, filename, searchTerms); //calls counter function

            if (searchArgsLength == 1) { // Executed when "only 1" argument is given
                if (count != null) {
                    if (count[0] == 1) {
                        System.out.println("The word '" + searchTerms[0] + "' appears " + count[0] + " time.");
                    } else {
                        System.out.println("The word '" + searchTerms[0] + "' appears " + count[0] + " times.");
                    }
                }

            } else if (searchArgsLength > 1) {     // Executed when "more than 1" args is given
                if (count != null) {
                    // Store the values to display in the left and right columns of the "table" respectively
                    ArrayList<String> leftColumnArr = new ArrayList<String>();
                    ArrayList<String> rightColumnArr = new ArrayList<String>();

                    // To take into consideration the length of the column headers
                    leftColumnArr.add("WORD");
                    leftColumnArr.add("TOTAL");
                    rightColumnArr.add("COUNT");

                    for (int val : count) { // calculates total count
                        totalCount += val;
                    }

                    for (String word : searchTerms) { //add all elements in searchterm array into arraylist: "leftColumnArr"
                        leftColumnArr.add(word);
                    }

                    for (int num : count) { //add all elements in count array into arraylist: "rightColumnArr" as strings
                        rightColumnArr.add(Integer.toString(num));
                    }

                    // find the element with the largest size in each column
                    String maxLeft = maxElementInArray(leftColumnArr);
                    String maxRight = maxElementInArray(rightColumnArr);

                    int leftSize = maxLeft.length(); // space length of the left column in the table
                    int rightSize = maxRight.length(); // space length of the right column in the table

                    // Table design
                    tabeleOutline(leftSize, rightSize); //calls function tableOutline
                    System.out.printf("| %-" + leftSize + "s | %-" + rightSize + "s |", "WORD", "COUNT");
                    System.out.println();
                    tabeleOutline(leftSize, rightSize);

                    for (int i = 0; i < searchArgsLength; i++) { // table values
                        System.out.printf("| %-" + leftSize + "s | %" + rightSize + "s |", searchTerms[i], count[i]);
                        System.out.println();
                    }
                    tabeleOutline(leftSize, rightSize);
                    System.out.printf("| %-" + leftSize + "s | %" + rightSize + "d |", "TOTAL", totalCount);
                    System.out.println();
                    tabeleOutline(leftSize, rightSize);

                }
            }
        } else {
            System.out.println("Usage: java WordCounter <filename> <searchTerm>");
        }
    }
}