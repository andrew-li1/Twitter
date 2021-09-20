import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * FileLineIterator provides a useful wrapper around Java's provided
 * BufferedReader and provides practice with implementing an Iterator.
 * Your solution should not read the entire file into memory at once, instead
 * reading a line whenever the next() method is called. See
 * Java's documentation for BufferedReader to learn how to construct one given
 * a path to a file. Then, think about how you can use BufferedReader's
 * methods within this class to implement our desired functionality.
 * <p>
 * Note: Any IOExceptions thrown by readers should be caught and handled properly.
 */
public class FileLineIterator implements Iterator<String> {

    private BufferedReader br;
    private String line;

    /**
     * Creates a FileLineIterator for the file located at filePath.
     * Fill out the constructor so that a user can instantiate a FileLineIterator.
     * Feel free to create and instantiate any variables that your implementation requires here.
     * See recitation and lecture notes for guidance.
     * <p>
     * If an IOException is thrown by the BufferedReader or FileReader, then hasNext should return
     * false.
     *
     * @param filePath - the path to the CSV file to be turned to an Iterator
     * @throws IllegalArgumentException if filePath is null or if the file doesn't exist
     */
    public FileLineIterator(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException();
        }
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e1) {
            throw new IllegalArgumentException();
        }
        try {
            line = br.readLine();
        } catch (IOException e) {
            line = null;
            System.out.println("Error in reading");
        }

    }

    /**
     * Returns true if there are lines left to read in the file, and
     * false otherwise.
     * <p>
     * If there are no more lines left, this method should close the
     * BuffereReader.
     *
     * @return a boolean indicating whether the FileLineIterator
     * can produce another line from the file
     */
    @Override
    public boolean hasNext() {
        if (line != null) {
            return true;
        } else {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("Error in reading");
            }
            return false;
        }
    }

    /**
     * Returns the next line from the file, or throws a NoSuchElementException
     * if there are no more strings left to return (i.e. hasNext() is false).
     * <p>
     * This method also advances the iterator in preparation for another
     * invocation.  If an IOException is thrown during a next() call, the next time
     * next() is called, it should throw a NoSuchElementException.
     *
     * @return the next line in the file
     * @throws java.util.NoSuchElementException if there is no more data in the file
     */
    @Override
    public String next() {
        String temp = line;
        if (hasNext()) {
            try {
                line = br.readLine();
            } catch (IOException e) {
                System.out.println("Error in reading");
                line = null;
            }
        } else {
            throw new NoSuchElementException();
        }
        return temp;
    }
}
