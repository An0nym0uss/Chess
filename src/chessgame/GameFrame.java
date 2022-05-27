package chessgame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private int width;
    private GamePanel panel;
    private Game game;
    
    public GameFrame() {
        this(600);
    }

    public GameFrame(int width) {
        this.width = width;
        game = new Game();
        panel = new GamePanel(width, game);

        this.getContentPane().add(panel, BorderLayout.NORTH);
        this.setTitle("Chess");
//        this.getContentPane().setPreferredSize(new Dimension(width,width));
        this.setResizable(false);
        
        this.getContentPane().add(buttons(), BorderLayout.SOUTH);
        
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private JPanel buttons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, width / 10, 0));

        JButton takeBackMove = new JButton("take back move");
        takeBackMove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.deselectChosen();
                game.takeBackMove();
                panel.revalidate();
                panel.repaint();
            }
        });

        JButton newGame = new JButton("new game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game = new Game();
                panel.setGame(game);
                panel.revalidate();
                panel.repaint();
            }
        });

        buttons.add(newGame);
        buttons.add(takeBackMove);
        buttons.setBackground(Color.GRAY);
        buttons.setPreferredSize(new Dimension(width, width / 15));

        return buttons;
    }
}
