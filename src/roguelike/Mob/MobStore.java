package roguelike.Mob;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import asciiPanel.AsciiPanel;
import roguelike.AI.GoblinAi;
import roguelike.AI.playerAi;
import roguelike.Level.Level;

public class MobStore {
	public String mobFileName = "Mobs.txt";
	public String playerFileName = "HumanPlayer.txt";
	public Scanner playerFile = null;
	public Scanner mobFile = null;
	public Level thisLevel;
	public HashMap <String, Color> colorDictionary = new HashMap <String, Color> ();
	public List <String> messages;
	
	public void initializeColors(){
		colorDictionary.put("brightGreen", AsciiPanel.brightGreen);
		colorDictionary.put("blue", AsciiPanel.brightBlue.brighter().brighter().brighter());
		colorDictionary.put("brightWhite", AsciiPanel.brightWhite);
		colorDictionary.put("darkGreen", AsciiPanel.green.brighter());
	}
	
	public MobStore(Level level, List <String> messages){
		initializeColors();
		this.messages = messages;
		this.thisLevel = level;
	}
	
	public void setLevel(Level level){
		this.thisLevel = level;
	}
	
	public Player newPlayer(){
		Player newPlayer = newPlayer("@");
		new playerAi(newPlayer, messages);
		return newPlayer;
	}
	
	public EnemyEntity newGoblin(){
		EnemyEntity newGoblin = newMob("goblin");
		new GoblinAi(newGoblin, this.thisLevel.player);
		return newGoblin;
	}
	
	public EnemyEntity newGoblinCaptain(){
		EnemyEntity newGoblinCaptain = newMob("goblin captain");
		new GoblinAi(newGoblinCaptain, this.thisLevel.player);
		return newGoblinCaptain;
	}
	
	public EnemyEntity newGoblinWarrior(){
		EnemyEntity newGoblinWarrior = newMob("goblin warrior");
		new GoblinAi(newGoblinWarrior, this.thisLevel.player);
		return newGoblinWarrior;
	}
	
	public EnemyEntity newElena(){
		EnemyEntity newElena = newMob("Elena");
		new GoblinAi(newElena, this.thisLevel.player);
		return newElena;
	}
	
	public Player newPlayer(String playerSymbol){
		try{
			playerFile = openMobFile(playerFileName);
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		char glyph = 0;
		Color color = null;
		int strength = 0, constitution = 0, dexterity = 0, intelligence = 0, wisdom = 0, charisma = 0;
		String[] tokens;
		while(playerFile.hasNextLine()){
			String tempLine = playerFile.nextLine();
			tokens = tempLine.split(":");
			if(tokens[0].trim().equals("@")){
				glyph = tokens[0].trim().charAt(0);
				color = colorDictionary.get(tokens[1].trim());
				strength = Integer.parseInt(tokens[2].trim());
				constitution = Integer.parseInt(tokens[3].trim());
				dexterity = Integer.parseInt(tokens[4].trim());
				intelligence = Integer.parseInt(tokens[5].trim());
				wisdom = Integer.parseInt(tokens[6].trim());
				charisma = Integer.parseInt(tokens[7].trim());
			}
		}
		Player newPlayer = new Player(this.thisLevel, glyph, color, strength, constitution, dexterity, intelligence, wisdom, charisma);
		this.thisLevel.addAtEmptyLocation(newPlayer);
		return newPlayer;
	}
	
	public EnemyEntity newMob(String mobName){
		try{
			mobFile = openMobFile(mobFileName);
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		String name = null;
		char glyph = 0;
		int HP = 0, attack = 0, armor = 0, dodge = 0;
		Color color = null;
		boolean isPlayer = false;
		String[] tokens = null;
		while(mobFile.hasNextLine()){
			String tempName = mobFile.nextLine();
			tokens = tempName.split(":");
			if(tokens[0].trim().equals(mobName)){
				name = tokens[0].trim();
				glyph = tokens[1].trim().charAt(0);
				color = colorDictionary.get(tokens[2].trim());
				HP = Integer.parseInt(tokens[3].trim());
				attack = Integer.parseInt(tokens[4].trim());
				armor = Integer.parseInt(tokens[5].trim());
				dodge = Integer.parseInt(tokens[6].trim());
			}
		}
		EnemyEntity newMob = new EnemyEntity(this.thisLevel, glyph, color);
		newMob.setName(name);
		newMob.setMaxHP(HP);
		newMob.setAttack(attack);
		newMob.setArmor(armor);
		newMob.setDodge(dodge);
		newMob.setIsPlayer(isPlayer);
		newMob.setVisionRadius(10);
		this.thisLevel.addAtEmptyLocation(newMob);
		mobFile.close();
		return newMob;
	}
	
	private static Scanner openMobFile(String fileName) throws FileNotFoundException{
		File file = new File(fileName);
		Scanner sc = new Scanner(file);
		return sc;
	}
}
