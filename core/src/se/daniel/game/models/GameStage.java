package se.daniel.game.models;

import java.util.ArrayList;
import java.util.Random;

import se.daniel.game.Main;
import se.daniel.game.screens.GameMenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameStage extends Group{	
	private static final int INIT_TIME = 2000;
	Pixmap pixmap;
	private int ScoreToWin = 10; //to be changeable
	private long gameStartTime;
	
	@Override
	public void act(float delta){
		if (System.currentTimeMillis() < gameStartTime + INIT_TIME) {
			return;
		}
		super.act(delta);
		//check if only 1 player alive

		int maxScore = 0;
		Curve aliveCurve = null;
		for(Curve curve : getCurves()) {
			if (curve.isAlive()) {
				if (aliveCurve == null) {
					aliveCurve = curve;
				} else {
					return;
				}
			}
			if (curve.getScore() > maxScore) {
				maxScore = curve.getScore();
			}
		}
		if (aliveCurve != null) {
			aliveCurve.addPoints();
		}
		if (maxScore > ScoreToWin ) {
			goToGameMenu();
		} else {
			startNewGame();
		}
	}

    public boolean keyDown(int keyCode) {
		if(keyCode == Keys.ESCAPE) {
			goToGameMenu();
		}
		for (Curve curve : getCurves()) {
			if (keyCode == curve.getKeyRight()) {
				curve.setTurningRight(true);
				return true;
			}
			if (keyCode == curve.getKeyLeft()) {
				curve.setTurningLeft(true);
				return true;
			}
		}
		return false;
	}

    public boolean keyUp(int keyCode) {		
		for (Curve curve : getCurves()) {
			if (keyCode == curve.getKeyRight()) {
				curve.setTurningRight(false);
				return true;
			}
			if (keyCode == curve.getKeyLeft()) {
				curve.setTurningLeft(false);
				return true;
			}
		}
		return false;
	}
	public void updatePixmap() {
		pixmap = ScreenUtils.getFrameBufferPixmap(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	private void goToGameMenu() {
		pixmap.dispose();
		((Game) Gdx.app.getApplicationListener()).setScreen(new GameMenu(getCurves().size()));
		//TODO: Add cool win animation!
	}

	public Color getPixelColor(int x, int y) {
		//final Pixmap pixmap = new Pixmap(Main.WIDTH, Main.HEIGHT, Format.RGB888);
		//ByteBuffer pixels = pixmap.getPixels();
		//Gdx.gl.glReadPixels(0, 0, Main.WIDTH, Main.HEIGHT, GL20.GL_RGB, GL20.GL_UNSIGNED_BYTE, pixels);
		Color color = new Color();
		Color.rgba8888ToColor(color, pixmap.getPixel(x,y));
		return color;
	}
	
	public void startNewGame() {
		gameStartTime = System.currentTimeMillis();
		Random rnd = new Random();
		for (Curve curve : getCurves()) {
			//random position
			curve.setX(rnd.nextInt(Main.WIDTH));
			curve.setY(rnd.nextInt(Main.HEIGHT));
			
			//random direction			
			curve.setRadians(Math.toRadians(rnd.nextInt(360)));
			
			//reset tail list
			curve.removeTail();
			curve.setAlive(true);
		}	
	}
	
	public ArrayList<Curve> getCurves() {
		ArrayList<Curve> curves = new ArrayList<Curve>();
		for (Actor actor : getChildren()) {
			curves.add((Curve) actor);
		}
		return curves;
	}
}
