package se.daniel.game.screens;

import java.util.ArrayList;
import se.daniel.game.models.Curve;
import se.daniel.game.models.GameStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;



public class GameScreen implements Screen, InputProcessor{
	private GameStage gameStage;
	private Stage mainStage;
	public GameScreen(ArrayList<Curve> curves){
		Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		this.mainStage = new Stage();
		this.gameStage = new GameStage();
		Table scoreTable = new Table() {
			@Override
			public void act(float delta) {
				super.act(delta);
				
			}
		};
		for (Curve curve : curves) {
			scoreTable.add(curve.getScoreLabel());
			scoreTable.row();
		}
		initializeGame(curves);
		

		Table mainTable = new Table();
		mainTable.add(gameStage);
		mainTable.add(scoreTable);
		mainStage.addActor(mainTable);
	}

	@Override
	public void show() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		gameStage.updatePixmap();
		mainStage.act();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		mainStage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
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
