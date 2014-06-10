package com.brsmith.android.games.framework.gl;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import com.brsmith.android.games.framework.interfaces.IGLGraphics;

public class GLGraphics implements IGLGraphics {

    GLSurfaceView glView;

    GLGraphics(GLSurfaceView view) {
        glView = view;
    }

    @Override
    public int[] GenTextures(int numTextures) {

        int textures[] = new int[numTextures];
        GLES20.glGenTextures(numTextures, textures, 0);
        return textures;
    }

    @Override
    public void DeleteTextures(int[] textures, int numTextures) {
        GLES20.glDeleteTextures(numTextures, textures, 0);
    }

    @Override
    public void SetTextureFilters() {
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
    }

    @Override
    public void BindTexture(int textureId) {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
    }

    @Override
    public void AssociateTexture(Bitmap bitmap) {
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
    }


    @Override
    public void SetVertexPointer() {
        //gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //vertices.position(0);
        //gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
    }

    @Override
    public void SetColorPointer() {

    }

    @Override
    public void SetTexCoordPointer() {

    }

    @Override
    public void DisableStates() {

    }
}
