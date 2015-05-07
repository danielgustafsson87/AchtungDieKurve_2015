package se.daniel.game.models;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.math.MathUtils;
public class Map {
	public static final int EXTRA_SMALL = 1;
	public static final int SMALL =2;
	public static final int MEDIUM = 3;
	public static final int LARGE = 4;
	public static final int EXTRA_LARGE = 5;
	public static final int MIN_SIZE = EXTRA_SMALL;
	public static final int MAX_SIZE = EXTRA_LARGE;
	private int mapSize;
	private int width;
	private int height;
	private Label label;
	private int speed;
	private float radius;
	
	
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
		case EXTRA_SMALL:
			mapSize = EXTRA_SMALL;
			width = 400;
			height = 320;
			label.setText("Extra Small");
			speed = 160;
			radius = 8.0f;
		case SMALL:
			mapSize = SMALL;
			width = 400;
			height = 320;
			label.setText("Extra Small");
			speed = 120;
			radius = 6.0f;
			
			return;
		case MEDIUM:
			mapSize = MEDIUM;
			width = 800;
			height = 640;
			speed = 60;
			radius = 3.0f;
			label.setText("Medium");
			return;
		case LARGE:
			mapSize = LARGE;
			width = 1200;
			height = 960;
			speed = 40;
			radius = 2.0f;
			
			label.setText("Large");
			return;
		case EXTRA_LARGE:
			mapSize = EXTRA_LARGE;
			width = 1600;
			height = 1280;
			speed = 30;
			radius = 1.5f;
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

	public int getSize() {
		return mapSize;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
}
