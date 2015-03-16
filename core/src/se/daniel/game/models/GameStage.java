package se.daniel.game.models;

import java.util.ArrayList;
import java.util.Random;

import se.daniel.game.Main;
import se.daniel.game.screens.GameMenu;
import se.daniel.game.screens.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameStage extends Group{	
	private static final int INIT_TIME = 1500;
	Pixmap pixmap;
	private int ScoreToWin;
	private long gameStartTime;
	private final static int BORDER_OFFSET = 30;
	
	public GameStage(int scoreToWin) {
		super();
		this.ScoreToWin = scoreToWin;
	}
	
	@Override
	public void act(float delta){
		if (System.currentTimeMillis() < gameStartTime + INIT_TIME) {
			return;
		}
		super.act(delta);
		//check if only 1 player alive

		int maxScore = 0;
		int aliveCurves = 0;
		for(Curve curve : getCurves()) {
			if (curve.isAlive()) {
				aliveCurves++;
				if (aliveCurves > 1){
					return;
				}
			}
			if (curve.getScore() > maxScore) {
				maxScore = curve.getScore();
			}
		}
		
		if (maxScore > ScoreToWin) {
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
		//pixmap = ScreenUtils.getFrameBufferPixmap(0, 0, (int) getStage().getWidth(), (int) getStage().getHeight());
		pixmap = ScreenUtils.getFrameBufferPixmap(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	public Pixmap getPixmap() {
		return pixmap;
	}
	private void goToGameMenu() {
		pixmap.dispose();
		((Game) Gdx.app.getApplicationListener()).setScreen(new GameMenu(getCurves().size()));
		//TODO: Add cool win animation!
	}

	public Color getPixelColor(int x, int y) {
		float yScreen = y * getStage().getViewport().getScreenHeight() / getStage().getHeight();
		float xScreen = x * getStage().getViewport().getScreenWidth() / getStage().getWidth();

		Color color = new Color();
		Color.rgba8888ToColor(color, pixmap.getPixel( (int) (xScreen + 0.5f), (int) (yScreen + 0.5f)));
		return color;
	}
	public Color getPixelColor(float x, float y) {
		Color color = new Color();
		Color.rgba8888ToColor(color, pixmap.getPixel( (int) (x + 0.5f), (int) (y + 0.5f)));
		return color;
	}
	
	public void startNewGame() {
		gameStartTime = System.currentTimeMillis();
		Random rnd = new Random();
		for (Curve curve : getCurves()) {
			//random position not to close to sides
			//System.out.print((float) (BORDER_OFFSET + rnd.nextInt((int) getStage().getViewport().getWorldWidth()) - (2 * BORDER_OFFSET)));
			curve.setX((float) (BORDER_OFFSET + rnd.nextInt((int) getStage().getViewport().getWorldWidth() - (2 * BORDER_OFFSET) - GameScreen.SCOREBAR_WIDTH)));
			curve.setY((float) (BORDER_OFFSET + rnd.nextInt((int) getStage().getViewport().getWorldHeight()- (2 * BORDER_OFFSET))));
			
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
