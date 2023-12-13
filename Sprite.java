/* Laine Rainbolt 10/30/2022 Assignment 5: Goombas and Fireballs
 * This program takes mouse and keyboard input
 * to add and remove a pipe image when in edit mode,
 * add Goomba images when in goomba mode, and
 * make Mario run, jump, collide with pipes, 
 * and shoot fireballs that kill the goombas. */

import java.awt.Graphics;

public abstract class Sprite {
    int x, y, w, h;
    int frameCounter;
    double vertVelocity = 1.2;
    boolean rightFacing = true;
    
    public abstract void draw(Graphics g, int scrollPos);

    abstract void update();

    boolean isPipe(){ return false; }
    boolean isGoomba(){ return false; }
    boolean isFireball(){ return false; }

    @Override 
    public String toString()
    {
	    return "Sprite (x,y) = (" + x + ", " + y + ")";
    }
}
