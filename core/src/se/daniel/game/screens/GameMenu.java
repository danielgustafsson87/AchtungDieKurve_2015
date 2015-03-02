package se.daniel.game.screens;

import java.util.ArrayList;

import se.daniel.game.models.Curve;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameMenu implements Screen{
	protected static final int MAX_PLAYERS = 8;
	private Stage stage = new Stage();
	private Table table = new Table();
	private Table playerTable = new Table();
	private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	
	private ArrayList<Curve> curves = new ArrayList<Curve>();
	
	private Label players = new Label("players:", skin);
	private TextButton arrowLeft = new TextButton("<", skin);
	private TextButton arrowRight = new TextButton(">", skin);
	private Label nbrOfPlayersLabel ;
	private TextButton startButton = new TextButton("Start", skin);
	private TextButton backButton = new TextButton("Back", skin);
	
	public GameMenu(int nbrOfPlayers) {
		for(int i = 0; i < nbrOfPlayers; i++) {
			curves.add(new Curve(i));
			
		}
		nbrOfPlayersLabel = new Label(Integer.toString(curves.size()), skin); //TODO: is label best here?
		
		
	}
	@Override
	public void show() {
		arrowLeft.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (curves.size() > 1) {
					playerTable.removeActor(curves.remove(curves.size() -1).getTable());
					
				}
				nbrOfPlayersLabel.setText((Integer.toString(curves.size())));
			}
		});
		arrowRight.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (curves.size() < MAX_PLAYERS) {
					Curve newCurve = new Curve(curves.size());
					curves.add(newCurve);
					playerTable.add(newCurve.getTable());
				}
				
				nbrOfPlayersLabel.setText((Integer.toString(curves.size())));
			}
		});
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(curves));
			}
		});
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		
		table.add(players).left();
		table.add(arrowLeft).padLeft(100);
		table.add(nbrOfPlayersLabel);
		table.add(arrowRight);
		table.row();
		
		for(Curve curve: curves) {
			// to look on curves properties
			playerTable.add(curve.getTable()).left();
			playerTable.row();
			
		}
		table.add(playerTable);
		table.row();
		table.add(startButton);
		table.row();
		table.add(backButton);
		
		table.setDebug(true);
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
		skin.dispose();
		stage.dispose();
	}

}
