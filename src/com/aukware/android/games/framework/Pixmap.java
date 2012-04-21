package com.aukware.android.games.framework;

import com.aukware.android.games.framework.Graphics.PixmapFormat;

public interface Pixmap {
	public int getWidth();

	public int getHeight();

	public PixmapFormat getFormat();

	public void dispose();
}