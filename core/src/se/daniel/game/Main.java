package se.daniel.game;


import se.daniel.game.screens.Splash;

import com.badlogic.gdx.Game;

public class Main extends Game {
    public static final String TITLE="Achtung die kruve"; 
    public static final int WIDTH=800,HEIGHT=640; // used later to set window size
    
    @Override
    public void create() {
            setScreen(new Splash());
    }
}
