package se.daniel.game.models;

import java.util.ArrayList;
import java.util.Random;

import se.daniel.game.screens.GameScreen;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.sun.tools.javac.util.Pair;

public class Curve extends Actor{
	private int keyLeft;
	private int keyRight;
	private Table table;
	private double radians;
	
	private static final double TURNING_SPEED = Math.PI * 0.7;
	private  float speed = 60;
	private  float radius = 3.0f;
	
	private boolean makesHole = false;
	private boolean projectionMatrixSet;
	private ShapeRenderer shapeRenderer;
	private ArrayList<Pair<Float, Float>> tail;
	private boolean turningRight;
	private boolean turningLeft;
	private boolean alive = true;
	private int score = 0; 
	private Label scoreLabel;
	
	private long holeStartTime = 0;
	private long HOLE_LENGTH = 350;
	
	public Curve(int playerNbr){
		super();
		shapeRenderer = new ShapeRenderer();
		projectionMatrixSet = false;
		table = new Table();
		
		setName("Player" + Integer.toString(playerNbr +1));
		tail = new ArrayList<Pair<Float, Float>>();

		setDefaultValues(playerNbr);
		scoreLabel = new Label(Integer.toString(score), new Skin(Gdx.files.internal("skins/uiskin.json")));
		scoreLabel.setColor(getColor());
		scoreLabel.setFontScale(3);
		createTable();
		setDebug(true);
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
        shapeRenderer.circle(getX(), getY(), radius);
        for(Pair<Float, Float> tailPart : tail) {
        	shapeRenderer.circle(tailPart.fst, tailPart.snd, radius);
        }
        /*
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(Math.round(getX() + (RADIUS +1) * (float) Math.cos(radians)),
				  Math.round(getY() + (RADIUS +1) * (float) Math.sin(radians)), 1);

		shapeRenderer.circle(Math.round(getX() + (RADIUS +1) * (float) Math.cos(radians + Math.PI/2)),
				  Math.round(getY() + (RADIUS +1) * (float) Math.sin(radians + Math.PI/2)), 1);

		shapeRenderer.circle(Math.round(getX() + (RADIUS +1) * (float) Math.cos(radians - Math.PI/2)),
				  Math.round(getY() + (RADIUS +1) * (float) Math.sin(radians - Math.PI/2)) , 1);
        
        */
        shapeRenderer.end();
        batch.begin();
    }
    @Override
    public void act(float delta){
    	if(!alive) {
    		return;
    	}
    	// if hole was made. decide next hole
    	if (holeStartTime + HOLE_LENGTH < System.currentTimeMillis()) {
    		Random rnd = new Random();
    		holeStartTime = System.currentTimeMillis() + 1000 + rnd.nextInt(4000);
    	}
    	

    	if (turningRight) {
    		radians -= TURNING_SPEED*delta;
    	}
    	if (turningLeft) {
    		radians += TURNING_SPEED*delta;
    	}
    	float oldX = getX();
    	float oldY = getY();
    	setX(getX() + speed*delta* (float) Math.cos(radians));
    	setY(getY() + speed*delta* (float) Math.sin(radians));
    	
    	//TODO: go to screen coordinates
    	if (Math.round(oldX) != Math.round(getX()) || Math.round(oldY) != Math.round(getY())) {
        	// No need to print subpixel precision for this game.
    		if(System.currentTimeMillis() < holeStartTime) {
        		tail.add(new Pair<Float, Float>(getX(), getY()));
        	}
        	// Only needs to check collision at new pixel. Otherwise just collides with ourself
	    	if (checkCollision()) {
	    		alive = false;
	    		givePoints();
	    		
	    	}
    	}
    }
	
	public void givePoints() {
		for (Curve curve : ((GameStage)getParent()).getCurves()) {
			if(curve.isAlive()) {
				curve.addPoint();
			}
		}
	}
	public void addPoint() {
		score++;
		scoreLabel.setText(Integer.toString(score));
		
	}

	private boolean checkCollision() {
		GameStage gameStage = (GameStage) getParent();

		Color frontColor = gameStage.getPixelColor(Math.round(getX() + (radius +1)  * (float) Math.cos(radians)),
											  Math.round(getY() + (radius + 1) * (float) Math.sin(radians)));
		
		Color leftColor = gameStage.getPixelColor(Math.round(getX() + (radius + 1) * (float) Math.cos(radians + Math.PI/2)),
				  							  Math.round(getY() + (radius + 1)* (float) Math.sin(radians + Math.PI/2)));

		Color rightColor = gameStage.getPixelColor(Math.round(getX() + (radius + 1) * (float) Math.cos(radians - Math.PI/2)),
				  							  Math.round(getY() + (radius + 1) * (float) Math.sin(radians - Math.PI/2)));
		
		/*
		if (!frontColor.equals(Color.BLACK) || !leftColor.equals(Color.BLACK)|| !rightColor.equals(Color.BLACK)) {
			System.out.println("");
			System.out.print("collision: ");
			System.out.print("curve color: " + getColor().toString());
		}
		
		if (!frontColor.equals(Color.BLACK)) {
			System.out.print(" front color " +frontColor.toString());
		}
		if (!leftColor.equals(Color.BLACK)) {
			System.out.print(" left color " +leftColor.toString());
		}
		if (!rightColor.equals(Color.BLACK)) {
			System.out.print(" right color " +rightColor.toString());
		}
		*/
		
		if (leftColor.b > 0.1 || leftColor.r > 0.1 || leftColor.g > 0.1) {
			System.out.println("left collision " + getColor().toString() + " " + leftColor.toString());
			return true;
		}
		if (frontColor.b > 0.1 || frontColor.r > 0.1 || frontColor.g > 0.1) {
			System.out.println("front collision " + getColor().toString() + " " + frontColor.toString());
			return true;
		}
		if (rightColor.b > 0.1 || rightColor.r > 0.1 || rightColor.g > 0.1) {
			System.out.println("right collision " + getColor().toString() + " " + rightColor.toString());
			return true;
		}
		if (getX() < 0 || getX() > getStage().getWidth() - GameScreen.SCOREBAR_WIDTH) {
			System.out.println("x outside screen " + getColor().toString());
			return true;
		}
		if (getY() < 0 || getY() > getStage().getHeight()) {
			System.out.println("x outside screen " + getColor().toString());
			return true;
		}
		return false;
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
		case 3:
			setKeyLeft(Keys.LEFT);
			setKeyRight(Keys.RIGHT);
			setColor(Color.MAGENTA);
			break;
		case 4:
			setKeyLeft(Keys.BUTTON_A);
			setKeyRight(Keys.BUTTON_B);
			setColor(Color.ORANGE);
			break;
		case 5:
			setKeyLeft(Keys.NUM_9);
			setKeyRight(Keys.NUM_0);
			setColor(Color.PINK);
			break;
		case 6:
			setKeyLeft(Keys.NUM_1);
			setKeyRight(Keys.NUM_2);
			setColor(Color.WHITE);
			break;
		case 7:
			setKeyLeft(Keys.NUMPAD_2);
			setKeyRight(Keys.NUMPAD_3);
			setColor(Color.CYAN);
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
	
	public boolean isAlive(){
		return alive;
	}
	public void setAlive(boolean lifeStatus){
		alive = lifeStatus;
	}
	private void createTable() {
		Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		TextField playerName = new TextField(getName(), skin);
		playerName.setColor(getColor());
		table.add(playerName);
		if (keyLeft != -1) {
			TextField leftKeySetter = new TextField(Keys.toString(keyLeft), skin);
			leftKeySetter.setColor(getColor());
			table.add(leftKeySetter);
		} else {
			table.add(new TextField("Not set", skin));
		}
		if (keyRight != -1) {
			TextField rightKeySetter = new TextField(Keys.toString(keyRight), skin);
			rightKeySetter.setColor(getColor());
			table.add(rightKeySetter);
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

	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}
	public void removeTail(){
		tail.clear();
	}

	public String toTableString() {
		return getName() + " " +Integer.toString(score);
	}
	public Label getScoreLabel(){
		return scoreLabel;
	}

	public long getHoleStartTime() {
		return holeStartTime;
	}

	public void setHoleStartTime(long holeStartTime) {
		holeStartTime = holeStartTime;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public float getSpeed() {
		return speed;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	

}
