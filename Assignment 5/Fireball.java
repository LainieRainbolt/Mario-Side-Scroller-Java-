/* Laine Rainbolt 10/30/2022 Assignment 5: Goombas and Fireballs
 * This program takes mouse and keyboard input
 * to add and remove a pipe image when in edit mode,
 * add Goomba images when in goomba mode, and
 * make Mario run, jump, collide with pipes, 
 * and shoot fireballs that kill the goombas. */

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Fireball extends Sprite {

    BufferedImage fireball_image;
    int horzVelocity = 15;

	Fireball(int x, int y)
	{
        this.x = x;
        this.y = y;
        this.h = 37;
        this.w = 37;
        if(fireball_image == null)
            fireball_image = View.loadImage("fireball.png");
	}

    public void draw(Graphics g, int scrollPos)
    {
        g.drawImage(fireball_image, x - scrollPos, y, null);
    }

    public void Bounce()
    {
        //frame counter less than 5
        if(frameCounter < 5)
            vertVelocity -= 26;
    }

    void update(){
        vertVelocity += 3; //this is gravity
		y += vertVelocity; //update position
        x += horzVelocity;

        frameCounter ++;

        if(y  > 385)
		{
			vertVelocity = 0;
			y = 385; // snap back to the ground
            frameCounter = 0;
            Bounce();
		}

    }

    boolean isFireball(){ return true; }

    @Override 
    public String toString()
    {
	    return "Fireball (x,y) = (" + x + ", " + y + ")";
    }
}
