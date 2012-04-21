package com.aukware.games.android.hashi;

import java.util.List;

import com.aukware.android.games.framework.Game;
import com.aukware.android.games.framework.Graphics;
import com.aukware.android.games.framework.Input.TouchEvent;
import com.aukware.android.games.framework.Screen;

public class MainScreen extends Screen {
	float delaytime = (float) 0.0;
	
	Grid grid;
	int fromX = -1, fromY = -1;
	int toX = -1, toY = -1;
	boolean createMode = false;

	public MainScreen(Game game) {
		super(game);
		Graphics g = game.getGraphics();
		
		createGrid (5, 5);
		
	}

	public void createGrid (int x, int y) {
		// create the initial grid
		grid = new Grid(x, y, game.getGraphics());
		
		// create connections
		grid.randomize ();
	}
	
	@Override
	public void update(float deltaTime) {

		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				// process event TOUCH_UP
				if (event.y > (g.getHeight() / 3) * 2)
					createMode = !createMode;
				else {
					if (createMode) {
						createGrid (5, 5);
					}
					else {
						if (grid.uconnections < grid.connections){ 
							if (fromX == -1) {
								fromX = event.x;
								fromY = event.y;
							}
							else {
								toX = event.x;
								toY = event.y;
								grid.connect (fromY, fromX, toY, toX);
							
								fromX = -1;
							}
						}
						else {
							if (grid.isWinner())
								createGrid (5, 5);
							else
								grid.clearUserConnections ();
						}
					}
				}
			}
		}
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawPixmap(Assets.background, 0, 0);
		grid.drawGrid (g, createMode);
		g.drawLine(0, 2*g.getHeight()/3, g.getWidth(), 2*g.getHeight()/3, 0xFF808080);
		if (!createMode)
			if (grid.uconnections == grid.connections) {
				if (grid.isWinner())
					g.drawCenteredText ("Congratulations\nPuzzle Solved!", g.getWidth()/2, g.getHeight()/3, 40, 0xFF000000);
				else {
					g.drawCenteredText ("Sorry\nTry Again!", g.getWidth()/2, g.getHeight()/3, 50, 0xFF000000);
					g.drawCenteredText("(look for another solution)", g.getWidth()/2, g.getHeight()/3 + 22, 10, 0xFF000000);
				}
				g.drawText("Click to clear board.", 5, 2*g.getHeight()/3 - 10, 18, 0xFF000000);
			} else {
				if (fromX == -1)
					g.drawText("Click starting node.", 5, 2*g.getHeight()/3 - 10, 18, 0xFF000000);
				else
					g.drawText("Click destination node.", 5, 2*g.getHeight()/3 - 10, 18, 0xFF000000);
			}
		g.drawText("Click to hide/show connections.", 5, 2*g.getHeight()/3 + 20, 18, 0xFF000000);
		if (createMode)
			g.drawText("Click to create new board.", 5, 2*g.getHeight()/3 - 10, 18, 0xFF000000);
		g.drawCenteredText("Copyright \u00A9 2012 Dave Chamberlain", g.getWidth()/2, g.getHeight() - 7, 10, 0xFF000000);
	}

	@Override
	public void pause() {
		// Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
