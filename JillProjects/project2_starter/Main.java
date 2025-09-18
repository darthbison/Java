import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<String> stopWords = new HashSet<>(
                Arrays.asList("the", "is", "in", "at", "of", "and", "a", "to", "it", "or", "was", "so"));
        Set<String> positiveWords = new HashSet<>(Arrays.asList("good", "great", "happy", "love", "like"));
        Set<String> negativeWords = new HashSet<>(Arrays.asList("bad", "terrible", "horrible", "sad", "hate"));

        System.out.println("Enter a paragraph of text:");
        String input = scanner.nextLine();

        // Tokenize the input string
        String[] words = NLPUtility.splitTextIntoTokens(input);
        System.out.println("\nTokenized:");
        System.out.println(Arrays.toString(words));


        // Get frequency of non-stop words sorted by word in ascending order
        Map<String, Integer> mapSortedByKey = NLPUtility.countFilteredWords(words, stopWords);


        // If the map is empty, inform the user and stop the analysis.
        if (mapSortedByKey == null || mapSortedByKey.isEmpty()) {
            System.out.println("\nNo valid words found.");
        } else {
            System.out.println("\nWord map sorted by key ascending:");
            mapSortedByKey.forEach((k, v) -> System.out.println(k + ":" + v));

            System.out.println("\nWord map sorted by value descending:");
            // Get frequency of non-stop words sorted by frequency in descending order
            Map<String, Integer> mapSortedByValue = NLPUtility.sortByValueDescending(mapSortedByKey);
            mapSortedByValue.forEach((k, v) -> System.out.println(k + ":" + v));

            // Perform sentiment analysis on word map
            String sentiment = NLPUtility.getSentiment(mapSortedByKey, positiveWords, negativeWords);
            System.out.println("\nSentiment: " + sentiment);

            // Display most frequent word(s) and their frequency
            Map<String, Integer> mostFrequent = NLPUtility.getWordsWithMaxFrequency(mapSortedByKey);
            StringBuilder sb = new StringBuilder();
            sb.append("\nMost frequent word(s): (");

            for (String word : mostFrequent.keySet())
            {
                sb.append("\n'");
                sb.append(word);
                sb.append("' used: ");
                sb.append(mostFrequent.get(word));
                sb.append(" times.");
            }

            sb.append("\n)");

            System.out.println(sb.toString());
            //
//            System.out.println("\nMost frequent word(s): " +
//                    mostFrequent.get("words") +
//                    " (used "
//                    + mostFrequent.get("frequency") +
//                    " times)");
        }
        scanner.close();
    }

}
