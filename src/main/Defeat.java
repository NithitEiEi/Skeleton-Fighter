package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static constant.Constant.MapConstant.*;
import static constant.FontClass.*;

public class Defeat extends JPanel {
    
    private Game game;

    Defeat(Game game) {
        this.game = game;
        setPanelSize();
    }

    public void setPanelSize() {
        setLayout(null);
        setBackground(new Color(3, 7, 12));

        JLabel title = new JLabel("Defeated");

        title.setForeground(new Color(255, 255, 255));
        title.setFont(FONT50);
        title.setBounds(300, 200, 200, 50);
        add(title);

        JButton buttonNext = new JButton("try again", null);
        buttonNext.setForeground(new Color(255, 255, 255));
        buttonNext.setBackground(new Color(50, 50, 50));
        buttonNext.setFont(FONT22);
        buttonNext.setBorder(null);
        buttonNext.setFocusPainted(false);
        buttonNext.setPreferredSize(new Dimension(200, 50));
        buttonNext.setBounds(150, 300, 200, 50);
        buttonNext.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                game.to(new GamePanel(game, chapter[game.getChapter()], enemyList[game.getChapter()], game.getChapter()));
            }
            
        });

        JButton buttonMain = new JButton("Main menu", null);
        buttonMain.setForeground(new Color(255, 255, 255));
        buttonMain.setBackground(new Color(50, 50, 50));
        buttonMain.setFont(FONT22);
        buttonMain.setBorder(null);
        buttonMain.setFocusPainted(false);
        buttonMain.setPreferredSize(new Dimension(200, 50));
        buttonMain.setBounds(425, 300, 200, 50);
        buttonMain.addActionListener(new ActionListener() {
            

            @Override
            public void actionPerformed(ActionEvent e) {
                game.to(new MainMenu(game));
            }
            
        });

        add(buttonNext, BorderLayout.NORTH);
        add(buttonMain, BorderLayout.NORTH);

    }

}