package se.daniel.game.models;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.sun.tools.javac.util.Pair;

public class Curve extends Actor{
	private String keyLeft;
	private String keyRight;
	private Color color;
	private Table table;
	private double radians;
	private float speed;
	private boolean projectionMatrixSet;
	private ShapeRenderer shapeRenderer;
	private ArrayList<Pair<Float, Float>> tail;
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
    	setX(getX() + speed*delta* (float) Math.cos(radians));
    	setY(getY() + speed*delta* (float) Math.sin(radians));
    }
	
	private void setDefaultValues(int playerNbr){
		switch(playerNbr) {
		case 0:
			setKeyLeft("v");
			setKeyRight("b");
			setColor(Color.RED);
			break;
		case 1:
			setKeyLeft("a");
			setKeyRight("s");
			setColor(Color.BLUE);
			break;
		
		case 2:
			setKeyLeft("k");
			setKeyRight("l");
			setColor(Color.GREEN);
			break;
			
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
		table.add(new TextField(getName(), skin));
		
		table.add(new TextField(getKeyLeft(), skin));
		table.add(new TextField(getKeyRight(), skin));
		
	}

	public void setRadians(double radians) {
		this.radians = radians;
		
	}
	public double getRadians() {
		return this.radians;
	}
	

}
