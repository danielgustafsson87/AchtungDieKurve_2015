package se.daniel.game.screens;

import java.util.ArrayList;

import se.daniel.game.models.Curve;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class WinScreen implements Screen{
	private int time;
	private ArrayList<Curve> curves;
	private float totalShowTime = 5;
	private float timeShowed = 0;
	private Table table = new Table();
	private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	
	private Stage stage = new Stage();
	
	public WinScreen(ArrayList<Curve> curves){
		this.curves = curves;
		for (Curve curve : curves) {
			Label nameLabel = new Label(curve.getName(), new Skin(Gdx.files.internal("skins/uiskin.json")));
			nameLabel.setColor(curve.getColor());
			nameLabel.setFontScale(curve.getScore() + 1);
			table.add(nameLabel);
			table.add(curve.getScoreLabel());
			table.row();
			table.setFillParent(true);
		}
	}

	@Override
	public void show() {
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		
		timeShowed += delta;
		if (timeShowed > totalShowTime){
			goToGameMenu();
		}	
	}

	private void goToGameMenu() {
		((Game) Gdx.app.getApplicationListener()).setScreen(new GameMenu(curves.size()));
		//TODO: Add cool win animation!
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
		// TODO Auto-generated method stub
		
	}

}
