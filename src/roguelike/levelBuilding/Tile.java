package roguelike.levelBuilding;

import java.awt.Color;
import asciiPanel.AsciiPanel;

public enum Tile {
    FLOOR('.', AsciiPanel.brightGreen, "stone floor"),
    WALL('#', AsciiPanel.brightBlack, "wall"),
    PERM_WALL('#', AsciiPanel.brightBlack, "wall"),
    BOUNDS('X', AsciiPanel.black, ""),
	STAIRS_DOWN('>', AsciiPanel.white, "stairs down"),
    STAIRS_UP('<', AsciiPanel.white, "stairs up"),
	DOOR_CLOSED('+', AsciiPanel.white, "door"),
	DOOR_OPEN('/', AsciiPanel.white, "door");

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    private String name;
    public String details() { return name; }
    
    Tile(char glyph, Color color, String name){
        this.glyph = glyph;
        this.color = color;
        this.name = name;
    }
    
    public boolean canEnter(){
    	return this == Tile.DOOR_OPEN || this == Tile.STAIRS_DOWN || this == Tile.STAIRS_UP
    		|| this == Tile.FLOOR;
    }
    
    public boolean isDiggable() {
        return this == Tile.WALL;
    }
    
    public boolean isBounds(){
    	return this == BOUNDS;
    }
}
