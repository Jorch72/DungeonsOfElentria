package roguelike.Mob;

import roguelike.Level.Level;

import java.awt.*;

public class EnemyEntity extends BaseEntity{
	
	public EnemyEntity(Level level, char glyph, Color color){ 
		super(level, glyph, color);
		setMaxCarryWeight(9999);
		setInventory(this);
	}
}
