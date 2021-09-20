/* Tests for TweetParser */

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TweetParserTest {

    // A helper function to create a singleton list from a word
    private static List<String> singleton(String word) {
        List<String> l = new LinkedList<String>();
        l.add(word);
        return l;
    }

    // A helper function for creating lists of strings
    private static List<String> listOfArray(String[] words) {
        List<String> l = new LinkedList<String>();
        for (String s : words) {
            l.add(s);
        }
        return l;
    }

    // Cleaning and filtering tests -------------------------------------------
    @Test
    public void removeURLsTest() {
        assertEquals("abc", TweetParser.removeURLs("abc"));
        assertEquals("abc ", TweetParser.removeURLs("abc http://www.cis.upenn.edu"));
        assertEquals(" abc ", TweetParser.removeURLs("http:// abc http:ala34?#?"));
        assertEquals(" abc  def", TweetParser.removeURLs("http:// abc http:ala34?#? def"));
        assertEquals(" abc  def", TweetParser.removeURLs("https:// abc https``\":ala34?#? def"));
        assertEquals("abchttp", TweetParser.removeURLs("abchttp"));
    }

    @Test
    public void testCleanWord() {
        assertEquals("abc", TweetParser.cleanWord("abc"));
        assertEquals("abc", TweetParser.cleanWord("ABC"));
        assertNull(TweetParser.cleanWord("@abc"));
        assertEquals("ab'c", TweetParser.cleanWord("ab'c"));
    }

    @Test
    public void testExtractColumnGetsCorrectColumn() {
        assertEquals(" This is a tweet.",
                TweetParser.extractColumn(
                        "wrongColumn, wrong column, wrong column!, This is a tweet.", 3));
    }

    @Test
    public void parseAndCleanSentenceNonEmptyFiltered() {
        List<String> sentence = TweetParser.parseAndCleanSentence("abc #@#F");
        List<String> expected = new LinkedList<String>();
        expected.add("abc");
        assertEquals(expected, sentence);
    }

    @Test
    public void testParseAndCleanTweetRemovesURLS1() {
        List<List<String>> sentences =
                TweetParser.parseAndCleanTweet("abc http://www.cis.upenn.edu");
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(singleton("abc"));
        assertEquals(expected, sentences);
    }

    @Test
    public void testCsvFileToTrainingDataSimpleCSV() {
        List<List<String>> tweets =
                TweetParser.csvFileToTrainingData("files/simple_test_data.csv", 1);
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(listOfArray("the end should come here".split(" ")));
        expected.add(listOfArray("this comes from data with no duplicate words".split(" ")));
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvFileToTweetsSimpleCSV() {
        List<String> tweets = TweetParser.csvFileToTweets("files/simple_test_data.csv", 1);
        List<String> expected = new LinkedList<String>();
        expected.add(" The end should come here.");
        expected.add(" This comes from data with no duplicate words!");
        assertEquals(expected, tweets);
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */
    @Test
    public void testExtractColumnNullcsvLine() {
        String str = null;
        assertNull(TweetParser.extractColumn(str, 3));
    }
    
    @Test
    public void testExtractColumnGetsNullColumn() {
        assertNull(TweetParser.extractColumn(
                        "wrongColumn, wrong column, wrong column!, This is a tweet.", 9));
    }
    
    @Test
    public void testExtractNegativeColumn() {
        assertNull(TweetParser.extractColumn(
                        "wrongColumn, wrong column, wrong column!, This is a tweet.", -1));
    }
    
    @Test
    public void testCsvFileToTweetsErrors() {
        assertThrows(IllegalArgumentException.class, () -> {
            TweetParser.csvFileToTweets(null, 1); });
        assertThrows(IllegalArgumentException.class, () -> {
            TweetParser.csvFileToTweets("files/safdsdwe", 1); });
    }
    
    @Test
    public void parseAndCleanSentenceNoFilter() {
        List<String> sentence = TweetParser.parseAndCleanSentence("abc efg");
        List<String> expected = new LinkedList<String>();
        expected.add("abc");
        expected.add("efg");
        assertEquals(expected, sentence);
    }
    
    @Test
    public void parseAndCleanSentenceEmptySentence() {
        List<String> sentence = TweetParser.parseAndCleanSentence("");
        List<String> expected = new LinkedList<String>();
        assertEquals(expected, sentence);
    }
    
    @Test
    public void parseAndCleanSentenceAllFiltered() {
        List<String> sentence = TweetParser.parseAndCleanSentence("*()_$ _@$*)");
        List<String> expected = new LinkedList<String>();
        assertEquals(expected, sentence);
    }
    
    @Test
    public void testParseAndCleanTweetRemovesURLSAndFilters() {
        List<List<String>> sentences =
                TweetParser.parseAndCleanTweet("abc &))&& http://www.cis.upenn.edu");
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(singleton("abc"));
        assertEquals(expected, sentences);
    }
    
    @Test
    public void testParseAndCleanTweetMultipleSentences() {
        List<List<String>> sentences =
                TweetParser.parseAndCleanTweet("Hello World. Good Morning");
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(listOfArray(new String[] {"hello", "world"}));
        expected.add(listOfArray(new String[] {"good", "morning"}));
        assertEquals(expected, sentences);
    }
    
    @Test
    public void testParseAndCleanTweetDoesNotIncludeEmptySentence() {
        List<List<String>> sentences =
                TweetParser.parseAndCleanTweet("Hello World. #*__*)");
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(listOfArray(new String[] {"hello", "world"}));
        assertEquals(expected, sentences);
    }
    
    @Test
    public void testCsvFileToTrainingErrors() {
        assertThrows(IllegalArgumentException.class, () -> {
            TweetParser.csvFileToTrainingData(null, 1); });
        assertThrows(IllegalArgumentException.class, () -> {
            TweetParser.csvFileToTrainingData("files/safdsdwe", 1); });
    }
    
}
