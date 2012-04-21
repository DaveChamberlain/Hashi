package com.aukware.games.android.hashi;

import com.aukware.android.games.framework.Game;
import com.aukware.android.games.framework.Graphics;
import com.aukware.android.games.framework.Screen;

public class LoadingScreen extends Screen {
	public LoadingScreen(Game game) {
		super(game);

		//
		// load the assets and any settings
		Assets.init(game);
		// Settings.load(game.getFileIO());

	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();

		game.setScreen(new MainScreen(game));
	}

	@Override
	public void present(float deltaTime) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
