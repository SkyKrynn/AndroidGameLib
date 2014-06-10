package com.brsmith.android.games.gl20practice.app;

import android.opengl.GLES20;

/**
 * Created by brsmith on 3/27/2014.
 */
public class Shader {
    int shaderId;

    public Shader(int type, String source)
    {
        shaderId = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shaderId, source);
        GLES20.glCompileShader(shaderId);
    }

    public int getShaderId() { return shaderId; }
}

