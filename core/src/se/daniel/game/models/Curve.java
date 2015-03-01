package se.daniel.game.models;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Curve extends Actor{
	private String keyLeft;
	private String keyRight;
	private String name;
	
	public Curve(int playerNbr){
		super();
		name = "Player + " + Integer.toString(playerNbr);
		setDefaultKeys(playerNbr);
	}
	
	private void setDefaultKeys(int playerNbr){
		switch(playerNbr) {
		case 1:
			setKeyLeft("a");
			setKeyRight("s");
		
		case 2:
			setKeyLeft("k");
			setKeyRight("l");
			
		default:
			setKeyLeft(null);
			setKeyRight(null);
			
		}
	}

	public String getKeyRight() {
		if (keyRight == null) {
			return "not set";
		}
		return keyRight;
	}

	public void setKeyRight(String keyRight) {
		this.keyRight = keyRight;
	}

	public String getKeyLeft() {
		if (keyLeft == null) {
			return "not set";
		}
		return keyLeft;
	}

	public void setKeyLeft(String keyLeft) {
		this.keyLeft = keyLeft;
	}

}
