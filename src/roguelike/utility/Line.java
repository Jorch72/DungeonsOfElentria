package roguelike.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Line implements Iterable<Node> {
	private List<Node> points;
	public List<Node> getPoints() { return points; }
	
	public Line(int x0, int y0, int x1, int y1) {
		points = new ArrayList<Node>();
		
		int dx = Math.abs(x1-x0);
		int dy = Math.abs(y1-y0);
		
		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;
		int err = dx-dy;
		
		while (true){
			points.add(new Node(x0, y0));
			
			if (x0==x1 && y0==y1)
				break;
			
			int e2 = err * 2;
			if (e2 > -dx) {
				err -= dy;
				x0 += sx;
			}
			if (e2 < dx){
				err += dx;
				y0 += sy;
			}
		}
	}

	@Override
	public Iterator<Node> iterator() {
		return points.iterator();
	}
}
