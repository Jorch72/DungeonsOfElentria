package roguelike.Screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import roguelike.Mob.Player;

public class EquipmentScreen implements Screen{
	public Player player;
	private Screen subscreen;
	//private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	
	public EquipmentScreen(Player player){
		this.player = player;
	}
	
	public void displayOutput(AsciiPanel terminal){
		
		String stats = String.format("Con: %s Str: %s Dex: %s Int: %s Wis: %s Cha: %s", player.constitution(), player.strength(), player.dexterity(), player.intelligence(), player.wisdom(), player.charisma());
		String weight = String.format("Currently Carrying: %s      Carrying Capacity: %s", player.currentCarryWeight(), player.maxCarryWeight());
		String helmet = String.format(" a - %-15s : - %s", "Helmet", player.helmetString());
		String armor = String.format(" b - %-15s : - %s", "Chest Piece", player.armorString());
		String rightHand = String.format(" c - %-15s : - %s", "Right Hand", player.weaponString());
		String legs = String.format(" d - %-15s : - %s", "Cuisses", player.leggingsString());
		String ankles = String.format(" e - %-15s : - %s", "Greaves", player.greavesString());
		String boots = String.format(" f - %-15s : - %s", "boots", player.bootsString());
		terminal.clear(' ', 0, 0, 88, 28);
		terminal.writeCenter(stats, 1);
		terminal.writeCenter(weight, 2);
		terminal.write(helmet, 0, 4);
		terminal.write(armor, 0, 5);
		terminal.write(rightHand, 0, 6);
		terminal.write(legs, 0, 7);
		terminal.write(ankles, 0, 8);
		terminal.write(boots, 0, 9);
		for(int i = 5; i < 83; i++){
			terminal.write('_', i, 3);
		}
		
		if(subscreen != null){
			subscreen.displayOutput(terminal);
		}
	}
	
	public Screen respondToUserInput(KeyEvent key){
		
		if(subscreen != null){
			subscreen = subscreen.respondToUserInput(key);
		}
		else{
		switch (key.getKeyCode()){
		case KeyEvent.VK_ESCAPE: {
			return null;
		}
		case KeyEvent.VK_A: {
			if(player.helmet() != null){
				player.unequipItem(player.helmet());
			}
			else{
				subscreen = new EquipScreen(player, "helmet");
			}
			break;
		}
		case KeyEvent.VK_B: {
			if(player.chest() != null){
				player.unequipItem(player.chest());
			}
			else{
				subscreen = new EquipScreen(player, "chest");
			}
			break;
		}
		case KeyEvent.VK_C: {
			if(player.weapon() != null){
				player.unequipItem(player.weapon());
			}
			else{
				subscreen = new EquipScreen(player, "weapon");
			}
			break;
		}
		case KeyEvent.VK_D: {
			if(player.legs() != null){
				player.unequipItem(player.legs());
			}
			else{
				subscreen = new EquipScreen(player, "legs");
			}
			break;
		}
		case KeyEvent.VK_E: {
			if(player.greaves() != null){
				player.unequipItem(player.greaves());
			}
			else{
				subscreen = new EquipScreen(player, "shins");
			}
			break;
		}
		case KeyEvent.VK_F: {
			if(player.boots() != null){
				player.unequipItem(player.boots());
			}
			else{
				subscreen = new EquipScreen(player, "feet");
			}
			break;
		}
		}
		}
		return this;
	}
}
