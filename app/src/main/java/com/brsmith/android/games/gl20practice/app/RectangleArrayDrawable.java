package com.brsmith.android.games.gl20practice.app;

import android.opengl.GLES20;

/**
 * Created by brsmith on 3/28/2014.
 */
public class RectangleArrayDrawable extends ArrayDrawable {
    public RectangleArrayDrawable(float x1, float y1, float x2, float y2) {
        this(x1, y1, x2, y2, new GLESBuffer(new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 4));
    }

    public RectangleArrayDrawable(float x1, float y1, float x2, float y2, float r, float g, float b, float a) {
        this(x1, y1, x2, y2, new GLESBuffer(new float[] {r,g,b,a, r,g,b,a, r,g,b,a, r,g,b,a, r,g,b,a, r,g,b,a}, 4));
    }

    public RectangleArrayDrawable(float x1, float y1, float x2, float y2, GLESBuffer color) {
        super(GLES20.GL_TRIANGLES,
                new GLESBuffer(new float[] {x1,y1,0, x2,y1,0, x2,y2,0, x1,y1,0, x1,y2,0, x2,y2,0}, 3),
                color);
    }
}
