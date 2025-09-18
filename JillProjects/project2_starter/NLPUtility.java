import java.util.*;
import java.util.stream.Collectors;

public class NLPUtility {

    /**
     * Splits the given text into word tokens using one or more whitespace
     * or punctuation characters as delimiters.
     *
     * @param text the input string to be tokenized
     * @return an array of word tokens, excluding punctuation and whitespace
     */
    public static String[] splitTextIntoTokens(String text) {

        // 1. Remove punctuation using a regular expression
        // \\p{Punct} matches any punctuation character.
        // Replace all punctuation with an empty string.
        //String cleanedText = text.replaceAll("\\p{Punct}", "");
        String cleanedText = text.replaceAll("\\p{P}", "");

        // 2. Split the cleaned string into tokens (words) based on whitespace
        // \\s+ matches one or more whitespace characters.
        String[] tokens;
        tokens = cleanedText.split("\\s+");

        return tokens;
    }

    /**
     * Counts the frequency of words in the given array, excluding those present in
     * the specified set of stop words.
     * The comparison is case-insensitive, and results are stored in a
     * {@link TreeMap} sorted alphabetically by word.
     *
     * @param words     An array of tokenized words to analyze.
     * @param stopWords A set of words to exclude from the frequency count (e.g.,
     *                  common stop words like "the", "and").
     * @return A {@link TreeMap} mapping each non-stop word to its frequency, sorted
     *         alphabetically.
     */
    public static TreeMap<String, Integer> countFilteredWords(String[] words, Set<String> stopWords) {

        // Create a custom comparator for a TreeMap
        TreeMap<String, Integer> frequencyMap = getStringIntegerTreeMap();


        for (String word : words)
        {
            if (!stopWords.contains(word)) {
                String lowercaseWord = word.toLowerCase();

                if (frequencyMap.containsKey(lowercaseWord))
                {
                    int countValue = frequencyMap.get(lowercaseWord) + 1;
                    frequencyMap.put(lowercaseWord, countValue);
                }
                else {
                    frequencyMap.put(lowercaseWord, 1);
                }

            }
        }


        return frequencyMap;
    }

    private static TreeMap<String, Integer> getStringIntegerTreeMap() {
        Comparator<String> customComparator = (word1, word2) -> {
            int wordCount1 = word1.split("\\s+").length; // Count words
            int wordCount2 = word2.split("\\s+").length;

            // 1. Compare by word count (primary sort)
            int countComparison = Integer.compare(wordCount1, wordCount2);
            if (countComparison != 0) {
                return countComparison; // Sort ascending by word count
            }

            // 2. If word count is the same, compare alphabetically (secondary sort)
            return word1.compareTo(word2);
        };

        TreeMap<String, Integer> frequencyMap;
        frequencyMap = new TreeMap<>(customComparator);
        return frequencyMap;
    }

    /**
     * Sorts the entries of a map by their values in descending order.
     * The result is returned as a {@link LinkedHashMap} to preserve the order of
     * sorted entries.
     *
     * @param map A map containing keys and integer values to be sorted by value.
     * @return A {@link LinkedHashMap} containing the same entries as the input map,
     *         sorted in descending order by value.
     */
    public static LinkedHashMap<String, Integer> sortByValueDescending(Map<String, Integer> map) {
        LinkedHashMap<String, Integer> descendingOrderMap;
        descendingOrderMap = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, // Merge function in case of duplicate keys (not expected here)
                        LinkedHashMap::new // Ensure a LinkedHashMap is returned
                ));

        return descendingOrderMap;
    }

    /**
     * Performs sentiment analysis by scanning the word-frequency map.
     * Adds up the total frequency of all words found in the predefined
     * positive and negative word sets.
     *
     * @param wordMap A map of words and their frequencies.
     * @return A summary string in the format: "Positive: X, Negative: Y"
     *         where X and Y are the total counts of positive and negative words.
     */
    public static String getSentiment(Map<String, Integer> wordMap, Set<String> positiveWords,
            Set<String> negativeWords) {

        int postiveWordCount = 0;
        int negativeWordCount = 0;

        for (String word: wordMap.keySet())
        {
            if (positiveWords.contains(word)) {
                postiveWordCount++;
            }
            else if (negativeWords.contains(word))
            {
                negativeWordCount++;
            }
        }

        String sentiment;
        sentiment = "Positive: " + postiveWordCount + ", Negative: " + negativeWordCount;

        return sentiment;
    }

    /**
     * Finds the words with the highest frequency in the given map and returns a map
     * containing a sorted word list along with the maximum frequency value.
     *
     * @param wordMap A map of words and their corresponding frequencies.
     * @return A map containing:
     *         - "words": A list of words with the highest frequency, sorted
     *         alphabetically.
     *         - "frequency": The highest frequency value.
     */
    public static Map<String, Integer> getWordsWithMaxFrequency(Map<String, Integer> wordMap) {

        // Create a custom comparator for a TreeMap
        TreeMap<String, Integer> frequencyMap = getStringIntegerTreeMap();

        for (String word : wordMap.keySet())
        {
            int totalCount = wordMap.get(word);
            if (totalCount > 1)
            {
                frequencyMap.put(word, totalCount);
            }
        }

        return frequencyMap;
    }

}
