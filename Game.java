/* Laine Rainbolt 10/30/2022 Assignment 5: Goombas and Fireballs
 * This program takes mouse and keyboard input
 * to add and remove a pipe image when in edit mode,
 * add Goomba images when in goomba mode, and
 * make Mario run, jump, collide with pipes, 
 * and shoot fireballs that kill the goombas. */

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	Model model = new Model();
	Controller controller = new Controller(model);
	View view = new View(controller, model);

	public Game()
	{
		this.setTitle("A5 - Polymorphism");
		this.setSize(500, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);
		this.addKeyListener(controller);

	}

	public void run()
	{
		Json loadObjext = Json.load("map.json");
		model.unmarshal(loadObjext);
		System.out.println("File is loaded");

		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 40 milliseconds - 25 frames per second
			try
			{
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}
}
