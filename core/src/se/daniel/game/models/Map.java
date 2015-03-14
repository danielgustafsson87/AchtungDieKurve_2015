package se.daniel.game.models;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Map {
	
	public static final int SMALL = 1;
	public static final int MEDIUM = 2;
	public static final int LARGE = 3;
	public static final int EXTRA_LARGE = 4;
	public static final int MIN_SIZE = SMALL;
	public static final int MAX_SIZE = EXTRA_LARGE;
	private int mapSize;
	private int width;
	private int height;
	private Label label;
	
	
	public Map(Skin skin) {
		label = new Label("Medium", skin);
		setSize(MEDIUM);
	}
	
	public void increaseSize() {
		setSize(mapSize + 1);
	}
	public void decreaseSize() {
		setSize(mapSize - 1);
	}
	
	public void setSize(int size){
		switch (size) {
		case SMALL:
			mapSize = SMALL;
			width = 400;
			height = 320;
			label.setText("Small");
			return;
		case MEDIUM:
			mapSize = MEDIUM;
			width = 800;
			height = 640;
			label.setText("Medium");
			return;
		case LARGE:
			mapSize = LARGE;
			width = 1200;
			height = 960;
			label.setText("Large");
			return;
		case EXTRA_LARGE:
			mapSize = EXTRA_LARGE;
			width = 1600;
			height = 1280;
			label.setText("Extra Large");
			return;
		default:
			return;	
		}
	}
	public Label getLabel() {
		return label;
	}

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
