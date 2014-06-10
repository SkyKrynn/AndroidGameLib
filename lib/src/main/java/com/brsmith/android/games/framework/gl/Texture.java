package com.brsmith.android.games.framework.gl;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.brsmith.android.games.framework.interfaces.IFileIO;
import com.brsmith.android.games.framework.interfaces.IGLGraphics;

public class Texture 
{
	IGLGraphics glGraphics;
	IFileIO fileIO;
    int resourceId;
	int textureId;
	//int minFilter;
	//int magFilter;
	public int width;
	public int height;
	
    public Texture(GLGame glGame, int resourceId, Context context)
	{
		this.glGraphics = glGame.getGLGraphics();
		this.fileIO = glGame.getFileIO();
		this.resourceId =  resourceId;
		load(context);
	}

	private void load(Context context)
	{
        int[] textureIds = glGraphics.GenTextures(1);
		textureId = textureIds[0];
		
		InputStream in = null;
		 try
		 {
             in = fileIO.readResource(resourceId, context);
			 Bitmap bitmap = BitmapFactory.decodeStream(in);
             glGraphics.BindTexture(textureId);
             glGraphics.AssociateTexture(bitmap);
             setFilters();
             glGraphics.BindTexture(0);
			 width = bitmap.getWidth();
			 height = bitmap.getHeight();
			 bitmap.recycle();
		 }
		 catch (IOException ex)
		 {
			 throw new RuntimeException("Couldn't load texture", ex);
		 }
		 finally
		 {
			 if(in != null)
				 try { in.close(); } catch (IOException ex) {}
		 }
	}
	
	public void setFilters()
	{
		//this.minFilter = minFilter;
		//this.magFilter = magFilter;
        glGraphics.SetTextureFilters();
	}
	
	public void reload(Context context)
	{
		load(context);
		bind();
		setFilters();
        glGraphics.BindTexture(0);
	}
	
	public void bind()
	{
        glGraphics.BindTexture(textureId);
	}
	
	public void dispose()
	{
        bind();
		int[] textureIds = { textureId };
        glGraphics.DeleteTextures(textureIds, 1);
	}
}
