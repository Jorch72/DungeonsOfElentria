package roguelike.Items;

import java.awt.Color;

import roguelike.modifiers.*;

public class Potion extends BaseItem{
	
	public Potion(String name, char glyph, Color color, String itemType, double weight, String effectName){
		super(name, glyph, color, itemType, weight);
		setEffect(effectName);
	}
	
	public void setEffect(String effectName){
		if(effectName.equals("weak poison")){
			setEffect(new Poison(1, 10));
		}
		else if(effectName.equals("strong poison")){
			setEffect(new Poison(2, 10));
		}
		else if(effectName.equals("weak healing")){
			setEffect(new Healing(10, 1));
		}
	}
}
