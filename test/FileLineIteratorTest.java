/* Tests for FileLineIterator */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

public class FileLineIteratorTest {

    /* Here's a test to help you out,
     * but you still need to write your own.
     *
     * You don't need to create any files for your tests though.
     * (Our submission infrastructure actually won't accept any files you make for testing)
     */

    @Test
    public void testHasNextAndNext() {
        FileLineIterator li = new FileLineIterator("files/simple_test_data.csv");
        assertTrue(li.hasNext());
        assertEquals("0, The end should come here.", li.next());
        assertTrue(li.hasNext());
        assertEquals("1, This comes from data with no duplicate words!", li.next());
        assertFalse(li.hasNext());
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */
    
    @Test
    public void testNextError() {
        FileLineIterator li = new FileLineIterator("files/simple_test_data.csv");
        li.next();
        li.next();
        assertThrows(NoSuchElementException.class, () -> { 
            li.next(); });
    }
    

    @Test
    public void testFileNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FileLineIterator("files/sjfpwneghw0q8th0qw"); });
    }
    
    @Test
    public void testFilePathNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FileLineIterator(null); });
    }
    

}

