package chessgame.chess.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import chessgame.chess.board.Board;

/**
 * Class {@code BoardUtil} is used for saving and loading boards.
 */
public class BoardUtil {
    /**
     * Write the byte data for {@code Board} to a file.
     * 
     * @param fileName name of the file to be saved
     * @param board    the board
     */
    public static void boardSaver(String fileName, Board board) {
        String path = BoardUtil.class.getResource("/saves").getPath() + "/" + fileName;

        try (OutputStream outputStream = new FileOutputStream(path)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(board);
            objectOutputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the byte data for {@code Board} from a file and get the object.
     * 
     * @param fileName name of the file containing the board object
     * @return {@code Board} if successful, otherwise {@code null}
     */
    public static Board boardLoader(String fileName) {
        String path = BoardUtil.class.getResource("/saves").getPath() + "/" + fileName;
        try (InputStream inputStream = new FileInputStream(path)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            Board board = (Board) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();

            return board;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
