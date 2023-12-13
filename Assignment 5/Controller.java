/* Laine Rainbolt 10/30/2022 Assignment 5: Goombas and Fireballs
 * This program takes mouse and keyboard input
 * to add and remove a pipe image when in edit mode,
 * add Goomba images when in goomba mode, and
 * make Mario run, jump, collide with pipes, 
 * and shoot fireballs that kill the goombas. */

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


class Controller implements ActionListener, MouseListener, KeyListener
{
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySpace;
	boolean keyEscape;
	boolean keyControl;
	boolean editMode;
	boolean goombaMode;

	Controller(Model m)
	{
    	model = m;
		editMode = false;
		goombaMode = false;
	}

	void setView(View v)
	{
    	view = v;
	}

	void setModel(Model m)
	{
		model = m;
	}

	public void actionPerformed(ActionEvent e)
	{
		
	}

	public void mousePressed(MouseEvent e)
	{	
		if(editMode) 
			if(goombaMode)
				model.addGoomba(e.getX() + view.scrollPos, e.getY());
			else
				model.addPipe(e.getX() + view.scrollPos, e.getY());
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_SPACE: keySpace = true; break;
			case KeyEvent.VK_ESCAPE: keyEscape = true; break;
			case KeyEvent.VK_CONTROL: 
				model.addFireball(model.sprites.get(0).x, model.sprites.get(0).y + 50);
				keyControl = true; 
				break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_SPACE: keySpace = false; break;
			case KeyEvent.VK_ESCAPE: keyEscape = false; break;
			case KeyEvent.VK_CONTROL: keyControl = false; break;
		}

		char c = Character.toLowerCase(e.getKeyChar());
		if(c == 'q')
			System.exit(0);
		if(c == 'e'){
			editMode = !editMode;
			System.out.println("Edit mode: " + editMode + " and Goomba mode: " + goombaMode);
		}
		if(c == 'g'){
			goombaMode = !goombaMode;
			System.out.println("Goomba mode: " + goombaMode);
		}
		if(c == 's')
		{
			Json saveObject = model.marshal();
			saveObject.save("map.json");
			System.out.println("File is saved");
		}
		if(c == 'l')	
		{
			Json loadObjext = Json.load("map.json");
			model.unmarshal(loadObjext);
			System.out.println("File is loaded");
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	void update()
	{
		((Mario) model.sprites.get(0)).setPreviousPosition();

		for(int i = 0; i < model.sprites.size(); i++)
		{
			if(model.sprites.get(i).isGoomba())
				((Goomba) model.sprites.get(i)).setPreviousPosition();
		}

		if(keyRight){
			(model.sprites.get(0).x)+=10;
			((Mario) model.sprites.get(0)).changeImageState();
			(model.sprites.get(0).rightFacing) = true;
		}
		if(keyLeft){
			(model.sprites.get(0).x)-=10;
			((Mario) model.sprites.get(0)).changeImageState();
			(model.sprites.get(0).rightFacing) = false;
		}
		if(keySpace) {
			((Mario) model.sprites.get(0)).Jump();
		}
		if(keyEscape) {
			System.exit(0);
		}
	}
}
