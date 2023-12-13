/* Laine Rainbolt 10/30/2022 Assignment 5: Goombas and Fireballs
 * This program takes mouse and keyboard input
 * to add and remove a pipe image when in edit mode,
 * add Goomba images when in goomba mode, and
 * make Mario run, jump, collide with pipes, 
 * and shoot fireballs that kill the goombas. */

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Goomba extends Sprite {

    BufferedImage goomba_image;
    BufferedImage goombaFire_image;
    int prev_x, prev_y;
    int direction;
    int speed = 5;
    int burnFrames = 0;
    boolean onFire = false;

	Goomba(int x, int y)
	{
        this.x = x;
        this.y = y;
        this.h = 45;
        this.w = 37;
        direction = -1;
        if(goomba_image == null)
            goomba_image = View.loadImage("goomba.png");
        if(goombaFire_image == null)
            goombaFire_image = View.loadImage("goomba_fire.png");
	}

    public Goomba(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        if(goomba_image == null)
            goomba_image = View.loadImage("goomba.png");
        if(goombaFire_image == null)
            goombaFire_image = View.loadImage("goomba_fire.png");
        this.h = 45;
        this.w = 37;
        direction = -1;
    }

    Json marshal ()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }

    public void draw(Graphics g, int scrollPos)
    {
        if(onFire)
            g.drawImage(goombaFire_image, x - scrollPos, y, null);
        else
            g.drawImage(goomba_image, x - scrollPos, y, null);
    }

    public void setPreviousPosition()
    {
        prev_x = x;
        prev_y = y;
    }

    public void getOutOfPipe(Pipe pipe)
    {
        //Goomba is coming from the left, moving to the right
        if(x + w >= pipe.x && prev_x + w <= pipe.x){
            //Goomba's right = pipes left
            x = pipe.x - w;
            direction *= -1;
        }
        //Goomba is coming from the right, moving to the left
        if(prev_x >= pipe.x + pipe.w){
            //Goomba's right = pipes left
            x = pipe.x + pipe.w;
            direction *= -1;
        }
        //if Goomba is coming from the air, moving down
        if(prev_y + h <= pipe.y){
            //Goomba's toes = pipe's top
            y = pipe.y - h;
            vertVelocity = 0;
        }
        //coming from the ground, moving up 
        if(prev_y >= pipe.y + pipe.h){
            //Goomba's head = Pipe's bottom
            y = pipe.y + pipe.h;
            vertVelocity = 0;
        }
    }

    boolean removeable(){
        if(burnFrames > 25)
            return true;
        else
            return false;
    }

    void update(){
        vertVelocity += 5.5; //this is gravity
		y += vertVelocity; //update position

        if(!onFire){
            x += direction * speed;
        }
        else
            burnFrames ++;

        if(y > 370)
		{
			vertVelocity = 0;
			y = 370; // snap back to the ground
		}
    }

    boolean isGoomba(){ return true; }

    @Override 
    public String toString()
    {
	    return "Goomba (x,y) = (" + x + ", " + y + ") w = " + w;
    }

}
