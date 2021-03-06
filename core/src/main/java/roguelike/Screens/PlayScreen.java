package roguelike.Screens;

import asciiPanel.AsciiPanel;
import roguelike.World.World;
import roguelike.levelBuilding.Tile;
import roguelike.utility.Point;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {
	
	public Point currentDirection = new Point(0, 0);
	
	private int screenWidth;
	private int screenHeight;
	private int mapHeight;
	private List <String> messages;
	private List <String> tempMessages;
	private Screen subscreen;
	private World world;
	
	public PlayScreen(){
		screenWidth = 88;
		screenHeight = 32;
		mapHeight = screenHeight - 4;
		messages = new ArrayList <String> ();
		tempMessages = new ArrayList <String> ();
		world = new World(screenWidth, mapHeight, messages);
		}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		displayTiles(terminal);
		displayMessages(terminal, messages);
		
		String health = String.format("Hp: %d/%d", world.getPlayer().currentHP(), world.getPlayer().maxHP());
		terminal.write(health, 0, mapHeight + 1);
		
		String stats = String.format("To Hit Bonus: +%d Damage: %dd%d+%d Armor: %d", 
				world.getPlayer().toHitBonus(), world.getPlayer().numOfDice(), 
				world.getPlayer().attackDamage(), 
				world.getPlayer().damageModifier(), world.getPlayer().armor());
		terminal.write(stats, 0, mapHeight + 2);
		
		if(subscreen != null){
			subscreen.displayOutput(terminal);
		}
		
	}
	
	public void displayMessages(AsciiPanel terminal, List <String> messages){
		for(int i = 0; i < messages.size() - 1; i++){
			if(messages.get(i).equals(messages.get(i + 1))){
				tempMessages.add(messages.get(i));
			}
		}
		for(String s : tempMessages){
			messages.remove(s);
		}
		
		for(int i = 0; i < messages.size() && mapHeight + i < terminal.getHeightInCharacters(); i++){
			terminal.writeCenter(messages.get(i), mapHeight + i);
		}
		if(subscreen == null){
			messages.clear();
			tempMessages.clear();
		}
	}
	
	private void displayTiles(AsciiPanel terminal) {
		for (int x = 0; x < screenWidth; x++){
	        for (int y = 0; y < mapHeight; y++){
	            terminal.write(world.getCurrentLevel().glyph(x, y), x, y, world.getCurrentLevel().color(x, y));
	            }
	        }
		terminal.write(world.getCurrentLevel().levelID, 0, mapHeight);
	}
	
	public void doAction(Point currentDirection){
		if(world.getCurrentLevel().isClosedDoor(world.getPlayer().x + currentDirection.x, world.getPlayer().y + currentDirection.y)){
			subscreen = new OpenDoorDialog(world.getCurrentLevel(), currentDirection.x, currentDirection.y);
		}
		else{
			world.getPlayer().move(currentDirection.x, currentDirection.y);
		}
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(subscreen != null){
			subscreen = subscreen.respondToUserInput(key);
		}
		else{
		switch (key.getKeyCode()){
		case KeyEvent.VK_ESCAPE: return new LoseScreen();
		case KeyEvent.VK_ENTER: return new WinScreen();
		case KeyEvent.VK_NUMPAD4:{ currentDirection = Point.WEST; doAction(currentDirection); break;}
		case KeyEvent.VK_NUMPAD6:{ currentDirection = Point.EAST; doAction(currentDirection); break;}
		case KeyEvent.VK_NUMPAD8:{ currentDirection = Point.NORTH; doAction(currentDirection); break;}
		case KeyEvent.VK_NUMPAD2:{ currentDirection = Point.SOUTH; doAction(currentDirection); break;}
		case KeyEvent.VK_NUMPAD7:{ currentDirection = Point.NORTH_WEST; doAction(currentDirection); break;}
		case KeyEvent.VK_NUMPAD9:{ currentDirection = Point.NORTH_EAST; doAction(currentDirection);	break;}
		case KeyEvent.VK_NUMPAD1:{ currentDirection = Point.SOUTH_WEST;	doAction(currentDirection);	break;}
		case KeyEvent.VK_NUMPAD3:{ currentDirection = Point.SOUTH_EAST;	doAction(currentDirection);	break;}
		case KeyEvent.VK_NUMPAD5:{ currentDirection = Point.WAIT; doAction(currentDirection); break;}
		default:{ currentDirection = Point.WAIT; doAction(currentDirection);}
		}
		
		switch(key.getKeyChar()){
		case 'd':{ subscreen = new DropScreen(world.getPlayer()); break;}
		case 'D':{ subscreen = new DrinkScreen(world.getPlayer()); break;}
		case 'i':{ subscreen = new EquipmentScreen(world.getPlayer()); break;}
		case 'I':{ subscreen = new InventoryScreen(world.getPlayer()); break;}
		case 'p':{ world.getPlayer().pickupItem(); break;}
		case '>':{
			if(world.getCurrentLevel().tile(world.getPlayer().x, world.getPlayer().y) == Tile.STAIRS_DOWN
					|| world.getCurrentLevel().tile(world.getPlayer().x, world.getPlayer().y) == Tile.CAVE){
				world.goDownALevel(); 
			}
			else{
				world.getPlayer().notify("There's no way to go down from here.");
			}
			break;
			}
		case '<':{ 
			if(world.getCurrentLevel().tile(world.getPlayer().x, world.getPlayer().y) == Tile.STAIRS_UP){
				world.goUpALevel(); 
			}
			else{
				world.getPlayer().notify("There's no way to go up from here.");
			}
			break;
			}
		}
		}
		
		if(subscreen == null && !key.isShiftDown()){ world.getCurrentLevel().update();}
		
		return this;
	}
}
