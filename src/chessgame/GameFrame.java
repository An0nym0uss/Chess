package chessgame;

import javax.swing.JFrame;
import java.awt.Dimension;

public class GameFrame extends JFrame {
    private GamePanel panel;
    
    public GameFrame() {
        this(600);
    }

    public GameFrame(int width) {
        panel = new GamePanel(width);

        this.setContentPane(panel);
        this.setTitle("Chess");
        this.getContentPane().setPreferredSize(new Dimension(width,width));
        this.setResizable(false);
        
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
