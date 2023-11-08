package entity;

import static constant.Constant.ActionConstant.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Knight extends Enemy {

    private BufferedImage img;
    private BufferedImage[][] animation;
    Graphics g;

    public Knight(int x, int health, int y) {
        super(x, health, y, 10, 70);
        playeraction = WALK;
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
        if (aniTick == 0 && index == 7 && attacking) {
            this.attackPoint = (int) getAtkRange();
            if (attackPoint <= player.gethitbox() && atkType != player.getDef()) {
                player.hurt(1);
            }
            player.impact(-7.5);
            atkType = randomAtk.nextInt(randomAtkType);
        } else {
            this.attackPoint = (int) x + hitboxPoint;
        }
    }

    @Override
    public void hurt(int atk) {
        curHealth--;
        setIndex();
        if(atk == critValue){
            curHealth--;
            setRetreat();
        }
        if(curHealth <= 0) {
            retreating = false;
            movingState(false);
            dyingState(true);
            return;
        }
        if (curHealth < health / 2) {
            randomAtkType = 4;
        }
        impact(7.5);
        critValue = critRandom.nextInt(10);
    }

    @Override
    protected void loadSprite() {
        try {
            this.img = ImageIO.read(new File("res/character/knight_sprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        animation = new BufferedImage[7][9];
        for (int j = 0; j < animation.length; j++) {
            for (int i = 0; i < animation[j].length; i++) {
                animation[j][i] = img.getSubimage(i * 80, j * 80, 80, 80);
            }
        }
    }

    @Override
    public void setAnimation() {
        aniTick += 1;
        if (dying) {
            aniSpeed = 12;
            playeraction = DEAD;
        } else if (attacking) {
            aniSpeed = 6;
            if (atkType == 0) {
                zIndex = 1;
                playeraction = ALEFT;
            } else if (atkType == 2) {
                zIndex = 0;
                playeraction = ATOP;
            } else if (atkType == 1) {
                zIndex = -1;
                playeraction = ARIGHT;
            } else if (atkType == 3) {
                aniSpeed = 8;
                playeraction = PIERCE;
            }
        } else if (moving) {
            aniSpeed = 8;
            playeraction = WALK;
        } else {
            zIndex = 0;
            playeraction = IDLE;
            aniSpeed = 8;
        }

        if (aniTick >= aniSpeed) {
            aniTick = 0;
            index++;
            if (index >= getKnightFrame(playeraction) && attacking) {
                endAtkSet(true);
            }
            if (index >= getKnightFrame(playeraction)) {
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
        getHealthBar(g, (int) x + 45, y, 80, 5);
        g.drawImage(animation[playeraction][(int) index], (int) x, y, 160, 160, null);
        setAnimation();
        retreat();
        dead();
    }

}
