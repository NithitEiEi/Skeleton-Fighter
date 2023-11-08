package itemobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Axe extends ItemObject {

    private BufferedImage img;
    private BufferedImage[] animation;
    private int aniTick = 0;
    private int index = 0;

    public Axe(int x, int y, int speed, boolean activate) {
        super(x, y, speed, activate, 2);
        loadSprite();
    }

    public int getX() {
        return this.x;
    }

    public void setActivate(boolean active) {
        activate = active;
    }

    private void fire() {
        if(activate) {
            x -= speed;
        }
    }

    public void loadSprite() {
        try {
            img = ImageIO.read(new File("res/item/axe.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        animation = new BufferedImage[4];
        for(int i = 0; i < animation.length; i++) {
            animation[i] = img.getSubimage(i * 30, 0, 30, 30);
        }
    }

    public void setAnimation() {
        aniTick++;
            if(aniTick >= 4) {
                index++;
                aniTick = 0;
            }
            if(index >= 4) {
                index = 0;
            }
    }

    @Override
    public void update(Graphics g) {
        fire();
        g.drawImage(animation[index], (int) x, y + 35, 60, 60, null);
        setAnimation();
    }
}