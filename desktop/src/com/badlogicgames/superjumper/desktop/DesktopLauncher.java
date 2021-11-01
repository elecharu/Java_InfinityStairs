package com.badlogicgames.superjumper.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogicgames.superjumper.SuperJumper;

public class DesktopLauncher {

	public static void main(String[] args){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "InfinityStairs";
		config.width = 540;
		config.height = 960;
		config.resizable = false;
		config.vSyncEnabled = true;
		new LwjglApplication(new SuperJumper(), config);
	}

	public DesktopLauncher() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "InfinityStairs";
		config.width = 540;
		config.height = 960;
		config.resizable = false;
		config.vSyncEnabled = true;
		new LwjglApplication(new SuperJumper(), config);
	}
}
