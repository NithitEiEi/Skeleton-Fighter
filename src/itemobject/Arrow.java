package itemobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Arrow extends ItemObject {

    private BufferedImage img;

    public Arrow(int x, int y, int speed, boolean activate) {
        super(x, y, speed, activate, 1);
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
            img = ImageIO.read(new File("res/item/arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Graphics g) {
        fire();
        g.drawImage(img, (int) x, y + 70, 90, 20, null);
    }
}
