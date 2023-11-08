package main;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame{

    private JPanel mainMenu;
    private int chapter = 0;
    
    Game() {
        mainMenu = new MainMenu(this);
        add(mainMenu);
        pack();
        setVisible(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Skeleton Fighter");
    }

    public static void main(String[] args) {
        new Game();
    }

    public void to(JPanel panel) {
        setContentPane(panel);
        validate();
        repaint();

        panel.requestFocus();
    }

    public int getChapter() {
        return this.chapter;
    }

    public void setChapter(int value) {
        this.chapter = value;
    }
}