package chessgame.chess.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code SaveUtil} is used for getting file names of the saves of
 * Chess game.
 */
public class SaveUtil {
    public static List<String> findAllSaves() {
        List<String> fileNameList = new ArrayList<>();

        String path = BoardUtil.class.getResource("/saves").getPath();
        // get text files only
        File[] listFiles = new File(path).listFiles((f, n) -> n.contains(".txt"));

        for (File file : listFiles) {
            fileNameList.add(file.getName());
        }

        return fileNameList;
    }
}
