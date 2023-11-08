package main;

import javax.swing.*;
import static constant.FontClass.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Final extends JPanel {
    
    private Game game;

    Final(Game game) {
        this.game = game;
        setPanelSize();
    }

    public void setPanelSize() {
        setLayout(new GridBagLayout());
        setBackground(new Color(3, 7, 12));

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel title = new JLabel("No one STAND IN YOUR WAY!!");

        title.setForeground(new Color(255, 255, 255));
        title.setFont(FONT50);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 450, 0);

        add(title, gbc);

        JButton button = new JButton("back to main menu", null);
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(50, 50, 50));
        button.setFont(FONT22);
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 50));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setChapter(0);
                game.to(new MainMenu(game));
            }
            
        });
        gbc.insets = new Insets(100, 0, 0, 0);
        add(button, gbc);


    }

}

