package roguelike.Screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class StartScreen implements Screen {
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("Dungeons of Elentria", 2);
        terminal.writeCenter("-- press [enter] to start --", 24);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}
