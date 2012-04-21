package com.aukware.android.games.framework.impl;


import com.aukware.android.games.framework.Graphics.PixmapFormat;
import com.aukware.android.games.framework.Pixmap;

import android.graphics.Bitmap;

public class AndroidPixmap implements Pixmap {
	Bitmap bitmap;
	PixmapFormat format;

	public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
		this.bitmap = bitmap;
		this.format = format;
	}

	// @Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	// @Override
	public int getHeight() {
		return bitmap.getHeight();
	}

	// @Override
	public PixmapFormat getFormat() {
		return format;
	}

	// @Override
	public void dispose() {
		bitmap.recycle();
	}
}
