package se.daniel.game.screens;

import java.util.ArrayList;

import se.daniel.game.Main;
import se.daniel.game.models.Curve;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.Random;
import java.math.*;
public class GameScreen implements Screen{
	private Stage stage = new Stage() {
		@Override
	    public boolean keyDown(int keyCode) {
			for (Curve curve : curves) {
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
		@Override
	    public boolean keyUp(int keyCode) {
			for (Curve curve : curves) {
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
	};
	private ArrayList<Curve> curves;
	public GameScreen(ArrayList<Curve> curves){
		this.curves = curves;
		/*
		 * Get all curves with there controls, color and other stuff
		*/
	}

	@Override
	public void show() {
		initializeCurves();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
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
