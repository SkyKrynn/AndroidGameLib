package com.brsmith.android.games.gl20practice.app;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.util.EnumSet;

/**
 * Created by brsmith on 3/28/2014.
 */
public class ArrayDrawable {
    private final String vertexShaderCode =
            "attribute vec4 aPosition;" +
            "attribute vec4 aColor;" +
            "varying vec4 vColor;" +
            "uniform mat4 uMVP;" +
            "void main() {" +
            " vColor = aColor; " +
            " vec4 vertex = vec4(aPosition[0],aPosition[1],aPosition[2],1.0); "+
            " gl_Position = uMVP * vertex; " +
            "}";

    private final String fragmentShaderCode =
            "#ifdef GL_FRAGMENT_PRECISION_HIGH \n" +
            "precision highp float; \n" +
            "#else \n" +
            "precision mediump float; \n" +
            "#endif \n" +
            "varying vec4 vColor; " +
            "void main() {" +
            " gl_FragColor = vColor;" +
            "}";

    int type;
    GLESProgram program;
    GLESBuffer bufferVertices;
    GLESBuffer bufferColor;
    int width;

    private float[] translateMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] projectionMatrix = new float[16];
    private float[] mvpMatrix = new float[16];

    public ArrayDrawable(int type, GLESBuffer vertices, GLESBuffer color) {
        this.type = type;
        bufferColor = color;
        setupLines(vertices, 1);
    }

    public ArrayDrawable(int type, GLESBuffer vertices, float r, float g, float b, float a) {
        this.type = type;
        setColor(r, g, b, a);
        setupLines(vertices, 1);
    }

    public ArrayDrawable(int type, GLESBuffer vertices) {
        this.type = type;
        setColor(1.0f, 1.0f, 1.0f, 1.0f);
        setupLines(vertices, 1);
    }

    protected void setColor(float r, float g, float b, float a) {
        bufferColor = new GLESBuffer(
                new float[] {
                        r, g, b, a,
                        r, g, b, a
                }, 4);
    }

    protected void setupLines(GLESBuffer vertices, int width) {
        bufferVertices = vertices;
        program = GLESProgramPool.getProgram(EnumSet.of(
                GLESProgram.Attributes.VERTICES,
                GLESProgram.Attributes.COLOR,
                GLESProgram.Attributes.TRANSLATION));
        setWidth(width);
    }

    public void rotate(float x, float y, float z) {
        if(x != 0)
            Matrix.rotateM(translateMatrix, 0, x, -1, 0, 0);
        if(y != 0)
            Matrix.rotateM(translateMatrix, 0, y, 0, -1, 0);
        if(z != 0)
            Matrix.rotateM(translateMatrix, 0, z, 0, 0, -1);

    }

    public void setWidth(int width) { this.width = width; }

    NumberCycler i = new NumberCycler(0.0f, 100f, 1f, 30000);

    protected void draw(GLESGraphics gl) {
        float ratio	= (float) gl.screenWidth / gl.screenHeight;
        float zNear = 0.1f;
        float zFar = 1000;
        float fov = 0.75f; // 0.2 to 1.0
        fov = 0.05f; // 0.2 to 1.0
        float size = (float) (zNear * Math.tan(fov / 2));
//        Matrix.setLookAtM(viewMatrix, 0, -13, 5, 10, 0, 0, 0, 0, 1, 0);
        Matrix.setLookAtM(viewMatrix, 0,
                0, 0, 30,  // eye       -13, 5, 10
                0, 0, 0,     // center    0, 0, 0
                0, 1, 0);    // up        0, 1, 0
        Matrix.frustumM(projectionMatrix, 0,
                -size,             // left
                size,              // right
                -size / ratio,     // bottom
                size / ratio,      // top
                zNear,             // near
                zFar);             // far

        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, translateMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        GLES20.glUseProgram(program.getProgramId());
        program.attachAttribute("aPosition", bufferVertices);
        program.attachAttribute("aColor", bufferColor);
        program.attachMatrix("uMVP", mvpMatrix);
        GLES20.glLineWidth(width);
        GLES20.glDrawArrays(type, 0, bufferVertices.getNumUnits());
    }
}
