package itemobject;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import entity.Player;

public abstract class ItemObject {

    protected int x;
    protected int y;
    protected int speed;
    protected boolean activate;
    private Random randomItem = new Random();
    private boolean hit = false;
    private int atk;

    public ItemObject(int x, int y, int speed, boolean activate, int atk) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.activate = activate;
        this.atk = atk;
    }

    public void setActivate(boolean active) {
        activate = active;
    }

    public void interact(Player player, ArrayList<ItemObject> itemList) {

        if (x <= player.gethitbox() && player.gethitbox() - 30 <= x) {
            if (player.getDef() != 3) {
                hit = true;
                player.hurt(this.atk);
                player.impact(-7.5);
            }
        }

        if (x < -50 || hit) {
            int random = randomItem.nextInt(10);
            if (random % 3 == 0) {
                itemList.add(new Axe(900, 320, 7, false));
            } else {
                itemList.add(new Arrow(900, 320, 7, false));
            }
            itemList.remove(0);
        }
    }

    public abstract void update(Graphics g);
}
