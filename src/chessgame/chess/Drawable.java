package chessgame.chess;

import javax.swing.JPanel;
import java.awt.Graphics;

/**
 * Object should implement class {@code Drawable} if it needs to be drawn on a
 * panel.
 */
public interface Drawable {
    /**
     * Draw the object and its components to the panel.
     * 
     * @param g     the Graphics
     * @param panel the panel
     */
    void draw(Graphics g, JPanel panel);
}
