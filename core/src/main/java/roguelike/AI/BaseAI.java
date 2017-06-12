package roguelike.AI;

import roguelike.Level.Level;
import roguelike.Mob.BaseEntity;
import roguelike.levelBuilding.Door;
import roguelike.utility.Line;
import roguelike.utility.Node;
import roguelike.utility.RandomGen;
import squidpony.squidai.DijkstraMap;
import squidpony.squidmath.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BaseAI {
	protected BaseEntity mob;
	public Door door;
	public List <Node> vision = new ArrayList <Node> ();
	public DijkstraMap path;

	public BaseAI(BaseEntity mob){
		this.mob = mob;
		this.mob.setMobAi(this);
		path = new DijkstraMap(mob.level().pathMap, DijkstraMap.Measurement.EUCLIDEAN);
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
		path.setGoal(target.x, target.y);
		ArrayList <Coord> coords;// = new ArrayList <Coord> ();
		// a Set is just like a List but always has unique items and is fast when checking if it contains something
		Set<Coord> blocked = mob.level().mobs.keysAsOrderedSet(); // normally we'd use .keySet() here, but
		// keySet() returns a weird Set that, while faster, doesn't allow removal of items. This gets a copy.
		// We can remove stuff from the copy.
		/*coords = path.findPath(8, -1, null, null, Coord.get(mob.x, mob.y), Coord.get(target.x, target.y));
		int myX = coords.get(0).x - mob.x;
		int myY = coords.get(0).y - mob.y;*/
		blocked.remove(Coord.get(mob.level().player.x, mob.level().player.y));
		/*
		for(BaseEntity entity : mob.level().mobs) {
			if(!entity.isPlayer()) {
				blocked.add(Coord.get(entity.x, entity.y));
			}
		}
		*/

		coords = path.findPath(8, blocked, null, Coord.get(mob.x, mob.y), Coord.get(target.x, target.y));
		if(coords == null || coords.isEmpty())
			return;
		int myX = coords.get(0).x - mob.x;
		int myY = coords.get(0).y - mob.y;

		mob.move(myX, myY);
		/*List <Node> nodes = new Path(this.mob, target.x, target.y).nodes();

		if(nodes.isEmpty()){
			System.out.println("Oops!");
		}

		int myX = nodes.get(0).x - mob.x;
		int myY = nodes.get(0).y - mob.y;

		mob.move(myX, myY);*/
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
			level.mobs.alter(Coord.get(mob.x, mob.y), Coord.get(x, y));
			mob.x = x;
			mob.y = y;
		}
	}

	public void onUpdate(){}

}