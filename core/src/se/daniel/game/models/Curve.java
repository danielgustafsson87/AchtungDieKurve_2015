package se.daniel.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class Curve extends Actor{
	private String keyLeft;
	private String keyRight;
	private Color color;
	private Table table = new Table();
	public Curve(int playerNbr){
		super();
		setName("Player" + Integer.toString(playerNbr +1));
		setDefaultKeys(playerNbr);
		createTable();
	}
	
	private void setDefaultKeys(int playerNbr){
		switch(playerNbr) {
		case 0:
			setKeyLeft("v");
			setKeyRight("b");
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
	public Table getTable(){
		return table;
	}
	private void createTable() {
		Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		table.add(new TextField(getName(), skin)).left();
		table.padLeft(100);
		table.add(new TextField(getKeyLeft(), skin));
		table.add(new TextField(getKeyRight(), skin));
		
		
		
	}
	

}
