/* Tests for TwitterBot class */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TwitterBotTest {

    String simpleData = "files/simple_test_data.csv";
    String testData = "files/test_data.csv";
    String noTrainingData = "files/big_ben_clock.csv";

    /*
     * This tests whether your TwitterBot class itself is written correctly
     *
     * This test operates very similarly to our MarkovChain tests in its use of
     * `fixDistribution`, so make sure you know how to test MarkovChain before testing this!
     */
    @Test
    public void simpleTwitterBotTest() {
        List<String> desiredTweet = new ArrayList<>(Arrays.asList(
                "this", "comes", "from", "data", "with", "no", "duplicate", "words", ".", "the",
                "end", "should", "come"
        ));

        TwitterBot t = new TwitterBot(simpleData, 1);
        t.fixDistribution(desiredTweet);

        String expected = "this comes from data with no duplicate words. the end should come.";
        String actual = TweetParser.replacePunctuation(t.generateTweet(63));
        assertEquals(expected, actual);
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */
    @Test
    public void simpleTwitterBotLengthMinusOne() {
        List<String> desiredTweet = new ArrayList<>(Arrays.asList(
                "this", "comes", "from", "data", "with", "no", "duplicate", "words", ".", "the",
                "end", "should", "come"
        ));

        TwitterBot t = new TwitterBot(simpleData, 1);
        t.fixDistribution(desiredTweet);

        String expected = "this comes from data with no duplicate words. the end should come.";
        //at the end of "should", the length of the generated tweet is exactly length - 1.
        //will output one more word.
        String actual = TweetParser.replacePunctuation(t.generateTweet(61));
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void simpleTwitterBotExactLength() {
        List<String> desiredTweet = new ArrayList<>(Arrays.asList(
                "this", "comes", "from", "data", "with", "no", "duplicate", "words", ".", "the",
                "end", "should", "come"
        ));

        TwitterBot t = new TwitterBot(simpleData, 1);
        t.fixDistribution(desiredTweet);

        String expected = "this comes from data with no duplicate words. the end should.";
        //at the end of "should", the length of the generated tweet is exactly length.
        //will NOT output one more word.
        String actual = TweetParser.replacePunctuation(t.generateTweet(60));
        assertEquals(expected, actual);
    }
    
    @Test
    public void testTwitterBotIllegalArguments() {
        TwitterBot t = new TwitterBot(simpleData, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            t.generateTweet(0); });
        assertThrows(IllegalArgumentException.class, () -> {
            t.generateTweet(281); });
    }
    
    @Test
    public void exactly12() {
        List<String> expected = new ArrayList<>(Arrays.asList("i", "need", "food", "!"));
        TwitterBot tb = new TwitterBot("./files/length_clarification.csv", 2);
        tb.fixDistribution(expected);
        String tweet = tb.generateTweet(12);
        assertEquals("i need food!", tweet);
        assertEquals(tweet.length(), 12);
    }

    @Test
    public void longerThan7() {
        List<String> expected = new ArrayList<>(Arrays.asList("i", "need", "food", "!"));
        TwitterBot tb = new TwitterBot("./files/length_clarification.csv", 2);
        tb.fixDistribution(expected);
        String tweet = tb.generateTweet(7);
        assertEquals("i need food!", tweet);
        assertNotEquals(tweet.length(), 7);
    }
}
