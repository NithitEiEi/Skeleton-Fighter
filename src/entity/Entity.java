package entity;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class Entity extends JPanel{

    protected float x;
    protected int y = 400;

    protected double health;
    protected double curHealth;

    protected int aniTick = 0, index = 0;
    protected double aniSpeed;

    protected int direct = -1;
    protected int atkType = 0;
    protected int defType = -1;
    protected boolean dying = false;
    protected boolean defending = false;
    protected boolean moving = false;
    protected boolean attacking = false;
    public boolean endAtk = false;
    protected int huntLimit = 60;
    protected int zIndex = 0;

    Entity(int x, double health, int y) {
        this.x = x;
        this.health = health;
        this.curHealth = health;
        this.y = y;
    }

    public void setIndex() {
        this.index = 0;
        this.aniTick = 0;
    }

    public int getZIndex() {
        return zIndex;
    }

    protected int calHealth (int w) {
        return (int) Math.floor(curHealth / health * w);
    }

    public void getHealthBar(Graphics g, int x, int y, int w, int h) {
        g.setColor(new Color(176, 33, 46));
        g.fillRect(x , y, calHealth(w) , h);
    }

    public void attackingState(boolean attack) {
        if (!moving) {
            attacking = attack;
        }
    }

    public boolean isAttack() {
        return attacking;
    }

    public int getAtk() {
        return atkType;
    }

    public void attackType(int type) {
        atkType = type;
    }

    protected void endAtkSet(boolean set) {
        endAtk = set;
    }

    public void defendingState(boolean def) {
            defending = def;
    }

    public void defType(int type) {
        defType = type;
    }

    public int getDef() {
        return defType;
    }

    public void setDirection(int value) {
        direct = value;
    }


    public void movingState(boolean move) {
        if (!attacking) {
            moving = move;
        }
    }

    public void dyingState(boolean dead) {
        dying = dead;
        if(dying) {
            setIndex();
        }
    }
    
    public void impact(double value) {
        x += value;
    }
    
    protected abstract void UpdateDirection(double forward, double backward);

    protected abstract void hurt(int atk);

}
