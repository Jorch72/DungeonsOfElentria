package roguelike.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import roguelike.Mob.BaseEntity;

public class PathFinder {
    private ArrayList<Node> open;
    private ArrayList<Node> closed;
    private HashMap<Node, Node> parents;
	private HashMap<Node,Integer> totalCost;
    
    public PathFinder() {
    	this.open = new ArrayList<Node>();
        this.closed = new ArrayList<Node>();
        this.parents = new HashMap<Node, Node>();
        this.totalCost = new HashMap<Node, Integer>();
    }
    
    private int heuristicCost(Node from, Node to) {
        return Math.max(Math.abs(from.x - to.x), Math.abs(from.y - to.y));
    }

    private int costToGetTo(Node from) {
        return parents.get(from) == null ? 0 : (1 + costToGetTo(parents.get(from)));
    }
    
    private int totalCost(Node from, Node to) {
        if (totalCost.containsKey(from))
        	return totalCost.get(from);
        
        int cost = costToGetTo(from) + heuristicCost(from, to);
        totalCost.put(from, cost);
        return cost;
    }

    private void reParent(Node child, Node parent){
        parents.put(child, parent);
        totalCost.remove(child);
    }

    public ArrayList<Node> findPath(BaseEntity creature, Node start, Node end, int maxTries) {
        open.clear();
        closed.clear();
        parents.clear();
        totalCost.clear();
    	
        open.add(start);
        
        for (int tries = 0; tries < maxTries && open.size() > 0; tries++){
            Node closest = getClosestPoint(end);
            
            open.remove(closest);
            closed.add(closest);

            if (closest.equals(end))
                return createPath(start, closest);
            else
                checkNeighbors(creature, end, closest);
        }
        return null;
    }

	private Node getClosestPoint(Node end) {
		Node closest = open.get(0);
		for (Node other : open){
		    if (totalCost(other, end) < totalCost(closest, end))
		        closest = other;
		}
		return closest;
	}

	private void checkNeighbors(BaseEntity creature, Node end, Node closest) {
		for (Node neighbor : closest.neighbors8()) {
		    if (closed.contains(neighbor)
		    		|| !creature.canEnter(neighbor.x, neighbor.y)
		    		&& !neighbor.equals(end))
		        continue;
			
		    if (open.contains(neighbor))
				reParentNeighborIfNecessary(closest, neighbor);
		    else
		        reParentNeighbor(closest, neighbor);
		}
	}

	private void reParentNeighbor(Node closest, Node neighbor) {
		reParent(neighbor, closest);
		open.add(neighbor);
	}

	private void reParentNeighborIfNecessary(Node closest, Node neighbor) {
		Node originalParent = parents.get(neighbor);
		double currentCost = costToGetTo(neighbor);
		reParent(neighbor, closest);
		double reparentCost = costToGetTo(neighbor);
		
		if (reparentCost < currentCost)
			open.remove(neighbor);
		else
			reParent(neighbor, originalParent);
	}

	private ArrayList<Node> createPath(Node start, Node end) {
		ArrayList<Node> path = new ArrayList<Node>();

		while (!end.equals(start)) {
		    path.add(end);
		    end = parents.get(end);
		}

		Collections.reverse(path);
		return path;
	}
}
