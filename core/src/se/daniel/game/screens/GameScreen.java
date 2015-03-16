package se.daniel.game.screens;

import java.util.ArrayList;

import se.daniel.game.Main;
import se.daniel.game.models.Curve;
import se.daniel.game.models.GameStage;
import se.daniel.game.models.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameScreen implements Screen, InputProcessor{
	public static final int SCOREBAR_WIDTH = 50;
	private GameStage gameStage;
	private Table scoreTable;
	private Stage mainStage;
	private Skin skin;
	public GameScreen(ArrayList<Curve> curves, Map map, int scoreToWin){
		skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	 
		//fit viewport is best I think
		this.mainStage = new Stage(new StretchViewport(map.getWidth(), map.getHeight()));
		this.gameStage = new GameStage(scoreToWin);
		
		Table scoreTable = new Table() {
			@Override
			public void act(float delta) {
				super.act(delta);
				//TODO: remove if not used...
			}
		};
		for (Curve curve : curves) {
			scoreTable.add(curve.getScoreLabel());
			scoreTable.row();
		}
		scoreTable.setDebug(true);

		Table mainTable = new Table();
		mainTable.add(gameStage).size(mainStage.getWidth() - SCOREBAR_WIDTH, mainStage.getHeight());
		mainTable.add(scoreTable).size(SCOREBAR_WIDTH, mainStage.getHeight());
		mainTable.setFillParent(true);
		mainTable.setDebug(true);
		mainStage.addActor(mainTable);
		
		initializeGame(curves);
		//mainStage.setViewport(new FitViewport((float) Main.WIDTH, (float) Main.HEIGHT));
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	int test = 0;
	@Override
	public void render(float delta) {
		gameStage.updatePixmap();
		mainStage.act();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		/*
        SpriteBatch batch = new SpriteBatch();
		Texture txt= new Texture(gameStage.getPixmap());

		if (test == 10) {
			batch.begin();
			batch.draw(txt, 0, 0);
			batch.end();  
			test = 0;
		}
		else {
			test++;
		}*/
		mainStage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		mainStage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void dispose() {
		skin.dispose();
		mainStage.dispose();
	}
	private void initializeGame(ArrayList<Curve> curves){
		for (Curve curve : curves) {
			gameStage.addActor(curve);
		}
		gameStage.startNewGame();
	}

	@Override
	public boolean keyDown(int keycode) {
		return gameStage.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return gameStage.keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
