package com.brsmith.android.games.gl20practice.app;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.EnumSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLES20Renderer implements GLSurfaceView.Renderer {
    private final String lineVertexShaderCode =
            "attribute vec4 aPosition;" +
            "void main() {" +
            " gl_Position = aPosition; " +
            "}";

    private final String lineFragmentShaderCode =
            "#ifdef GL_FRAGMENT_PRECISION_HIGH \n" +
            "precision highp float; \n" +
            "#else \n" +
            "precision mediump float; \n" +
            "#endif \n" +
            "void main() {" +
            " vec4 fragColor = vec4(1.0);" +
            " gl_FragColor = fragColor;" +
            "}";


    private final String lineVertexShaderColorCode =
            "attribute vec4 aPosition;" +
            "attribute vec4 aColor;" +
            "varying vec4 vColor;" +
            "void main() {" +
            " vColor = aColor; " +
            " gl_Position = aPosition; " +
            "}";

    private final String lineFragmentShaderColorCode =
            "#ifdef GL_FRAGMENT_PRECISION_HIGH \n" +
            "precision highp float; \n" +
            "#else \n" +
            "precision mediump float; \n" +
            "#endif \n" +
            "varying vec4 vColor; " +
            "void main() {" +
            " gl_FragColor = vColor;" +
            "}";

    int lineAVertexLocation;
    int lineAColorLocation;
    FloatBuffer lineVFB;
    FloatBuffer lineCFB;
    FloatBuffer triangleVFB;
    FloatBuffer rectangleVFB;

    VertexShader vertexShader;
    FragmentShader fragmentShader;
    GLESProgram lineProgram;

    private void initShapes() {
        float[] lineVFA = {
                0.0f,0.0f,0.0f,
                0.5f,0.5f,0.0f
        };
        ByteBuffer lineVBB = ByteBuffer.allocateDirect(lineVFA.length * 4);
        lineVBB.order(ByteOrder.nativeOrder());
        lineVFB = lineVBB.asFloatBuffer();
        lineVFB.put(lineVFA);
        lineVFB.position(0);

        float[] lineCFA = {
//                0.0f,0.0f,1.0f,1.0f,
                1.0f,1.0f,0.0f,1.0f
        };
        ByteBuffer lineCBB = ByteBuffer.allocateDirect(lineCFA.length * 4);
        lineCBB.order(ByteOrder.nativeOrder());
        lineCFB = lineCBB.asFloatBuffer();
        lineCFB.put(lineCFA);
        lineCFB.position(0);

        float[] triangleVFA = {
                -0.5f,0.0f,0.0f,
                0.0f,0.5f,0.0f,
                0.5f,0.0f,0.0f
        };
        ByteBuffer triangleVBB = ByteBuffer.allocateDirect(triangleVFA.length * 4);
        triangleVBB.order(ByteOrder.nativeOrder());
        triangleVFB = triangleVBB.asFloatBuffer();
        triangleVFB.put(triangleVFA);
        triangleVFB.position(0);

        float[] rectangleVFA = {
                0,0,0,
                0,0.5f,0,
                0.75f,0.5f,0,
                0.75f,0.5f,0,
                0.75f,0,0,
                0,0,0
        };
        ByteBuffer rectangleVBB = ByteBuffer.allocateDirect(rectangleVFA.length * 4);
        rectangleVBB.order(ByteOrder.nativeOrder());
        rectangleVFB = rectangleVBB.asFloatBuffer();
        rectangleVFB.put(rectangleVFA);
        rectangleVFB.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        initShapes();

        //vertexShader = new VertexShader(lineVertexShaderCode);
        //fragmentShader = new FragmentShader(lineFragmentShaderCode);
        //lineProgram = new GLESProgram();
        //lineProgram.addShader(vertexShader);
        //lineProgram.addShader(fragmentShader);
        //lineProgram.link();

        lineProgram = GLESProgramPool.getProgram(EnumSet.of(GLESProgram.Attributes.VERTICES, GLESProgram.Attributes.COLOR));

        lineAVertexLocation = GLES20.glGetAttribLocation(lineProgram.getProgramId(), "aPosition");
//        lineAColorLocation = GLES20.glGetAttribLocation(lineProgram.getProgramId(), "aColor");
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glUseProgram(lineProgram.getProgramId());

        GLES20.glVertexAttribPointer(lineAVertexLocation, 3, GLES20.GL_FLOAT, false, 0, lineVFB);
        GLES20.glEnableVertexAttribArray(lineAVertexLocation);

//        GLES20.glVertexAttribPointer(lineAVertexLocation, 3, GLES20.GL_FLOAT, false, 0, triangleVFB);
//        GLES20.glEnableVertexAttribArray(lineAVertexLocation);

//        GLES20.glVertexAttribPointer(lineAVertexLocation, 3, GLES20.GL_FLOAT, false, 0, rectangleVFB);
//        GLES20.glEnableVertexAttribArray(lineAVertexLocation);
//        GLES20.glVertexAttribPointer(lineAColorLocation, 4, GLES20.GL_FLOAT, false, 0, lineCFB);
//        GLES20.glEnableVertexAttribArray(lineAColorLocation);
        GLES20.glLineWidth(10);

        GLES20.glDrawArrays(GLES20.GL_LINES, 0, 2);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
    }
}
