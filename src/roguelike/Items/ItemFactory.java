package roguelike.Items;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import asciiPanel.AsciiPanel;
import roguelike.Level.Level;
import roguelike.utility.RandomGen;

public class ItemFactory {
	public String itemFileName = "Items.txt";
	public Scanner itemFile = null;
	public Level thisLevel;
	public HashMap <String, Color> colorDictionary = new HashMap <String, Color> ();
	public HashMap <String, Item> itemDictionary = new HashMap <String, Item> ();
	public List <Item> itemList = new ArrayList <Item> ();
	
	public ItemFactory(Level level){
		initializeColors();
		initializeItemDictionary();
		this.thisLevel = level;
	}
	
	public void setThisLevel(Level level){ this.thisLevel = level; }
	
	public void initializeItemDictionary(){
		try{
			itemFile = openItemFile(itemFileName);
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		
		String name = null, itemType = null;
		char glyph = 0;
		Color color = null;
		int toHitBonus = 0, numOfDice = 0, attack = 0, attackBonus = 0, dodge = 0, armor = 0;
		double weight = 0.0;
		String[] tokens = null;
		
		while(itemFile.hasNextLine()){
			String tempLine = itemFile.nextLine();
			if((tempLine.isEmpty()) || (tempLine.contains("CHESTPIECES"))
				|| (tempLine.contains("HELMETS"))
				|| (tempLine.contains("WEAPONS"))
				|| (tempLine.contains("LEGGINGS"))
				|| (tempLine.contains("BRACERS"))
				|| (tempLine.contains("BOOTS"))){
				continue;
			}
			tokens = tempLine.split(":");
			if(!tokens[0].trim().equals("name")){
				name = tokens[0].trim();
				itemType = tokens[1].trim();
				glyph = tokens[2].trim().charAt(0);
				color = colorDictionary.get(tokens[3].trim());
				toHitBonus = Integer.parseInt(tokens[4].trim());
				numOfDice = Integer.parseInt(tokens[5].trim());
				attack = Integer.parseInt(tokens[6].trim());
				attackBonus = Integer.parseInt(tokens[7].trim());
				dodge = Integer.parseInt(tokens[8].trim());
				armor = Integer.parseInt(tokens[9].trim());
				weight = Double.parseDouble(tokens[10].trim());
				
				Item newItem = new Item(name, name, glyph, color, itemType, weight);
				newItem.setToHit(toHitBonus);
				newItem.setNumOfDice(numOfDice);
				newItem.setAttack(attack);
				newItem.setAttackBonus(attackBonus);
				newItem.setDodge(dodge);
				newItem.setArmor(armor);
				
				itemDictionary.put(name, newItem);
				itemList.add(newItem);
			}
		}
		
		itemFile.close();
	}
	
	public void initializeColors(){
		colorDictionary.put("brightGreen", AsciiPanel.brightGreen);
		colorDictionary.put("blue", AsciiPanel.brightBlue.brighter().brighter().brighter());
		colorDictionary.put("brightWhite", AsciiPanel.brightWhite);
		colorDictionary.put("darkGreen", AsciiPanel.green.brighter());
		colorDictionary.put("brown", new Color(139, 69, 19));
		colorDictionary.put("gray", new Color(211, 211, 211).darker());
	}
	
	public Item newItem(String itemName){
		try{
			itemFile = openItemFile(itemFileName);
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		
		String name = null, itemType = null;
		char glyph = 0;
		Color color = null;
		int toHitBonus = 0, numOfDice = 0, attack = 0, attackBonus = 0, dodge = 0, armor = 0;
		double weight = 0.0;
		String[] tokens = null;
		
		while(itemFile.hasNextLine()){
			String tempLine = itemFile.nextLine();
			tokens = tempLine.split(":");
			if(tokens[0].trim().equals(itemName)){
				name = tokens[0].trim();
				itemType = tokens[1].trim();
				glyph = tokens[2].trim().charAt(0);
				color = colorDictionary.get(tokens[3].trim());
				toHitBonus = Integer.parseInt(tokens[4].trim());
				numOfDice = Integer.parseInt(tokens[5].trim());
				attack = Integer.parseInt(tokens[6].trim());
				attackBonus = Integer.parseInt(tokens[7].trim());
				dodge = Integer.parseInt(tokens[8].trim());
				armor = Integer.parseInt(tokens[9].trim());
				weight = Double.parseDouble(tokens[10].trim());
			}
		}
		
		Item newItem = new Item(name, name, glyph, color, itemType, weight);
		newItem.setToHit(toHitBonus);
		newItem.setNumOfDice(numOfDice);
		newItem.setAttack(attack);
		newItem.setAttackBonus(attackBonus);
		newItem.setDodge(dodge);
		newItem.setArmor(armor);
		itemFile.close();
		return newItem;
		
	}
	
	public void newItemAtRandomLocation(){
		int roll = RandomGen.rand(0, itemDictionary.size() - 1);
		this.thisLevel.addAtEmptyLocation(newItem(itemList.get(roll).name()));
	}
	
	private static Scanner openItemFile(String fileName) throws FileNotFoundException{
		File file = new File(fileName);
		Scanner sc = new Scanner(file);
		return sc;
	}
}
