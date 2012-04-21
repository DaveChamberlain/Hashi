package com.aukware.games.android.hashi;

import com.aukware.android.games.framework.Screen;
import com.aukware.android.games.framework.impl.AndroidGame;

import android.app.Activity;
import android.os.Bundle;

public class HashiActivity extends AndroidGame {
	public Screen getStartScreen() {

		//
		// Put up splash/loading screen if appropriate
		// game will being from the splash screen
		return new LoadingScreen(this);
	}
}
