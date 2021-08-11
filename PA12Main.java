/*
 * Name: Longxin Li
 * Date: 5/03/2021
 * Filename: PA12Main.java
 * Course: CSC 210
 * 
 * Purpose: This function will obtain the dictionary file name, 
 *  phrase, and max number of words in each possible anagram 
 *  from the array of strings passed to main, and read all of 
 *  the words from the dictionary file and use them to generate 
 *  the required anagrams to find all possible words that can 
 *  be found by using subsets of letters from the provided phrase.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class PA12Main {
    public static void main(String[] args) {
        String filename = args[0];
        String phrase = args[1];
        int limit = Integer.decode(args[2]);
        ArrayList<String> words = readFile(filename, phrase);

        System.out.println(String.format("Phrase to scramble: %s\n", phrase));

        System.out.println(
                String.format("All words found in %s:\n%s\n", phrase, words));

        System.out.println(String.format("Anagrams for %s:", phrase));
        scramble(words, new ArrayList<String>(), new LetterInventory(phrase),
                limit);
    }

    /**
     * read file and return a list of words
     * 
     * @param filename
     *            file name
     * @param phrase
     *            phrase used to filter
     * @return an ArrayList of words
     */
    public static ArrayList<String> readFile(String filename, String phrase) {
        ArrayList<String> words = new ArrayList<>();
        try {
            // read file
            File fileObj = new File(filename);
            Scanner scanner = new Scanner(fileObj);
            // for each word
            while (scanner.hasNextLine()) {
                LetterInventory temp = new LetterInventory(phrase);
                String word = scanner.nextLine();
                // if phrase can be subtract, add to words
                try {
                    temp.subtract(word);
                    words.add(word);
                    // otherwise, pass
                } catch (IllegalArgumentException e) {
                    // pass
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }

    public static void scramble(ArrayList<String> words,
            ArrayList<String> chosen, LetterInventory letters, int limit) {

        // successful match
        if (letters.size() == 0) {
            System.out.println(chosen);
        }

        // return if chosen size larger than limit
        if (limit != 0 && chosen.size() >= limit)
            return;

        // return if words is empty
        if (words.size() <= 0)
            return;

        // for each word in word list
        for (String word : words) {
            LetterInventory temp = new LetterInventory(letters);

            // if word is subtractable, recall to try to select next word
            try {
                temp.subtract(word);
                ArrayList<String> newWords = new ArrayList<>(words);
                newWords.remove(word);

                ArrayList<String> newChosen = new ArrayList<>(chosen);
                newChosen.add(word);

                scramble(newWords, newChosen, temp, limit);

            } catch (IllegalArgumentException e) {
                // pass
            }

        }

    }
}