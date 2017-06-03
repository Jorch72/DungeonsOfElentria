package roguelike.AI;

import roguelike.levelBuilding.*;
import roguelike.utility.*;

import java.util.ArrayList;
import java.util.List;

import roguelike.Level.Level;
import roguelike.Mob.BaseEntity;

public class BaseAI {
	protected BaseEntity mob;
	public Door door;
	public List <Node> vision = new ArrayList <Node> ();
	
	public BaseAI(BaseEntity mob){
		this.mob = mob;
		this.mob.setMobAi(this);
	}
	
	public boolean canSee(int wx, int wy) {
	
		if ((mob.x-wx)*(mob.x-wx) + (mob.y-wy)*(mob.y-wy) > mob.visionRadius()*mob.visionRadius())
			return false;
		
		for (Node p : new Line(mob.x, mob.y, wx, wy)){
			if (mob.realTile(p.x, p.y).canEnter() || p.x == wx && p.y == wy)
				continue;
			
			return false;
		}
		
		return true;
	}
	
	public void hunt(BaseEntity target){
		List <Node> nodes = new Path(this.mob, target.x, target.y).nodes();
		
		if(nodes.isEmpty()){
			System.out.println("Oops!");
		}
		
		int myX = nodes.get(0).x - mob.x;
		int myY = nodes.get(0).y - mob.y;
		
		mob.move(myX, myY);
	}
	
	public void wander(){
		int x = RandomGen.rand(-1, 1);
		int y = RandomGen.rand(-1, 1);
		BaseEntity otherEntity = mob.level().checkForMob(mob.x + x, mob.y + y);
		if(otherEntity != null || !mob.level().isGround(mob.x + x, mob.y + y)){
			return;
		}
		else{
			mob.move(x, y);
		}	
	}
	
	public void onNotify(String message){}
	
	public void onEnter(int x, int y, Level level){
		if(level.isGround(x, y)){
			mob.x = x;
			mob.y = y;
		}
		else{
			return;
		}
	}
	
	public void onUpdate(){}
	
}
