/* Laine Rainbolt 10/30/2022 Assignment 5: Goombas and Fireballs
 * This program takes mouse and keyboard input to add and remove a pipe image when in edit mode,
 * add Goomba images when in goomba mode, and make Mario run, jump, collide with pipes, 
 * and shoot fireballs that kill the goombas. */

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Mario extends Sprite
{
    int prev_x, prev_y;
    int currentImage;
    BufferedImage[] mario_images;

    public Mario(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.h = 95;
        this.w = 60;
        currentImage = 0;
        frameCounter = 0;
        mario_images = new BufferedImage[5];
        if(mario_images[0] == null){ mario_images[0] = View.loadImage("mario1.png"); }
        if(mario_images[1] == null){ mario_images[1] = View.loadImage("mario2.png"); }
        if(mario_images[2] == null){ mario_images[2] = View.loadImage("mario3.png"); }
        if(mario_images[3] == null){ mario_images[3] = View.loadImage("mario4.png"); }
        if(mario_images[4] == null){ mario_images[4] = View.loadImage("mario5.png"); }
    }

    void changeImageState()
    {
        currentImage++;
        if(currentImage > 4)
            currentImage = 0;
    }

    public void Jump()
    {
        //frame counter less than 5 AND the space bar is pressed
        if(frameCounter < 5)
            vertVelocity -= 15;
    }

    public void getOutOfPipe(Pipe pipe)
    {
        //Mario is coming from the left, moving to the right
        if(prev_x + w <= pipe.x){
            //mario's right = pipes left
            x = pipe.x - w;
        }

        //Mario is coming from the right, moving to the left
        if(prev_x >= pipe.x + pipe.w){
            //mario's right = pipes left
            x = pipe.x + pipe.w;
        }

        //if Mario is coming from the air, moving down
        if(prev_y + h <= pipe.y){
            //Mario's toes = pipe's top
            y = pipe.y - h;
            vertVelocity = 0;
            frameCounter = 0;
        }

        //coming from the ground, moving up 
        if(prev_y >= pipe.y + pipe.h){
            //Mario's head = Pipe's bottom
            y = pipe.y + pipe.h;
            vertVelocity = 0;
        }
    }

    public void setPreviousPosition()
    {
        prev_x = x;
        prev_y = y;
    }

    public void draw(Graphics g, int scrollPos)
    {
        if(super.rightFacing)
            g.drawImage(mario_images[currentImage], x - scrollPos, y, null);
        else
            g.drawImage(mario_images[currentImage], x - scrollPos + w, y, -w, h, null);
    }

    void update()
	{
        vertVelocity += 5.5; //this is gravity
		y += vertVelocity; //update position
        frameCounter ++;

        if(y > 320)
		{
			vertVelocity = 0;
			y = 320; // snap back to the ground
            frameCounter = 0;
		}
	}
    
    boolean isPipe() { return false; }

    @Override 
    public String toString()
    { 
	    return "Mario (x,y) = (" + x + ", " + y + "), width = " + w + ", height = " + h;
    }
}
