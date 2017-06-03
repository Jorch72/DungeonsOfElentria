package roguelike.World;
import java.util.*;

import roguelike.Items.ItemFactory;
import roguelike.Level.Level;
import roguelike.Mob.MobStore;
import roguelike.Mob.Player;
import roguelike.utility.RandomGen;

public class World {
	public int screenWidth, mapHeight;
	private Player player;
	private MobStore mobStore;
	private ItemFactory itemStore;
	private Level currentLevel;
	public List <String> messages = new ArrayList <String> ();
	private HashMap <Integer, Level> levels = new HashMap <Integer, Level> ();
	
	public MobStore getMobStore() {return mobStore;}
	public void setMobStore(MobStore mobStore) {this.mobStore = mobStore;}

	public ItemFactory getItemStore() {return itemStore;}
	public void setItemStore(ItemFactory itemStore) {this.itemStore = itemStore;}

	public Level getCurrentLevel() {return currentLevel;}
	public void setCurrentLevel(Level currentLevel) {this.currentLevel = currentLevel;}
	
	public World(int screenWidth, int mapHeight, List <String> messages){
		initializeWorld(screenWidth, mapHeight, messages);
	}

	public Player getPlayer() {return player;}
	public void setPlayer(Player player) {this.player = player;}
	
	public void initializeWorld(int screenWidth, int mapHeight, List <String> messages){
		this.screenWidth = screenWidth;
		this.mapHeight = mapHeight;
		this.messages = messages;
		currentLevel = new  Level(screenWidth, mapHeight);
		currentLevel.buildLevel();
		mobStore = new MobStore(currentLevel, messages);
		itemStore = new ItemFactory(currentLevel);
		player = mobStore.newPlayer();
		currentLevel.setPlayer(player);
		currentLevel.levelNumber = 1;
		initializeMobsOnLevel();
		createRandomItems();
		levels.put(currentLevel.levelNumber, currentLevel);
	}
	
	public void goUpALevel(){
		if(levels.containsKey(getCurrentLevel().levelNumber - 1)){
			levels.get(currentLevel.levelNumber - 1).mobs.add(player);
			player.setLevel(levels.get(currentLevel.levelNumber - 1));
			levels.get(currentLevel.levelNumber).mobs.remove(player);
			setCurrentLevel(levels.get(getCurrentLevel().levelNumber - 1));
			getCurrentLevel().setPlayer(player);
			getCurrentLevel().addAtEmptyLocation(player);
		}
		else{
			player.notify("There's no way up from here.");
		}
	}
	
	public void goDownALevel(){
		if(levels.containsKey(currentLevel.levelNumber + 1)){
			levels.get(currentLevel.levelNumber + 1).mobs.add(player);
			player.setLevel(levels.get(currentLevel.levelNumber + 1));
			levels.get(currentLevel.levelNumber).mobs.remove(player);
			setCurrentLevel(levels.get(getCurrentLevel().levelNumber + 1));
			getCurrentLevel().setPlayer(player);
			getCurrentLevel().addAtEmptyLocation(player);
		}
		else{
			Level tempLevel = new Level(screenWidth, mapHeight);
			tempLevel.buildLevel();
			mobStore = new MobStore(tempLevel, messages);
			itemStore = new ItemFactory(tempLevel);
			tempLevel.setPlayer(player);
			tempLevel.addAtEmptyLocation(player);
			player.setLevel(tempLevel);
			tempLevel.mobs.add(player);
			currentLevel.remove(player);
			tempLevel.levelNumber = currentLevel.levelNumber + 1;
			setCurrentLevel(tempLevel);
			initializeMobsOnLevel();
			createRandomItems();
			levels.put(currentLevel.levelNumber, currentLevel);
		}
	}
	
	public void createRandomItems(){
		for(int i = 0; i < 10; i++){
			itemStore.newItemAtRandomLocation();
		}
	}
	
	public void initializeMobsOnLevel(){
		for(int i = 0; i < 25; i++){
			int x = RandomGen.rand(1, 100);
			if(x <= 2){
				mobStore.newElena();
			}
			else if((x >= 3) && (x <= 20)){
				int y = RandomGen.rand(1, 100);
				if(y <= 50){
					mobStore.newGoblinCaptain();
				}
				else{
					mobStore.newGoblinWarrior();
				}
			}
			else{
				mobStore.newGoblin();
			}
		}
	}
}
