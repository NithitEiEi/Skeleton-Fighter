package listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import entity.Player;
import main.GamePanel;

public class KeyInput implements KeyListener{
    
    private Player player;
    private GamePanel panel;
    
    public KeyInput(Player player, GamePanel panel) {
        this.player = player;
        this.panel = panel;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> {
                player.setDirection(-1);
                player.movingState(false);
                player.setIndex();
            }
            case KeyEvent.VK_D -> {
                player.setDirection(-1);
                player.movingState(false);
                player.setIndex();
            }
            case KeyEvent.VK_J -> {
                player.defendingState(false);
                player.defType(-1);
                player.setIndex();
                break;
            }
            case KeyEvent.VK_K -> {
                player.defendingState(false);
                player.defType(-1);
                player.setIndex();
                break;
            }
            case KeyEvent.VK_L -> {
                player.defendingState(false);
                player.defType(-1);
                player.setIndex();
                break;
            }
            case KeyEvent.VK_S -> {
                player.defendingState(false);
                player.defType(-1);
                player.setIndex();
                break;
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e){
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> {
                player.setDirection(0);
                player.movingState(true);
                break;
            }
            case KeyEvent.VK_D -> {
                player.setDirection(1);
                player.movingState(true);
                break;
            }
            case KeyEvent.VK_U -> {
                player.attackingState(true);
                player.attackType(0);
                break;
            }
            case KeyEvent.VK_I -> {
                player.attackingState(true);
                player.attackType(2);
                break;
            }
            case KeyEvent.VK_O -> {
                player.attackingState(true);
                player.attackType(1);
                break;
            }
            case KeyEvent.VK_J -> {
                player.defendingState(true);
                player.defType(1);
                player.setIndex();
                break;
            }
            case KeyEvent.VK_K -> {
                player.defendingState(true);
                player.defType(2);
                player.setIndex();
                break;
            }
            case KeyEvent.VK_L -> {
                player.defendingState(true);
                player.defType(0);
                player.setIndex();
                break;
            }
            case KeyEvent.VK_S -> {
                player.defendingState(true);
                player.defType(3);
                player.setIndex();
                break;
            }
            case KeyEvent.VK_ESCAPE -> {
                panel.pause();
            }
            case KeyEvent.VK_E -> {
                if(!player.isAttack()){
                    player.setIndex();
                }
                player.dodge = true;
            }
        }
    }
}
