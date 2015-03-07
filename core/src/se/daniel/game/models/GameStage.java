package se.daniel.game.models;

import java.nio.ByteBuffer;

import se.daniel.game.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameStage extends Stage{
	
	Pixmap pixmap;
	@Override
    public boolean keyDown(int keyCode) {
		
		for (Actor actor : getActors()) {
			Curve curve = (Curve) actor;
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
		for (Actor actor : getActors()) {
			Curve curve = (Curve) actor;
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
		pixmap = ScreenUtils.getFrameBufferPixmap(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	public Color getPixelColor(int x, int y) {
		//final Pixmap pixmap = new Pixmap(Main.WIDTH, Main.HEIGHT, Format.RGB888);
		//ByteBuffer pixels = pixmap.getPixels();
		//Gdx.gl.glReadPixels(0, 0, Main.WIDTH, Main.HEIGHT, GL20.GL_RGB, GL20.GL_UNSIGNED_BYTE, pixels);
		Color color = new Color();
		Color.rgba8888ToColor(color, pixmap.getPixel(x,y));
		return color;
	}
	
	@Override
	public void dispose(){
		super.dispose();
		pixmap.dispose();
	}
}
