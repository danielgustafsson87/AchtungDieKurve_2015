package se.daniel.game.screens;

import java.util.ArrayList;

import se.daniel.game.models.Curve;
import se.daniel.game.models.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
	private TextButton startButton = new TextButton("Start", skin);
	private TextButton backButton = new TextButton("Back", skin);
	
	private Label nbrOfPlayersLabel;
	private Label scoreLabel;
	private Map map;
	private int scoreToWin = 5;
	public GameMenu(int nbrOfPlayers) {
		scoreLabel = new Label(Integer.toString(scoreToWin), skin);
		map = new Map(skin);
		for(int i = 0; i < nbrOfPlayers; i++) {
			curves.add(new Curve(i));
		}
	}
	@Override
	public void show() {
		
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(curves, map, scoreToWin));
			}
		});
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		table.add(getNbrOfPlayersTable(skin)).left().row();
		table.add(getMapTable(skin)).left().row();
		table.add(getScoreTable(skin)).left().row();
		
		Table playerLabels = new Table();
		playerLabels.add(new Label("Players:", skin));
		playerLabels.add(new Label("Turn Left:", skin));
		playerLabels.add(new Label("Turn Right:", skin));
		playerTable.add(playerLabels).row();
		for(Curve curve: curves) {
			// to look on curves properties
			playerTable.add(curve.getTable());
			playerTable.row();
			
		}
		table.add(playerTable).height(300);
		table.row();
		
		table.add(backButton).left();
		table.add(startButton).right();
		
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
	
	private Table getNbrOfPlayersTable(Skin skin) {
		nbrOfPlayersLabel = new Label(Integer.toString(curves.size()), skin); //TODO: is label best here?
		
		TextButton arrowLeft = new TextButton("<", skin);
		arrowLeft.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (curves.size() > 1) {
					playerTable.removeActor(curves.remove(curves.size() -1).getTable());
					
				}
				nbrOfPlayersLabel.setText((Integer.toString(curves.size())));
			}
		});
		TextButton arrowRight = new TextButton(">", skin);
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
		Table nbrOfPlayersTable = new Table();
		nbrOfPlayersTable.add(new Label("players:", skin)).left();
		nbrOfPlayersTable.add(arrowLeft).padLeft(20);
		nbrOfPlayersTable.add(nbrOfPlayersLabel);
		nbrOfPlayersTable.add(arrowRight);
		return nbrOfPlayersTable;
	}
	
	private Table getMapTable(Skin skin) {
		TextButton arrowLeft = new TextButton("<", skin);
		arrowLeft.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (map.getSize() > Map.MIN_SIZE) {
					map.decreaseSize();
				}
			}
		});
		TextButton arrowRight = new TextButton(">", skin);
		arrowRight.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (map.getSize() < Map.MAX_SIZE) {
					map.increaseSize();
				}
			}
		});
		Table mapTable = new Table();
		mapTable.add(new Label("Map size:", skin)).left();
		mapTable.add(arrowLeft).padLeft(20);
		mapTable.add(map.getLabel()).width(100).center();
		mapTable.add(arrowRight);
		return mapTable;
	}

	private Table getScoreTable(Skin skin2) {
		TextButton arrowLeft = new TextButton("<", skin);
		arrowLeft.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (scoreToWin > 20) {
					scoreToWin -= 10;
				}
				scoreLabel.setText((Integer.toString(scoreToWin)));
			}
		});
		TextButton arrowRight = new TextButton(">", skin);
		arrowRight.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (scoreToWin < 200) {
					scoreToWin += 10;
				}
				
				scoreLabel.setText((Integer.toString(scoreToWin)));
			}
		});
		Table scoreTable = new Table();
		scoreTable.add(new Label("Points to win:", skin)).left();
		scoreTable.add(arrowLeft).padLeft(20);
		scoreTable.add(scoreLabel);
		scoreTable.add(arrowRight);
		return scoreTable;
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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
