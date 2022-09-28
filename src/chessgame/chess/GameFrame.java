package chessgame.chess;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class {@code GameFrame} defines the game frame of Chess and displays it on
 * screen.
 */
public class GameFrame extends JFrame implements Runnable {
    private int width;
    private GamePanel panel;
    private Game game;

    public GameFrame() {
    }

    @Override
    public void run() {
        chooseResolution();
    }

    /**
     * Allows to choose resolution of Chess game and then starts the game.
     */
    private void chooseResolution() {
        JFrame resolution = new JFrame();
        JPanel title = new JPanel();
        JLabel titleLable = new JLabel("Choose resolution");
        title.add(titleLable);
        resolution.add(title, BorderLayout.NORTH);

        // buttons for different resolutions
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(4, 1, 10, 10));
        resolution.add(buttons);

        JButton r1 = new JButton("400 x 400");
        r1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                width = 400;
                resolution.dispose();
                startGame();
            }
        });
        buttons.add(r1);

        JButton r2 = new JButton("600 x 600");
        r2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                width = 600;
                resolution.dispose();
                startGame();
            }
        });
        buttons.add(r2);

        JButton r3 = new JButton("800 x 800");
        r3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                width = 800;
                resolution.dispose();
                startGame();
            }
        });
        buttons.add(r3);

        JButton r4 = new JButton("1000 x 1000");
        r4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                width = 1000;
                resolution.dispose();
                startGame();
            }
        });
        buttons.add(r4);

        resolution.setVisible(true);
        resolution.setSize(250, 300);
        resolution.setLocationRelativeTo(null);
        resolution.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * The game starts with a board panel and buttons at the bottom.
     */
    private void startGame() {
        game = new Game();
        panel = new GamePanel(width, game);

        this.getContentPane().add(panel, BorderLayout.NORTH);
        this.setTitle("Chess");
        this.setResizable(false);

        this.getContentPane().add(buttons(), BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Construct panel of two buttons: new game, take back move.
     * 
     * @return button panel
     */
    private JPanel buttons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, width / 10, 0));

        JButton takeBackMove = new JButton("take back move");
        takeBackMove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.deselectChosen();
                game.takeBackMove();
                game.changeSide();
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
