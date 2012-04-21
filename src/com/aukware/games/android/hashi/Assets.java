package com.aukware.games.android.hashi;

import com.aukware.android.games.framework.Game;
import com.aukware.android.games.framework.Graphics;
import com.aukware.android.games.framework.Graphics.PixmapFormat;
import com.aukware.android.games.framework.Pixmap;
import com.aukware.android.games.framework.Sound;

public class Assets {
	public static Pixmap background;

	public static int level = 4;

	//
	// initialize the assets 
	//
	public static void init(Game game) {
		//
		// Passed the main game, load the assets into its space
		//
		Graphics g = game.getGraphics();

		background = g.newPixmap("background.png", PixmapFormat.RGB565);
	}
}
