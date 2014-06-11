package com.brsmith.android.games.gl20practice.app;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.FloatBuffer;
import java.util.Set;

public class GLESProgram {

    public static enum Attributes {
        VERTICES,
        COLOR,
        TEXTURE,
        TRANSLATION
    }

    Set<Attributes> attributes;

    int programId = -1;
    int nextTexturePosition = 0;

    public GLESProgram(Set<Attributes> attributes, String vertexSource, String fragmentSource) {
        this.attributes = attributes;
        createProgram();
        addShader(new VertexShader(vertexSource));
        addShader(new FragmentShader(fragmentSource));
        link();

        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if(linkStatus[0] != GLES20.GL_TRUE)
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Could not link program\n\n\n");
            builder.append("Vertex Shader\n");
            builder.append(vertexSource);
            builder.append("\n\nFragment Shader");
            builder.append(fragmentSource);
            builder.append("\n\n\n");
            Log.d("GLESProgram", builder.toString());
        }
    }

    private void createProgram() {
        programId = GLES20.glCreateProgram();
    }

    public void addShader(Shader shader) {
        GLES20.glAttachShader(programId, shader.getShaderId());
    }

    public void link() {
        GLES20.glLinkProgram(programId);
    }

    public void attachAttribute(String attributeName, GLESBuffer GLESBuffer) {
        attachAttribute(attributeName, GLESBuffer.getBuffer(), GLESBuffer.getUnitSize());
    }

    public void attachAttribute(String attributeName, FloatBuffer buffer, int size) {
        int attributeLocation = GLES20.glGetAttribLocation(programId, attributeName);
        GLES20.glVertexAttribPointer(attributeLocation, size, GLES20.GL_FLOAT, false, 0, buffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);
    }

    public void attachMatrix(String uniformName, float[] matrix) {
        int uniformLocation = GLES20.glGetUniformLocation(programId, uniformName);
        GLES20.glUniformMatrix4fv (uniformLocation, 1, false, matrix, 0);
    }

    public void attachTexture(String uniformName, Texture texture) {
        int uniformLocation = GLES20.glGetUniformLocation(programId, uniformName);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + nextTexturePosition);
        texture.bind();
        GLES20.glUniform1i(uniformLocation, nextTexturePosition);
        nextTexturePosition++;
    }

    public void reset() { nextTexturePosition = 0; }

    public int getProgramId() { return programId; }

    public void remoteProgram() {
        GLES20.glDeleteProgram(programId);
        programId = -1;
    }
}
