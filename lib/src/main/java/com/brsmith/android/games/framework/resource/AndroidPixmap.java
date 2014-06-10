package com.brsmith.android.games.framework.resource;

import android.graphics.Bitmap;

import com.brsmith.android.games.framework.interfaces.IPixmap;
import com.brsmith.android.games.framework.enums.PixmapFormat;
public class AndroidPixmap implements IPixmap
{
	Bitmap bitmap;
	PixmapFormat format;
	
	public AndroidPixmap(Bitmap bitmap, PixmapFormat format)
	{
		this.bitmap = bitmap;
		this.format = format;
	}
	
	public AndroidPixmap(IPixmap pixmap, int width, int height)
	{
		this.bitmap = Bitmap.createScaledBitmap(((AndroidPixmap)pixmap).bitmap, width, height, false);
		this.format = pixmap.getFormat();
	}

	public int getWidth()
	{
		return bitmap.getWidth();
	}

	public int getHeight()
	{
		return bitmap.getHeight();
	}

	public PixmapFormat getFormat() 
	{
		return format;
	}

	public void dispose()
	{
		bitmap.recycle();
	}
	
	public Bitmap getBitmap()
	{
		return bitmap;
	}

}
