package com.brsmith.android.games.gl20practice.app;

import android.opengl.GLES20;

public class LineArrayDrawable extends ArrayDrawable {
    public LineArrayDrawable(GLESBuffer vertices, GLESBuffer color) {
        super(GLES20.GL_LINES, vertices, color);
    }

    public LineArrayDrawable(GLESBuffer vertices, float r, float g, float b, float a) {
        super(GLES20.GL_LINES, vertices, r, g, b, a);
    }

    public LineArrayDrawable(GLESBuffer vertices) {
        super(GLES20.GL_LINES, vertices);
    }
}
