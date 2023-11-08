package entity;

import static constant.Constant.ActionConstant.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Slime extends Enemy {

    private BufferedImage img;
    private BufferedImage[][] animation;

    public Slime(int x, int health, int y) {
        super(x, health, y, 20, 60);
        playeraction = SLIMEWALK;
        loadSprite();
    }

    @Override
    public void attack(Player player, float playerX) {
        if (isFirstAttack) {
            atkType = randomAtk.nextInt(randomAtkType);
            isFirstAttack = false;
            setIndex();
        }
        if (x + attackRange - playerX <= 0) {
            setDirection(-1);
            movingState(false);
            attackingState(true);
        }
        if (aniTick == 0 && index == 8 && attacking) {
            this.attackPoint = (int) getAtkRange();
            if (attackPoint <= player.gethitbox() && atkType != player.getDef()) {
                player.hurt(1);
            } else {
                atkType = randomAtk.nextInt(randomAtkType);
            }
            player.impact(-7.5);
        } else {
            this.attackPoint = (int) x + hitboxPoint;
        }
    }

    @Override
    public void hurt(int atk) {
        curHealth--;
        if(atk == critValue){
            curHealth --;
        }
        if(curHealth <= 0) {
            retreating = false;
            movingState(false);
            dyingState(true);
            return;
        }
        impact(7.5);
        critValue = critRandom.nextInt(10);
    }

    @Override
    protected void loadSprite() {
        try {
            img = ImageIO.read(new File("res/character/slime_sprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        animation = new BufferedImage[3][9];
        for (int j = 0; j < animation.length; j++) {
            for (int i = 0; i < animation[j].length; i++) {
                animation[j][i] = img.getSubimage(i * 80, j * 80, 80, 80);
            }
        }
    }

    @Override
    public void setAnimation() {
        aniTick ++;
        if (dying) {
            aniSpeed = 12;
            playeraction = SLIMEDEAD;
        } else if (attacking) {
            aniSpeed = 12;
            playeraction = SLIMEATK;
        } else if (moving) {
            aniSpeed = 10;
            playeraction = SLIMEWALK;
        }

        if (aniTick >= aniSpeed) {
            aniTick = 0;
            index++;
            if (index >= getSlimeFrame(playeraction) && attacking) {
                endAtkSet(true);
            }
            if (index >= getSlimeFrame(playeraction)) {
                index = 0;
            }
        }
        if (attacking && endAtk) {
            attackingState(false);
            endAtkSet(false);
        }

    }

    public void update(Graphics g) {
        UpdateDirection(0.4, 0.4);
        getHealthBar(g, (int) x + 40, y, 80, 5);
        try {
            g.drawImage(animation[playeraction][(int) index], (int) x, y, 160, 160, null);
            
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(playeraction+" "+index);
        }
        setAnimation();
        dead();
    }

}
