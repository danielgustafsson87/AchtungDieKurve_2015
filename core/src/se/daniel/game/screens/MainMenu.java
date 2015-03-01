package se.daniel.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenu implements Screen{
	private Stage stage = new Stage();
	private Table table = new Table();
	
	private Skin testSkin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	private TextButton playButton = new TextButton("play", testSkin);
	private TextButton optionsButton = new TextButton("options", testSkin);
	private TextButton exitButton = new TextButton("exit", testSkin);
	@Override
	public void show() {
		table.add(playButton).row();
		table.add(optionsButton).row();
		table.add(exitButton).row();
		table.setFillParent(true);
		stage.addActor(table);
		
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		
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
		dispose();
		
	}

	@Override
	public void dispose() {
		testSkin.dispose();
		stage.dispose();
		
	}

}
