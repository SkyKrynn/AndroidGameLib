package com.brsmith.android.games.gl20practice.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;

public class Texture {
    int textureId;
    int resourceId;
    int width;
    int height;
    int minFilter;
    int magFilter;

    public Texture(Context context, int resourceId) {
        this.resourceId = resourceId;
        load(context);
    }

    private void load(Context context) {
        textureId = createTextureId();

        InputStream in = null;
        Bitmap bitmap;
        try {
            in = context.getResources().openRawResource(resourceId);
            bitmap = BitmapFactory.decodeStream(in);

            bind();
            GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 1);
            setFilters(GLES20.GL_NEAREST, GLES20.GL_LINEAR);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            unbind();

            width = bitmap.getWidth();
            height = bitmap.getHeight();
            bitmap.recycle();
        } finally {
            try {
                if(in != null)
                    in.close();
            } catch(IOException e) {
                // e.printStackTrace();
            }
        }

    }

    public void bind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
    }

    public void unbind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    public void reload(Context context) {
        load(context);
        bind();
        setFilters(minFilter, magFilter);
        unbind();
    }

    public void setFilters(int minFilter, int magFilter)
    {
        this.minFilter = minFilter;
        this.magFilter = magFilter;

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, minFilter);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, magFilter);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
    }

    public void dispose() {
        bind();
        int[] textureIds = { textureId };
        GLES20.glDeleteTextures(1, textureIds, 0);
    }

    private int createTextureId() {
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        return textureIds[0];
    }

    public int getTextureId() { return textureId; }
}
