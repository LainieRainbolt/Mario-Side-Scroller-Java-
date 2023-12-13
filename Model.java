/* Laine Rainbolt 10/30/2022 Assignment 5: Goombas and Fireballs
 * This program takes mouse and keyboard input
 * to add and remove a pipe image when in edit mode,
 * add Goomba images when in goomba mode, and
 * make Mario run, jump, collide with pipes, 
 * and shoot fireballs that kill the goombas. */

import java.util.ArrayList;
import java.util.Iterator;

class Model
{
	ArrayList<Sprite> sprites;
	Mario mario;

	Model()
	{
		sprites = new ArrayList<Sprite>();
		mario = new Mario(100,320);
		sprites.add(mario);
	}

	Json marshal ()
	{
		Json ob = Json.newObject();
		Json tmpList = Json.newList();
		Json tmpListGoombas = Json.newList();
		ob.add("pipes", tmpList);
		ob.add("goombas", tmpListGoombas);
		for(Iterator<Sprite> it = sprites.iterator(); it.hasNext();)
		{
			Sprite s = it.next();
			if(s.isPipe())
				tmpList.add(((Pipe) s).marshal());
			if(s.isGoomba())
				tmpListGoombas.add(((Goomba) s).marshal());
		}
		return ob;
	}

	void unmarshal(Json ob)
	{
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);

		Json tmpList = ob.get("pipes");
		Json tmpListGoombas = ob.get("goombas");
		for(int i = 0; i < tmpList.size(); i++)
			sprites.add(new Pipe(tmpList.get(i)));
		for(int i = 0; i < tmpListGoombas.size(); i++)
			sprites.add(new Goomba(tmpListGoombas.get(i)));
	}

	public void addPipe(int x, int y)
	{
		boolean foundPipe = false;
		for(int i = 0; i < sprites.size(); i++)
		{
			if(sprites.get(i).isPipe()){
				if(((Pipe) sprites.get(i)).amIClickingOnYou(x,y))
				{
					foundPipe = true;
					sprites.remove(i);
				}
			}
		}

		if(!foundPipe)
			sprites.add(new Pipe(x,y));
	}

	public void addGoomba(int x, int y){ sprites.add(new Goomba(x, y)); }

	public void addFireball(int x, int y){ sprites.add(new Fireball(x, y)); }

	public boolean checkCollision(Sprite a, Sprite b)
	{
		//sprite's left > sprite right  not colliding
		if(b.x > (a.x + a.w))
			return false;
		//sprite's right < sprite's left  not colliding 
		if((b.x + b.w) < a.x)
			return false;
		//sprite's bottom < sprite's top
		if((b.y + b.h) < a.y)
			return false;
		//sprite's top > sprite's bottom 
		if(b.y > (a.y + a.h))
			return false;

		//if colliding 
		return true;
	}

	public void update()
	{
		ArrayList<Sprite> removeFireball = new ArrayList<Sprite>();

		for(int i = 0; i < sprites.size(); i++){
			sprites.get(i).update();
			if(sprites.get(i).isPipe()){
				boolean check = checkCollision((Pipe) sprites.get(i), mario);
				if(check) mario.getOutOfPipe((Pipe) sprites.get(i));
			}

			if(sprites.get(i).isGoomba()){
				for(int j=0; j < sprites.size(); j++){
					if(sprites.get(j).isPipe())
					{
						//check collision
						boolean check = checkCollision((Pipe) sprites.get(j),(Goomba) sprites.get(i));
						if(check)((Goomba)sprites.get(i)).getOutOfPipe((Pipe) sprites.get(j));
					}
					if(sprites.get(j).isFireball())
					{
						if(sprites.get(j).x > mario.x + 500)
							removeFireball.add(sprites.get(j));
						//check collision
						boolean check = checkCollision((Fireball) sprites.get(j),(Goomba) sprites.get(i));
						if(check)
						{
							//kill Goomba
							((Goomba)sprites.get(i)).onFire = true;
							removeFireball.add(sprites.get(j));
						}
					}
				}
			}
		}
		sprites.removeAll(removeFireball);
		//wait a few frames to remove Goomba
		for(int i = 0; i < sprites.size(); i++){
			if(sprites.get(i).isGoomba() && ((Goomba)sprites.get(i)).removeable())
				sprites.remove((Goomba)sprites.get(i));
		}
	}

}
