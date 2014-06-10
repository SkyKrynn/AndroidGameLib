package com.brsmith.android.games.framework.interfaces;

import android.graphics.Bitmap;
import com.brsmith.android.games.framework.enums.PixmapFormat;

public interface IPixmap
{
	public int getWidth();
	public int getHeight();
	public PixmapFormat getFormat();
	public void dispose();
	public Bitmap getBitmap();
}
