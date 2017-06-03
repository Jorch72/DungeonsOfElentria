package roguelike.Mob;

import java.awt.Color;

import roguelike.Items.Item;
import roguelike.Items.ItemFactory;
import roguelike.Level.Level;
import roguelike.utility.RandomGen;

public class Player extends BaseEntity{
	
	private int strength, constitution, dexterity, intelligence, wisdom, charisma, toHitBonus, damageModifier, numOfDice;
	private Item weapon, armor, helmet, leggings, boots, greaves;
	
	public Player(Level level, char glyph, Color color, int strength, int constitution, int dexterity, int intelligence, int wisdom, int charisma){
		super(level, glyph, color);
		setStrength(strength);
		setConstitution(constitution);
		setDexterity(dexterity);
		setIntelligence(intelligence);
		setWisdom(wisdom);
		setCharisma(charisma);
		setPlayerHP();
		setToHitBonus();
		setDodgeChance();
		setDamageModifier();
		setIsPlayer(true);
		setMaxCarryWeight(this.strength * 15);
		setInventory(this);
		setEquipment(this);
		initializeStartingGear();
	}
	
	public String helmetString(){ return this.helmet == null ? "" : this.helmet.name(); }
	public String armorString(){ return this.armor == null ? "" : this.armor.name(); }
	public String leggingsString(){ return this.leggings == null ? "" : this.leggings.name(); }
	public String weaponString(){ return this.weapon == null ? "" : this.weapon.name(); }
	public String greavesString(){ return this.greaves == null ? "" : this.greaves.name(); }
	public String bootsString(){ return this.boots == null ? "" : this.boots.name(); }
	
	public int strength(){ return this.strength; }
	public void setStrength(int strength){ this.strength = strength; }
	
	public int constitution(){ return this.constitution; }
	public void setConstitution(int constitution){ this.constitution = constitution; }
	
	public int dexterity(){ return this.dexterity; }
	public void setDexterity(int dexterity){ this.dexterity = dexterity; }
	
	public int intelligence(){ return this.intelligence; }
	public void setIntelligence(int intelligence){ this.intelligence = intelligence; }
	
	public int wisdom(){ return this.wisdom; }
	public void setWisdom(int wisdom){ this.wisdom = wisdom; }
	
	public int charisma(){ return this.charisma; }
	public void setCharisma(int charisma){ this.charisma = charisma; }
	
	public int damageModifier(){ return this.damageModifier; }
	public void setDamageModifier(){ this.damageModifier = this.strength / 2 - 5; }
	public void updateDamageModifier(int damageBonus){ this.damageModifier += damageBonus; }
	
	public int toHitBonus(){ return this.toHitBonus; }
	public void setToHitBonus(){ this.toHitBonus = this.strength / 2 - 5 + this.dexterity / 2 - 5; }
	public void updateToHitBonus(int update){ this.toHitBonus += update; }
	
	public void setDodgeChance(){ setDodge(this.dexterity / 2 - 5); }
	
	public void setNumOfDice(int numOfDice){ this.numOfDice = numOfDice; }
	public int numOfDice(){ return this.numOfDice; }
	
	public void setPlayerHP(){
		int maxHealth = this.constitution * 2;
		int random = RandomGen.rand(1, 100);
		
		if(random <= 10){ maxHealth -= 5; }
		else if(random > 10 && random <= 20){ maxHealth -= 4; }
		else if(random > 20 && random <= 30){ maxHealth -= 3; }
		else if(random > 30 && random <= 40){ maxHealth -= 2; }
		else if(random > 40 && random <= 50){ maxHealth -= 1; }
		else if(random > 50 && random <= 60){ maxHealth += 1; }
		else if(random > 60 && random <= 70){ maxHealth += 2; }
		else if(random > 70 && random <= 80){ maxHealth += 3; }
		else if(random > 80 && random <= 90){ maxHealth += 4; }
		else if(random > 90){ maxHealth += 5; }
		this.setMaxHP(maxHealth);
	}
	
	public void unequipWeapon(){
		equipment().remove(weapon);
		inventory().add(weapon);
		setNumOfDice(1);
		setAttack(3);
		updateToHitBonus(-weapon.toHit());
		updateDamageModifier(-weapon.attackBonus());
		this.weapon = null;
	}
	
	public void equipWeapon(Item weapon){ 
		if(inventory().contains(weapon)){
			inventory().remove(weapon);
		}
		this.weapon = weapon;
		setNumOfDice(weapon.numOfDice());
		setAttack(weapon.attackDamage());
		updateToHitBonus(weapon.toHit());
		updateDamageModifier(weapon.attackBonus());
		equipment().add(weapon);
	}
	
	public void unequipHelmet(){
		equipment().remove(helmet);
		inventory().add(helmet);
		updateArmor(-helmet.armor());
		updateToHitBonus(-helmet.toHit());
		updateDamageModifier(-helmet.attackBonus());
		this.helmet = null;
	}
	
	public void equipHelmet(Item helmet){ 
		if(inventory().contains(helmet)){
			inventory().remove(helmet);
		}
		this.helmet = helmet;
		updateArmor(helmet.armor());
		updateToHitBonus(helmet.toHit());
		updateDamageModifier(helmet.attackBonus());
		equipment().add(helmet);
		}
	
	public void unequipArmor(){
		equipment().remove(armor);
		inventory().add(armor);
		updateArmor(-armor.armor());
		updateToHitBonus(-armor.toHit());
		updateDamageModifier(-armor.attackBonus());
		this.armor = null;
	}
	
	public void equipArmor(Item armor){ 
		if(inventory().contains(armor)){
			inventory().remove(armor);
		}
		this.armor = armor; 
		updateArmor(armor.armor());
		updateToHitBonus(armor.toHit());
		updateDamageModifier(armor.attackBonus());
		equipment().add(armor);
		}
	
	public void unequipLeggings(){
		equipment().remove(leggings);
		inventory().add(leggings);
		updateArmor(-leggings.armor());
		updateToHitBonus(-leggings.toHit());
		updateDamageModifier(-leggings.attackBonus());
		this.leggings = null;
	}
	
	public void equipLeggings(Item leggings){ 
		if(inventory().contains(leggings)){
			inventory().remove(leggings);
		}
		this.leggings = leggings;
		updateArmor(leggings.armor());
		updateToHitBonus(leggings.toHit());
		updateDamageModifier(leggings.attackBonus());
		equipment().add(leggings);
		}
	
	public void unequipBoots(){
		equipment().remove(boots);
		inventory().add(boots);
		updateArmor(-boots.armor());
		updateToHitBonus(-boots.toHit());
		updateDamageModifier(-boots.attackBonus());
		this.boots = null;
	}
	
	public void equipBoots(Item boots){ 
		if(inventory().contains(boots)){
			inventory().remove(boots);
		}
		this.boots = boots;
		updateArmor(boots.armor());
		updateToHitBonus(boots.toHit());
		updateDamageModifier(boots.attackBonus());
		equipment().add(boots);
		}
	
	public void unequipGreaves(){
		equipment().remove(greaves);
		inventory().add(greaves);
		updateArmor(-greaves.armor());
		updateToHitBonus(-greaves.toHit());
		updateDamageModifier(-greaves.attackBonus());
		this.greaves = null;
	}
	
	public void equipGreaves(Item greaves){
		if(inventory().contains(greaves)){
			inventory().remove(greaves);
		}
		this.greaves = greaves;
		updateArmor(greaves.armor());
		updateToHitBonus(greaves.toHit());
		updateDamageModifier(greaves.attackBonus());
		equipment().add(greaves);
		}
	
	public void unequipItem(Item itemToUnequip){
		if(itemToUnequip.itemType().equals("weapon")){
			unequipWeapon();
		}
		else if(itemToUnequip.itemType().equals("helmet")){
			unequipHelmet();
		}
		else if(itemToUnequip.itemType().equals("chest")){
			unequipArmor();
		}
		else if(itemToUnequip.itemType().equals("legs")){
			unequipLeggings();
		}
		else if(itemToUnequip.itemType().equals("shins")){
			unequipGreaves();
		}
		else if(itemToUnequip.itemType().equals("feet")){
			unequipBoots();
		}
	}
	
	public void equipItem(Item itemToEquip){
		if(itemToEquip.itemType().equals("weapon")){
			equipWeapon(itemToEquip);
		}
		else if(itemToEquip.itemType().equals("helmet")){
			equipHelmet(itemToEquip);
		}
		else if(itemToEquip.itemType().equals("chest")){
			equipArmor(itemToEquip);
		}
		else if(itemToEquip.itemType().equals("legs")){
			equipLeggings(itemToEquip);
		}
		else if(itemToEquip.itemType().equals("shins")){
			equipGreaves(itemToEquip);
		}
		else if(itemToEquip.itemType().equals("feet")){
			equipBoots(itemToEquip);
		}
	}
	
	public Item weapon(){ return this.weapon; }
	public Item helmet(){ return this.helmet; }
	public Item chest(){ return this.armor; }
	public Item legs(){ return this.leggings; }
	public Item boots(){ return this.boots; }
	public Item greaves(){ return this.greaves; }
	
	public void initializeStartingGear(){
		ItemFactory startingItems = new ItemFactory(this.level());
		equipItem(startingItems.newItem("iron shortsword"));
		equipItem(startingItems.newItem("iron cap"));
		equipItem(startingItems.newItem("leather armor"));
		equipItem(startingItems.newItem("iron cuisses"));
		equipItem(startingItems.newItem("leather boots"));
		equipItem(startingItems.newItem("iron greaves"));
	}
	
	public void meleeAttack(BaseEntity otherEntity){ commonAttack(otherEntity, attackDamage(), otherEntity.name()); }
	
	private void commonAttack(BaseEntity otherEntity, int attackDamage, String otherName){
		int toHitRoll = RandomGen.rand(1, 100);
		int diceRoll = 0, tempDamage = 0;
		toHitRoll += toHitBonus();
		
		for(int numberOfDice = 0; numberOfDice < weapon().numOfDice(); numberOfDice++){
			diceRoll = RandomGen.rand(1, attackDamage);
			tempDamage += diceRoll;
		}
		tempDamage += damageModifier();
		int damageAmount = tempDamage - otherEntity.armor();
		String action;
		
		if(toHitRoll < otherEntity.dodge()){ action = "dodge"; doDeflectAction(action, otherEntity); }
		else if(damageAmount < 1){ action = "deflect"; doDeflectAction(action, otherEntity); }
		else{ action = "attack"; doAttackAction(action, otherEntity, damageAmount); otherEntity.modifyHP(damageAmount, "killed by a " + name()); }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
