package com.brsmith.android.games.gl20practice.app;

import android.opengl.GLES20;

public class GLESGraphics {
    public int screenHeight;
    public int screenWidth;

    public void Init() {
        Init(0, 0, 0, 1);
    }

    public void Init(float r, float g, float b) {
        Init(r, g, b, 1);
    }

    public void Init(float r, float g, float b, float a) {
        setClearColor(r, g, b, a);
    }

    public void SetViewPort(int width, int height) {
        SetViewPort(0, 0, width, height);
    }

    public void SetViewPort(int x, int y, int width, int height) {
        screenWidth = width;
        screenHeight = height;
        GLES20.glViewport(x, y, width, height);
    }

    public void ClearScreen(float r, float g, float b) {
        setClearColor(r, g, b, 1);
        clear();
    }

    public void ClearScreen(float r, float g, float b, float a) {
        setClearColor(r, g, b, a);
        clear();
    }

    public void ClearScreen() {
        clear();
    }

    private void setClearColor(float r, float g, float b, float a) {
        GLES20.glClearColor(r, g, b, a);
    }

    private void clear() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }
}
