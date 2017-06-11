package roguelike.utility;

import java.util.List;

import roguelike.Mob.BaseEntity;

public class Path {
	public static PathFinder pathFinder = new PathFinder();
	
	public List <Node> nodes;
	public List <Node> nodes(){
		return nodes;
	}
	
	public Path(BaseEntity mob, int x, int y){
		nodes = pathFinder.findPath(mob, new Node(mob.x, mob.y), new Node(x, y), 300);
	}
}
