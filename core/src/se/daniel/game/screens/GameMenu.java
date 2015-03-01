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
	private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	
	private int nbrOfPlayers = 2;
	private ArrayList<Curve> curves = new ArrayList<Curve>();
	
	private Label players = new Label("players:", skin);
	private TextButton arrowLeft = new TextButton("<", skin);
	private TextButton arrowRight = new TextButton(">", skin);
	private Label nbrOfPlayersLabel = new Label(Integer.toString(nbrOfPlayers), skin); //TODO: is label best here?
	
	@Override
	public void show() {
		arrowLeft.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (nbrOfPlayers > 1) {
					nbrOfPlayers--;
					curves.remove(nbrOfPlayers);
				}
				nbrOfPlayersLabel.setText((Integer.toString(nbrOfPlayers)));
				nbrOfPlayersLabel.setText((Integer.toString(curves.size())));
			}
		});
		arrowRight.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (nbrOfPlayers < MAX_PLAYERS) {
					nbrOfPlayers++;
					curves.add(new Curve(nbrOfPlayers));
				}
				
				nbrOfPlayersLabel.setText((Integer.toString(nbrOfPlayers)));
				nbrOfPlayersLabel.setText((Integer.toString(curves.size())));
			}
		});
		
		
		table.add(players).padLeft(200);
		table.add(arrowLeft);
		table.add(nbrOfPlayersLabel);
		table.add(arrowRight).row();
		for(int i = 0; i < nbrOfPlayers; i++) {
			curves.add(new Curve(i));
		}
		
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
