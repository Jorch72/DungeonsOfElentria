package roguelike.utility;

import java.util.ArrayList;
import java.util.Collections;

public class Node {
	public int x;
	public int y;
	
	public Node(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public boolean equals(Object obj){
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Node)){
			return false;
		}
		Node other = (Node) obj;
		if(x != other.x){
			return false;
		}
		if(y != other.y){
			return false;
		}
		return true;
	}
	
	public ArrayList <Node> neighbors8(){
		ArrayList <Node> neighbors = new ArrayList <Node> ();
		
		for(int x = -1; x <= 1; x++){
			for(int y = -1; y <= 1; y++){
				if(x == 0 && y == 0){
					continue;
				}
				neighbors.add(new Node(this.x + x, this.y + y));
			}
		}
		
		Collections.shuffle(neighbors);
		return neighbors;
	}
	
}
