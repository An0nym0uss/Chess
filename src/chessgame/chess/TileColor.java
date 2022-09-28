package chessgame.chess;

import java.awt.Color;

/**
 * Class {@code TileColor} defines colors of tiles of different types and in
 * different status.
 */
public final class TileColor {
    /**
     * light green
     */
    public static Color WHITE_TILE = new Color(235, 235, 211);

    /**
     * dark green
     */
    public static Color BLACK_TILE = new Color(125, 147, 93);

    /**
     * yellow with 40% transparency
     */
    public static Color HIGHLIGHT_TILE = new Color(245, 245, 50, 100);

    /**
     * grey with 78% transparency
     */
    public static Color MOVE = new Color(90, 90, 90, 200);

    /**
     * pink with 78% transparency
     */
    public static Color CAPTURE = new Color(220, 30, 140, 200);

    /**
     * red with 40% transparency
     */
    public static Color CHECKED = new Color(235, 20, 20, 100);
}
