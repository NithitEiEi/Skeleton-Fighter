package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import entity.*;
import itemobject.Axe;
import itemobject.ItemObject;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import static constant.Constant.MapConstant.*;
import static constant.FontClass.*;

import listener.KeyInput;

public class GamePanel extends JPanel {

    private int frames = 120;
    private Timer timer = new Timer(1000 / frames, new Listener());
    private Player player;
    private Enemy currentEnemy;
    private Game game;
    private JLabel label = new JLabel("");
    private ImageIcon[] icons = { new ImageIcon("res/ui/pause.png"), new ImageIcon("res/ui/resume.png") };
    private JLabel pauseText = new JLabel(" Game Paused. ");
    private JButton pauseBtn = new JButton(icons[1]);
    private BufferedImage img;
    private boolean pause = false;
    private ArrayList<ItemObject> itemList = new ArrayList<>();
    private int launchTime = 200;
    private Random randomLaunch = new Random();
    private int enemies;
    private int chapters;
    private String[] enemyList;

    public GamePanel(Game gameClass, File background, String[] enemyList, int chapters) {

        try {
            img = ImageIO.read(background);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.enemyList = enemyList;
        this.chapters = chapters;
        this.game = gameClass;
        this.itemList.add(new Axe(900, 320, 7, false));
        setPause();
        setPanelSize();
        requestFocus();
        respawn();
        this.player = new Player(0, 8, getYLocation(chapters));
        addKeyListener(new KeyInput(player, this));
        this.enemies = 1;
        timer.start();
    }

    class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
            player.attack(currentEnemy);
            player.EnemyCollision(currentEnemy);
            currentEnemy.hunt(player);
            itemList.get(0).interact(player, itemList);
            gameDefeated();
            respawn();
            setCrit();
            launch();
        }

    }

    public void respawn() {
        if((enemies == 0 || currentEnemy.isDead && enemies != 0) && enemies < enemyList.length) {
            if(enemyList[enemies].equals("villager")) {
                currentEnemy = new Villager(900, 3, getYLocation(chapters));
                currentEnemy.isDead = false;
            } else if(enemyList[enemies].equals("knight")) {
                currentEnemy = new Knight(900, 6, getYLocation(chapters));
                currentEnemy.isDead = false;
            } else if(enemyList[enemies].equals("slime")) {
                currentEnemy = new Slime(900, 10, getYLocation(chapters));
                currentEnemy.isDead = false;
            }
            enemies++;
        }
    }

    public void gameDefeated() {
        if(enemies >= enemyList.length && currentEnemy.isDead) {
            timer.stop();
            if(chapters == finalChapter){
                game.setChapter(0);
                game.to(new Final(game));
                return;
            }
            game.setChapter(game.getChapter()+1);
            game.to(new Victory(game));
        }
        else if (player.die) {
            timer.stop();
            game.to(new Defeat(game));
        }
    }

    public void launch() {

        if(chapters >= 1) {
            if (launchTime <= 0) {
                itemList.get(0).setActivate(true);
                launchTime = 300 + randomLaunch.nextInt(200);
            } else {
                launchTime--;
            }
        }
    }

    public void drawBG(Graphics g) {
        g.drawImage(img, 0, 0, 800, 600, null);
    }

    private void setPause() {
        pauseText.setBounds(220, 250, 360, 60);
        pauseText.setFont(FONT60);
        pauseText.setOpaque(true);
        pauseText.setBackground(new Color(0, 0, 0, 128));
        pauseText.setForeground(new Color(255, 255, 255));
        pauseText.setVisible(false);
        pauseBtn.setBounds(730, 10, 40, 40);
        pauseBtn.setBackground(new Color(0, 0, 0, 128));
        pauseBtn.setBorderPainted(false);
        pauseBtn.setFocusPainted(false);
        pauseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });
    }

    public void pause() {
        pause = !pause;
        if (pause) {
            pauseText.setVisible(true);
            pauseBtn.setIcon(icons[0]);
            timer.stop();
        } else {
            pauseText.setVisible(false);
            pauseBtn.setIcon(icons[1]);
            timer.start();
            requestFocus();
        }
        return;

    }

    private void setCrit() {
        label.setBounds(10, 30, 230, 50);
        label.setFont(FONT28);
        label.setOpaque(true);
        label.setBackground(new Color(0, 0, 0, 128));
        label.setForeground(new Color(255, 255, 255));
        if (currentEnemy.getCrit() == 0) {
            label.setText(" Bonus ATK (left)");
        } else if (currentEnemy.getCrit() == 1) {
            label.setText(" Bunus ATK (right)");
        } else if (currentEnemy.getCrit() == 2) {
            label.setText(" Bunus ATK (top)");
        } else {
            label.setText(" Bonus ATK -");
        }
    }

    public void setPanelSize() {
        Dimension size = new Dimension(800, 600);
        setPreferredSize(size);
        setLayout(null);
        add(label);
        add(pauseBtn);
        add(pauseText);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBG(g);
        if (currentEnemy.getZIndex() > player.getZIndex()) {
            player.update(g);
            currentEnemy.update(g);
        } else {
            currentEnemy.update(g);
            player.update(g);
        }
        itemList.get(0).update(g);
    }

}