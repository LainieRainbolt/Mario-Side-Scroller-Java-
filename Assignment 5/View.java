/* Laine Rainbolt 10/14/2022 Assignment 4: Mario and Pipes
 * This program takes mouse and keyboard input
 * to add and remove a pipe image when in edit mode and
 * make Mario run, jump, and collide with pipes. */

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;

class View extends JPanel
{
	Model model;
	int scrollPos;
	BufferedImage ground_image;

	View(Controller c, Model m)
	{
		model = m;
		scrollPos = 0;
		c.setView(this);

		try
		{
			this.ground_image = ImageIO.read(new File("ground.png"));
		}
		
		catch(Exception e) 
		{
    		e.printStackTrace(System.err);
    		System.exit(1);
		}
	}

	public void paintComponent(Graphics g)
	{
		scrollPos = model.mario.x - 100;
		g.setColor(new Color(128, 255, 255));
    	g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.drawImage(ground_image, 0 - scrollPos, 415, null);
		g.drawImage(ground_image, -2010 - scrollPos, 415, null);
		g.drawImage(ground_image, 2010 - scrollPos, 415, null);

		for(int i = 0; i < model.sprites.size(); i++)
			model.sprites.get(i).draw(g, scrollPos);
	}

	static BufferedImage loadImage(String filename)
    {
	 	BufferedImage im = null;
        try
		{
			im = ImageIO.read(new File(filename));
		}
		catch(Exception e) 
		{
    		e.printStackTrace(System.err);
    		System.exit(1);
		}
	 	return im;
    }

}
