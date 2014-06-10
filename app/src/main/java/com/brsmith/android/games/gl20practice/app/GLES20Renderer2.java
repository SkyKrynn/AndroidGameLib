package com.brsmith.android.games.gl20practice.app;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLES20Renderer2 implements GLSurfaceView.Renderer {

    GLESGraphics glGraphics = new GLESGraphics();
    RectangleArrayDrawable rectangle;
    LineArrayDrawable singleLine1;
    LineArrayDrawable singleLine2;
    LineArrayDrawable singleLine3;
    LineArrayDrawable singleLine4;
    TriangleArrayDrawable singleTriangle1;
    TriangleArrayDrawable singleTriangle2;
    TriangleArrayDrawable singleTriangle3;
    TriangleArrayDrawable singleTriangle4;

    ElementDrawable rect;
    ElementDrawable triangles;
    ElementDrawable filledRect;

    Context context;
    public GLES20Renderer2(Context context) {
        this.context = context;
    }

    private void initShapes() {
        GLESBuffer buffer;
        GLESIndexBuffer index;

        buffer = new GLESBuffer(
                new float[] {
                        0.2f,0.2f,0.0f,
                        -0.2f,0.2f,0.0f,
                        -0.2f,-0.2f,0.0f,
                        0.2f,-0.2f,0.0f
                }, 3);
        index = new GLESIndexBuffer(
                new short[] {
                        0,1, 1,2, 2,3, 3,0
                });
        rect = new ElementDrawable(GLES20.GL_LINES, buffer, index);
        rect.setColor(new GLESBuffer(new float[] {1,0,0,1, 1,0,0,1, 1,0,0,1, 1,0,0,1, 1,0,0,1, 1,0,0,1, 1,0,0,1, 1,0,0,1}, 4));

        buffer = new GLESBuffer(
                new float[] {
                        0.5f, -0.5f, 0.0f,
                        0.8f, -0.5f, 0.0f,
                        0.8f, -0.8f, 0.0f,
                        0.5f, -0.8f, 0.0f
                }, 3);

        index = new GLESIndexBuffer(
                new short[] {
                        0, 1, 2,  2, 3, 0
                });
        filledRect = new ElementDrawable(GLES20.GL_TRIANGLES, buffer, index);
        //filledRect.setColor(new GLESBuffer(new float[] {0,1,1,1, 0,1,1,1, 0,1,1,1, 0,0,1,1, 0,0,1,1, 0,0,1,1}, 4));
        buffer = new GLESBuffer(
                new float[] {
                        //1,0, 0,0, 1,1, 0,1
                        //1,1, 0,1, 1,0, 0,0
                        0,0, 1,0, 1,1, 0,1  // works
                        //0.25f,0.25f, 0.75f,0.25f, 0.75f,0.75f, 0.25f,0.75f
                        //0,1, 1,1, 1,0, 0,0
                }, 2);
        Texture texture = new Texture(context, R.drawable.brick);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.width, texture.height);
        //filledRect.setTexture(texture, buffer);
        filledRect.setTexture(region);

        buffer = new GLESBuffer(
                new float[] {
                        0.0f, 0.0f, 0.0f,
                        -0.4f, 0.0f, 0.0f,
                        -0.2f, 0.2f, 0.0f,
                        0.2f, 0.2f, 0.0f,
                        0.4f, 0.0f, 0.0f
                }, 3
        );

        index = new GLESIndexBuffer(
                new short[] {
                        0, 1, 2, 0, 3, 4
                });

        triangles = new ElementDrawable(GLES20.GL_TRIANGLES, buffer, index);
        triangles.setColor(new GLESBuffer(new float[] {1,1,0,1, 1,1,0,1, 0,1,1,1, 0,1,1,1, 0,1,0,1, 0,1,0,1}, 4));

        rectangle = new RectangleArrayDrawable(-0.75f, 0.75f, 0.75f, -0.75f, 0.5f, 0.5f, 0, 1);

        buffer = new GLESBuffer(
                new float[] {
                        0.0f,0.5f,0.0f,
                        0.5f,0.0f,0.0f
                }, 3);
        singleLine1 = new LineArrayDrawable(buffer, 1, 1, 1, 1);

        buffer = new GLESBuffer(
                new float[] {
                        0.5f,0.0f,0.0f,
                        0.0f,-0.5f,0.0f
                }, 3);
        singleLine2 = new LineArrayDrawable(buffer, 1, 0, 0, 1);

        buffer = new GLESBuffer(
                new float[] {
                        0.0f,-0.5f,0.0f,
                        -0.5f,0.0f,0.0f
                }, 3);
        singleLine3 = new LineArrayDrawable(buffer, 0, 1, 0, 1);

        buffer = new GLESBuffer(
                new float[] {
                        -0.5f,0.0f,0.0f,
                        0.0f,0.5f,0.0f
                }, 3);
        singleLine4 = new LineArrayDrawable(buffer, 0, 0, 1, 1);

        buffer = new GLESBuffer(
                new float[] {
                        0.0f,0.25f,0.0f,
                        0.25f,0.0f,0.0f,
                        0.0f,0.0f,0.0f
                }, 3);
        GLESBuffer color = new GLESBuffer(
                new float[] {
                        1.0f, 0.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f
                }, 4);
        singleTriangle1 = new TriangleArrayDrawable(buffer, color);

        buffer = new GLESBuffer(
                new float[] {
                        0.25f,0.0f,0.0f,
                        0.0f,-0.25f,0.0f,
                        0.0f,0.0f,0.0f
                }, 3);
        singleTriangle2 = new TriangleArrayDrawable(buffer, 1, 0, 0, 1);

        buffer = new GLESBuffer(
                new float[] {
                        0.0f,-0.25f,0.0f,
                        -0.25f,0.0f,0.0f,
                        0.0f,0.0f,0.0f
                }, 3);
        singleTriangle3 = new TriangleArrayDrawable(buffer, 0, 1, 0, 1);

        buffer = new GLESBuffer(
                new float[] {
                        -0.25f,0.0f,0.0f,
                        0.0f,0.25f,0.0f,
                        0.0f,0.0f,0.0f
                }, 3);
        singleTriangle4 = new TriangleArrayDrawable(buffer, 0, 0, 1, 1);

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glGraphics.Init(0, 0, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glGraphics.SetViewPort(width, height);
        GLESProgramPool.resetPool();

        initShapes();
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glGraphics.ClearScreen();

        //rectangle.draw(glGraphics);

        //singleLine1.draw(glGraphics);
        //singleLine2.draw(glGraphics);
        //singleLine3.draw(glGraphics);
        //singleLine4.draw(glGraphics);

        //singleTriangle1.draw(glGraphics);
        //singleTriangle2.draw(glGraphics);
        //singleTriangle3.draw(glGraphics);
        //singleTriangle4.rotate(0, 0, 45);
        //singleTriangle4.draw(glGraphics);

        rect.draw();
        filledRect.draw();
        triangles.draw();
        GLES20.glColorMask(true, true, true, true);
    }
}
