package com.brsmith.android.games.gl20practice.app;

import android.opengl.GLES20;

/**
 * Created by brsmith on 3/28/2014.
 */
public class TriangleArrayDrawable extends ArrayDrawable {
    public TriangleArrayDrawable(GLESBuffer vertices, GLESBuffer color) {
        super(GLES20.GL_TRIANGLES, vertices, color);
    }

    public TriangleArrayDrawable(GLESBuffer vertices, float r, float g, float b, float a) {
        super(GLES20.GL_TRIANGLES, vertices, new GLESBuffer(new float[] {r,g,b,a, r,g,b,a, r,g,b,a},4));
    }

    public TriangleArrayDrawable(GLESBuffer vertices) {
        this(vertices, 1, 1, 1, 1);
    }
}
