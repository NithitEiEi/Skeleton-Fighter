package entity;

import static constant.Constant.ActionConstant.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Villager extends Enemy {

    private BufferedImage img;
    private BufferedImage[][] animation;
    private int retreatCount = 0;

    public Villager(int x, int health, int y) {
        super(x, health, y, 30, 100);
        playeraction = VILLWALK;
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
        if (aniTick == 0 && index == 9 && attacking) {
            this.attackPoint = (int) getAtkRange();
            if (retreatCount == 2) {
                retreatCount = 0;
                setRetreat();
            } else {
                retreatCount++;
            }
            if (attackPoint <= player.gethitbox() && atkType != player.getDef()) {
                player.hurt(1);
                setRetreat();
                retreatCount = 0;
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
        setIndex();
        setRetreat();
        retreatCount = 0;
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
            img = ImageIO.read(new File("res/character/villager_sprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        animation = new BufferedImage[6][10];
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
            playeraction = VILLDEAD;
        } else if (attacking) {
            aniSpeed = 6;
            if (atkType == 1) {
                zIndex = -1;
                playeraction = ALEFT;
            } else if (atkType == 2) {
                zIndex = 0;
                playeraction = ATOP;
            } else if (atkType == 0) {
                zIndex = 1;
                playeraction = ARIGHT;
            }
        } else if (moving) {
            aniSpeed = 10;
            playeraction = VILLWALK;
        } else {
            zIndex = 0;
            playeraction = VILLIDLE;
            aniSpeed = 8;
        }

        if (aniTick >= aniSpeed) {
            aniTick = 0;
            index++;
            if (index >= getVillagerFrames(playeraction) && attacking) {
                endAtkSet(true);
            }
            if (index >= getVillagerFrames(playeraction)) {
                index = 0;
            }
        }
        if (attacking && endAtk) {
            attackingState(false);
            endAtkSet(false);
        }

    }

    public void update(Graphics g) {
        UpdateDirection(0.6, 2);
        getHealthBar(g, (int) x + 70, y, 80, 5);
        g.drawImage(animation[playeraction][(int) index], (int) x, y, 160, 160, null);
        setAnimation();
        retreat();
        dead();
    }
}
