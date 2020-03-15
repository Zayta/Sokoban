package exp.zhen.zayta.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import exp.zhen.zayta.main.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "NUR_Nighters";
		config.height = 200;
		config.width = 400;
		new LwjglApplication(new Game(), config);
	}
}
