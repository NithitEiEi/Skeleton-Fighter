package entity;

import java.awt.Graphics;
import java.util.Random;

public abstract class Enemy extends Entity {

    protected static int playeraction;
    protected int attackPoint;
    protected int attackRange;
    protected int hitboxPoint;
    protected int state = 0;
    protected boolean retreating = false;
    protected boolean huntActive = true;
    public boolean isDead = false;
    protected boolean isFirstAttack = true;
    protected int randomAtkType = 3;
    protected int retreatValue = 2;
    protected Random randomAtk = new Random();
    protected Random critRandom = new Random();
    protected int critValue;

    public Enemy(int x, int health, int y, int attackRange, int hitboxPoint) {
        super(x, health, y);
        this.attackRange = attackRange;
        this.hitboxPoint = hitboxPoint;
        critValue = critRandom.nextInt(10);
    }

    public void setX(int value) {
        this.x = value;
    }

    protected void setRetreat() {
        setIndex();
        huntActive = false;
        attackingState(false);
        movingState(true);
        retreating = true;
    }

    public float gethitbox() {
        return x + hitboxPoint;
    }

    public float getAtkRange() {
        return x + attackRange;
    }

    public int getCrit() {
        return critValue;
    }

    public void hunt(Player player) {
        if (huntActive) {
            float playerX = player.gethitbox();
            if (Math.abs((playerX - gethitbox())) >= Math.abs(getAtkRange() - gethitbox())) {
                movingState(true);
                setDirection(0);
                isFirstAttack = true;
                huntActive = true;
            } else {
                attack(player, playerX);
            }
        }
    }

    public void retreat() {
        if (retreating) {
            setDirection(1);
            if (index == 5 && aniTick == 0) {
                huntActive = true;
                retreating = false;
                return;
            }
        }
    }

    public void dead() {
        if (dying) {
            attackingState(false);
            movingState(false);
            huntActive = false;
            if (aniTick == 0 && index == 7) {
                isDead = true;
                setIndex();
                dyingState(false);
            }
        }
    }

    @Override
    protected void UpdateDirection(double forward, double backward) {
        if (moving && !attacking && !defending) {
            if (direct == 0) {
                this.x -= forward;
            } else if (direct == 1) {
                this.x += backward;
            }
        }
    }

    public abstract void attack(Player player, float playerX);

    protected abstract void loadSprite();

    public abstract void setAnimation();

    public abstract void update(Graphics g);

}
