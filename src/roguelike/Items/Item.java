package roguelike.Items;

import java.awt.Color;

public class Item implements Comparable <Item>{
	private char glyph;
	private Color color;
	private String name, appearance, itemType;
	private int toHitBonus, numOfDice, attack, attackBonus, armor, dodge;
	public int x, y;
	private boolean isIdentified;
	private double weight;
	
	public Item(String name, String appearance, char glyph, Color color, String itemType, double weight){
		this.glyph = glyph;
		this.color = color;
		this.name = name;
		this.appearance = appearance;
		this.itemType = itemType;
		this.isIdentified = false;
		this.weight = weight;
	}
	
	public String details(){
		if(isIdentified){
			return this.name;
		}
		else{
			return this.appearance;
		}
	}
	
	@Override
	public int compareTo(Item otherItem){
		return this.name().compareToIgnoreCase(otherItem.name());
	}
	
	public char glyph(){ return this.glyph; }
	public Color color(){ return this.color; }
	public String name(){ return this.name; }
	public String appearance(){ return this.appearance; }
	public double weight(){ return this.weight; }
	public String itemType(){ return this.itemType; }
	
	public void setToHit(int bonus){ this.toHitBonus = bonus; }
	public void modifyToHit(int bonus){ this.toHitBonus += bonus; }
	public int toHit(){ return this.toHitBonus; }
	
	public void setNumOfDice(int numOfDice){ this.numOfDice = numOfDice; }
	public void modifyNumOfDice(int modification){ this.numOfDice += modification; }
	public int numOfDice(){ return this.numOfDice; }
	
	public void modifyAttack(int bonus){ this.attack += bonus; }
	public void setAttack(int bonus){ this.attack = bonus; }
	public int attackDamage(){ return this.attack; }
	
	public void modifyAttackBonus(int attackBonus){ this.attackBonus += attackBonus; }
	public void setAttackBonus(int attackBonus){ this.attackBonus = attackBonus; }
	public int attackBonus(){ return this.attackBonus; }
	
	public void modifyArmor(int bonus){ this.armor += bonus; }
	public void setArmor(int bonus){ this.armor = bonus; }
	public int armor(){ return this.armor; }
	
	public void modifyDodge(int bonus){ this.dodge += bonus; }
	public void setDodge(int bonus){ this.dodge = bonus; }
	public int dodge(){ return this.dodge; }
	
}
