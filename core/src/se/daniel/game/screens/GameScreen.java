package se.daniel.game.screens;

import java.util.ArrayList;

import se.daniel.game.Main;
import se.daniel.game.models.Curve;
import se.daniel.game.models.GameStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import java.util.Random;

public class GameScreen implements Screen{
	private GameStage stage;
		
	private ArrayList<Curve> curves;
	public GameScreen(ArrayList<Curve> curves){
		this.curves = curves;
		this.stage = new GameStage();
		initializeCurves();

	}

	@Override
	public void show() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		stage.updatePixmap();
		stage.act();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}
	private void initializeCurves(){
		Random rnd = new Random();
		for (Curve curve : curves) {
			//random position
			curve.setX(rnd.nextInt(Main.WIDTH));
			curve.setY(rnd.nextInt(Main.HEIGHT));
			
			//random direction			
			curve.setRadians(Math.toRadians(rnd.nextInt(360)));
			
			stage.addActor(curve);
		}
	}

}
