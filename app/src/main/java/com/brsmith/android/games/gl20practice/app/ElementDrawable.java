package com.brsmith.android.games.gl20practice.app;

import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class ElementDrawable {
    int type;
    GLESBuffer bufferVertices;
    GLESIndexBuffer bufferIndex;
    GLESBuffer bufferColor;
    List<Texture> textures;
    List<GLESBuffer> bufferTexCoords;
    int lineWidth;
    Set<GLESProgram.Attributes> attribs;

    public ElementDrawable(int elementType, GLESBuffer vertices, GLESIndexBuffer index) {
        this.type = elementType;
        bufferVertices = vertices;
        textures = new ArrayList<Texture>();
        bufferTexCoords = new ArrayList<GLESBuffer>();
        attribs = EnumSet.of(GLESProgram.Attributes.VERTICES);
        bufferIndex = index;
        setWidth(10);
    }

    public void setColor(GLESBuffer colors) {
        bufferColor = colors;
        if(!attribs.contains(GLESProgram.Attributes.COLOR))
            attribs.add(GLESProgram.Attributes.COLOR);
    }

    public void setTexture(Texture texture, GLESBuffer texCoords) {
        addTexture(texture, texCoords);
    }
    public void addTexture(Texture texture, GLESBuffer texCoords) {
        bufferTexCoords.add(texCoords);
        textures.add(texture);
        if(!attribs.contains(GLESProgram.Attributes.TEXTURE))
            attribs.add(GLESProgram.Attributes.TEXTURE);
    }

    public void setTexture(TextureRegion region)
    {
        addTexture(region);
    }
    public void addTexture(TextureRegion region)
    {
        float[] buffer = new float[]
                {
                        region.u1,region.v1,
                        region.u2,region.v1,
                        region.u2,region.v2,
                        region.u1,region.v2
                };
        GLESBuffer glesBuffer = new GLESBuffer(buffer, 2);
        setTexture(region.texture, glesBuffer);
    }

    public void setWidth(int width) { lineWidth = width; }

    public void draw() {
        GLESProgram program = GLESProgramPool.getProgram(attribs);
        GLES20.glUseProgram(program.getProgramId());

        if(attribs.contains(GLESProgram.Attributes.TEXTURE)) {
            int idx = 0;
            for(Texture texture : textures) {
                program.attachTexture("uSampler" + idx, texture);
                idx++;
            }
        }

        program.attachAttribute("aPosition", bufferVertices);

        if(attribs.contains(GLESProgram.Attributes.COLOR) && !attribs.contains(GLESProgram.Attributes.TEXTURE))
            program.attachAttribute("aColor", bufferColor);

        if(attribs.contains(GLESProgram.Attributes.TEXTURE)) {
            int idx = 0;
            for(GLESBuffer buffer : bufferTexCoords) {
                program.attachAttribute("aCoord" + idx, buffer);
                idx++;
            }
        }

        GLES20.glLineWidth(lineWidth);
        GLES20.glDrawElements(type, bufferIndex.dataLength, GLES20.GL_UNSIGNED_SHORT, bufferIndex.getBuffer());
    }
}
