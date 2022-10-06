import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

/**
 *    WordCounter Program.
 *    @author: Kiran Baby, 220015821
 */

public class WordCounter {

    /**
     * Method to find the count of each words passed into the args.
     * @param searchArgsLength
     * @param filename
     * @param searchTerms
     * @return count
     */
    public static int[] counter(int searchArgsLength, String filename, String[] searchTerms) {
        try {
            int[] count = new int[searchArgsLength];
            File textFile = new File(filename);
            Scanner textScanner = new Scanner(textFile);

            // Iterate through each line of the file
            while (textScanner.hasNext()) {
                String word = textScanner.next();
                // Remove special characters in the string and covert the o/p to an array
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
     * @param leftSize
     * @param rightSize
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
     * Return largest element in the array.
     * @param arr
     * @return index
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
     * @param args
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            int searchArgsLength = args.length - 1;
            int[] count = new int[searchArgsLength];
            int total = 0;
            String filename = args[0];
            String[] searchTerms = new String[searchArgsLength];
            ArrayList<String> leftColumnArr = new ArrayList<String>();
            ArrayList<String> rightColumnArr = new ArrayList<String>();

            for (int i = 0; i < searchArgsLength; i++) {
                searchTerms[i] = args[i + 1];
            }

            // Condition for when args length is '1'
            if (searchArgsLength == 1) {
                count = counter(searchArgsLength, filename, searchTerms);

                if (count != null) {
                    if (count[0] == 1) {
                        System.out.println("The word '" + searchTerms[0] + "' appears " + count[0] + " time.");
                    } else {
                        System.out.println("The word '" + searchTerms[0] + "' appears " + count[0] + " times.");
                    }
                }

            } else if (searchArgsLength > 1) {     // condition for "more than 1" args
                count = counter(searchArgsLength, filename, searchTerms);

                // Total count
                for (int val : count) {
                    total += val;
                }

                if (count != null) {
                    leftColumnArr.add("WORD");
                    leftColumnArr.add("TOTAL");

                    rightColumnArr.add("COUNT");

                    for (String word : searchTerms) {
                        leftColumnArr.add(word);
                    }

                    for (int num : count) {
                        rightColumnArr.add(Integer.toString(num));
                    }

                    String maxLeft = maxElementInArray(leftColumnArr);
                    String maxRight = maxElementInArray(rightColumnArr);

                    int leftSize = maxLeft.length();
                    int rightSize = maxRight.length();

//                    ------------------------------------------------------
                    tabeleOutline(leftSize, rightSize);
                    System.out.printf("| %-" + (leftSize + 1) + "s| %-" + (rightSize) + "s |", "WORD", "COUNT");
                    System.out.println();
                    tabeleOutline(leftSize, rightSize);

                    for (int i = 0; i < searchArgsLength; i++) {
                        System.out.printf("| %-" + (leftSize + 1) + "s| %" + (rightSize) + "s |", searchTerms[i], count[i]);
                        System.out.println();
                    }
                    tabeleOutline(leftSize, rightSize);
                    System.out.printf("| %-" + (leftSize + 1) + "s| %" + (rightSize) + "d |", "TOTAL", total);
                    System.out.println();
                    tabeleOutline(leftSize, rightSize);

                }
            }
        } else {
            System.out.println("Usage: java WordCounter <filename> <searchTerm>");
        }
    }
}