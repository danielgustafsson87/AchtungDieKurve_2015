package se.daniel.game.models;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.sun.tools.javac.util.Pair;

public class Curve extends Actor{
	private int keyLeft;
	private int keyRight;
	private Table table;
	private double radians;
	private float speed;
	private boolean projectionMatrixSet;
	private ShapeRenderer shapeRenderer;
	private ArrayList<Pair<Float, Float>> tail;
	private boolean turningRight;
	private boolean turningLeft;
	public Curve(int playerNbr){
		super();
		shapeRenderer = new ShapeRenderer();
		projectionMatrixSet = false;
		table = new Table();
		setName("Player" + Integer.toString(playerNbr +1));
		tail = new ArrayList<Pair<Float, Float>>();
		setDebug(true);
		speed = 50;

		setDefaultValues(playerNbr);
		createTable();
		
	}
	
    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.end();
        if(!projectionMatrixSet){
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            projectionMatrixSet = true; //TODO: maybe we need new projection matrix each time?
        }
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(getColor());
        shapeRenderer.circle(getX(), getY(), 3.0f);
        for(Pair<Float, Float> tailPart : tail) {
        	shapeRenderer.circle(tailPart.fst, tailPart.snd, 3.0f);
        }
        shapeRenderer.end();
        
        batch.begin();
    }
    @Override
    public void act(float delta){
    	tail.add(new Pair<Float, Float>(getX(), getY()));
    	if (turningRight) {
    		radians -= Math.PI*0.5*delta;
    	}
    	if (turningLeft) {
    		radians += Math.PI*0.5*delta;
    	}
    	setX(getX() + speed*delta* (float) Math.cos(radians));
    	setY(getY() + speed*delta* (float) Math.sin(radians));
    }
	
	private void setDefaultValues(int playerNbr){
		switch(playerNbr) {
		case 0:
			setKeyLeft(Keys.A);
			setKeyRight(Keys.S);
			setColor(Color.RED);
			break;
		case 1:
			setKeyLeft(Keys.N);
			setKeyRight(Keys.M);
			setColor(Color.BLUE);
			break;
		
		case 2:
			setKeyLeft(Keys.K);
			setKeyRight(Keys.L);
			setColor(Color.GREEN);
			break;
			
		default:
			setKeyLeft(-1);
			setKeyRight(-1);
			
		}
	}

	public int getKeyRight() { 
		return keyRight;
	}

	public void setKeyRight(int keyRight) {
		this.keyRight = keyRight;
	}

	public int getKeyLeft() {
		return keyLeft;
	}

	public void setKeyLeft(int keyLeft) {
		this.keyLeft = keyLeft;
	}
	public Table getTable(){
		return table;
	}
	private void createTable() {
		Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		table.add(new TextField(getName(), skin));
		if (keyLeft != -1) {
			table.add(new TextField(Keys.toString(keyLeft), skin));
		} else {
			table.add(new TextField("Not set", skin));
		}
		if (keyRight != -1) {
			table.add(new TextField(Keys.toString(keyRight), skin));
		} else {
			table.add(new TextField("not set", skin));
		}
	}

	public void setRadians(double radians) {
		this.radians = radians;
		
	}
	public double getRadians() {
		return this.radians;
	}

	public boolean isTurningRight() {
		return turningRight;
	}

	public void setTurningRight(boolean turningRight) {
		this.turningRight = turningRight;
	}

	public boolean isTurningLeft() {
		return turningLeft;
	}

	public void setTurningLeft(boolean turningLeft) {
		this.turningLeft = turningLeft;
	}
	

}
