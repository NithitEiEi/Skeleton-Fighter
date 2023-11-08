package entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static constant.Constant.ActionConstant.*;

public class Player extends Entity {

    private int attackPoint;
    private BufferedImage img;
    private BufferedImage[][] animation;
    protected static int playeraction;
    public boolean die = false;
    public boolean dodge = false;

    public Player(int x, int health, int y) {
        super(x, health, y);
        attackPoint = (int) gethitbox();
        loadSprite();
    }

    public float gethitbox() {
        return x + 110;
    }

    public float getAtkPoint() {
        return gethitbox() + attackPoint;
    }

    public void EnemyCollision(Enemy enemy) {
        if (gethitbox() >= enemy.gethitbox() - 10) {
            if (direct == 1) {
                movingState(false);
                setIndex();
            }
        }
    }

    public void dodge() {
        if (dodge && !defending && !attacking) {
            movingState(true);
            aniSpeed = 2;
            x -= 4;
            if (index == 1 && aniTick == 0 || gethitbox() - 50 <= 8) {
                dodge = false;
                movingState(false);
                setIndex();
                return;
            }
        }
    }

    public void attack(Enemy enemy) {
        if (aniTick == 0 && index == 5 && attacking) {
            attackPoint += 33;
        } else {
            attackPoint = (int) gethitbox();
        }
        if (attackPoint > enemy.gethitbox() && attacking) {
            enemy.hurt(getAtk());
        }
    }

    @Override
    public void defendingState(boolean def) {
        if (!dodge) {
            defending = def;
        } else {
            defending = false;
        }
    }

    @Override
    protected void UpdateDirection(double forward, double backward) {
        if (moving && !attacking && !defending) {
            if (direct == 0) {
                if (x > -60) {
                    this.x += backward;
                } else if (x <= -60) {
                    movingState(false);
                    setIndex();
                }
            } else if (direct == 1) {
                if (gethitbox() > 800) {
                    movingState(false);
                    setIndex();
                } else {
                    this.x += forward;
                }
            }
        }
    }

    @Override
    public void hurt(int atk) {

        curHealth -= atk;
        attackingState(false);
        movingState(false);
        setIndex();
        if (curHealth <= 0) {
            die = true;
            return;
        }
        impact(-7.5);
    }

    public void setAnimation() {

        aniTick++;
        if (attacking) {
            aniSpeed = 6;
            if (atkType == 0) {
                playeraction = ALEFT;
                zIndex = -1;
            } else if (atkType == 2) {
                zIndex = 0;
                playeraction = ATOP;
            } else if (atkType == 1) {
                zIndex = 1;
                playeraction = ARIGHT;
            }
        } else if (defending) {
            aniSpeed = 14;
            if (defType == 1) {
                playeraction = GSIDE;
            } else if (defType == 2) {
                playeraction = GTOP;
            } else if (defType == 0) {
                playeraction = GSIDE;
            } else if (defType == 3) {
                playeraction = LAYDOWN;
            }
        } else if (moving) {
            playeraction = WALK;
            aniSpeed = 8;
        } else {
            zIndex = 0;
            playeraction = IDLE;
            aniSpeed = 14;
        }

        if (aniTick >= aniSpeed) {
            aniTick = 0;
            index++;
            if (index >= getSpriteFrames(playeraction)) {
                if (attacking) {
                    endAtkSet(true);
                    playeraction = IDLE;
                    setIndex();
                }
                index = 0;
            }
        }
        if (attacking && endAtk || defending) {
            attackingState(false);
            endAtkSet(false);
        }

    }

    public void loadSprite() {
        try {
            img = ImageIO.read(new File("res/character/skeleton_sprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        animation = new BufferedImage[8][9];
        for (int j = 0; j < animation.length; j++) {
            for (int i = 0; i < animation[j].length; i++) {
                animation[j][i] = img.getSubimage(i * 80, j * 80, 80, 80);
            }
        }
    }

    public void update(Graphics g) {
        setAnimation();
        UpdateDirection(0.8, -0.8);
        getHealthBar(g, 10, 10, 230, 15);
        g.drawImage(animation[playeraction][(int) index], (int) x, y, 160, 160, null);
        dodge();
    }
}
