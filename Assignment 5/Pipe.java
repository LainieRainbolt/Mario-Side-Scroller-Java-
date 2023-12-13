/* Laine Rainbolt 10/30/2022 Assignment 5: Goombas and Fireballs
 * This program takes mouse and keyboard input
 * to add and remove a pipe image when in edit mode,
 * add Goomba images when in goomba mode, and
 * make Mario run, jump, collide with pipes, 
 * and shoot fireballs that kill the goombas. */

import java.awt.image.BufferedImage;
import java.awt.Graphics;

class Pipe extends Sprite
{
    Model model;
    BufferedImage pipe_image;

	Pipe(int x, int y)
	{
        this.x = x;
        this.y = y;
        this.h = 400;
        this.w = 55;
        if(pipe_image == null)
            pipe_image = View.loadImage("pipe.png");
	}

    public Pipe(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        if(pipe_image == null)
            pipe_image = View.loadImage("pipe.png");
        this.h = 400;
        this.w = 55;
    }

    void setModel(Model m)
	{
		model = m;
	}

    boolean amIClickingOnYou(int mouse_x, int mouse_y)
    {
        if(mouse_x >= x && mouse_x <= (x + 55)){
            if(mouse_y >= y && mouse_y <= (y + 400))
                return true;
        }
        return false;
    }

    @Override 
    public String toString()
    {
	    return "Pipe (x,y) = (" + x + ", " + y + ") w = " + w;
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
        g.drawImage(pipe_image, x - scrollPos, y, null);
    }

    void update(){}

    @Override
    boolean isPipe() { return true; }
}