package roguelike.Items;

import java.awt.Color;

public class BaseItem implements ItemInterface, Comparable <BaseItem>{
	private String name, appearance, itemType;
	private char glyph;
	private Color color;
	private int armorValue, dodgeValue, toHitBonus, damageValue;
	private double weight;
	
	public BaseItem(String name, char glyph, Color color, String itemType, double weight){
		this.name = name;
		this.glyph = glyph;
		this.color = color;
		this.itemType = itemType;
		this.weight = weight;
	}
	
	public String name(){ return this.name; }
	public String details(){ return this.name(); }
	public String itemType(){ return this.itemType; }
	public char glyph(){ return this.glyph; }
	public Color color(){ return this.color; }
	public double weight(){ return this.weight; }
	
	@Override
	public int compareTo(BaseItem otherItem){ return this.name().compareToIgnoreCase(otherItem.name()); }
}
