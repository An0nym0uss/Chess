package chessgame.chess.board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Class {@code BoardReader} is used to help set up {@code Board}.
 */
public class BoardReader {

    /**
     * Given file name of a text file found in "src/resources/boards", convert text
     * to {@code String} and return.
     * 
     * @param fileName
     * @return {@code String} of content if file exists, {@code null} otherwise
     */
    public static String readBoard(String fileName) {
        String path = BoardReader.class.getResource("/resources/boards/" + fileName).getPath();
        try (Reader reader = new FileReader(path);
                BufferedReader bufReader = new BufferedReader(reader)) {

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufReader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
