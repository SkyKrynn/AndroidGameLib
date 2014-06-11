package com.brsmith.android.games.gl20practice.app;

import android.util.Log;

import java.util.Set;

/**
 * Created by brsmith on 3/31/2014.
 */
public class GLESProgramFactory {

    public static GLESProgram generateProgram(Set<GLESProgram.Attributes> attributes) {
        return generateProgram(attributes, 1);
    }

    public static GLESProgram generateProgram(Set<GLESProgram.Attributes> attributes, int numTextures) {
        String vertexShader = createVertexShaderCode(attributes, numTextures);
        String fragmentShader = createFragmentShaderCode(attributes, numTextures);
        GLESProgram program = new GLESProgram(attributes, vertexShader, fragmentShader);
        Log.d("ProgramFactory", "Program: " + String.valueOf(program.getProgramId()));
        Log.d("ProgramFactory", "Vertex Shader: " + vertexShader);
        Log.d("ProgramFactory", "Fragment Shader: " + fragmentShader);
        return program;
    }

    private static String createVertexShaderCode(Set<GLESProgram.Attributes> attributes, int numTextures) {
        StringBuilder builder = new StringBuilder();

        if(hasVertices(attributes)) {
            builder.append("attribute vec4 aPosition;");
        }

        if(hasTexture(attributes)) {
            for(int idx = 1; idx <= numTextures; idx++) {
                builder.append("attribute vec2 aCoord");
                builder.append(idx);
                builder.append(";");
            }
        }

        if(hasColor(attributes)) {
            builder.append("attribute vec4 aColor;");
        }

        if(hasTexture(attributes)) {
            for(int idx = 1; idx <= numTextures; idx++) {
                builder.append("varying vec2 vCoord");
                builder.append(idx);
                builder.append(";");
            }
        }

        if(hasColor(attributes)) {
            builder.append("varying vec4 vColor;");
        }

        if(hasTranslations(attributes)) {
            builder.append("uniform mat4 uMVP;");
        }

        builder.append("void main() {");

        if(hasVertices(attributes)) {
            if(hasTranslations(attributes)) {
                builder.append("  vec4 vertex = vec4(aPosition[0],aPosition[1],aPosition[2],1.0);");
                builder.append("  gl_Position = uMVP * vertex;");
            } else {
                builder.append("  gl_Position = aPosition;");
            }
        }

        if(hasColor(attributes)) {
            builder.append("  vColor = aColor;");
        }

        if(hasTexture(attributes)) {
            for(int idx = 1; idx <= numTextures; idx++) {
                builder.append("  vCoord");
                builder.append(idx);
                builder.append(" = aCoord");
                builder.append(idx);
                builder.append(";");
            }
        }


        builder.append("}");

        return builder.toString();
    }

    private static String createFragmentShaderCode(Set<GLESProgram.Attributes> attributes, int numTextures) {
        StringBuilder builder = new StringBuilder();

        builder.append("#ifdef GL_FRAGMENT_PRECISION_HIGH \n");
        builder.append("precision highp float; \n");
        builder.append("#else \n");
        builder.append("precision mediump float; \n");
        builder.append("#endif \n");

        if(hasColor(attributes)) {
            builder.append("varying vec4 vColor;");
        }

        if(hasTexture(attributes)) {
            StringBuilder coords = new StringBuilder();
            StringBuilder samplers = new StringBuilder();
            for(int idx = 1; idx <= numTextures; idx++) {
                coords.append("varying vec2 vCoord");
                coords.append(idx);
                coords.append(";");

                samplers.append("uniform sampler2D uSampler");
                samplers.append(idx);
                samplers.append(";");
            }
            builder.append(coords);
            builder.append(samplers);
        }

        builder.append("void main() {");

        if(hasColor(attributes) || hasTexture(attributes)) {
            if(hasColor(attributes) && hasTexture(attributes)) {

                StringBuilder declares = new StringBuilder();
                declares.append("  vec4 ");
                StringBuilder loads = new StringBuilder();
                StringBuilder combines = new StringBuilder();
                combines.append("  gl_FragColor = vColor + (");

                for(int idx = 1; idx <= numTextures; idx++) {
                    if(idx != 1) {
                        declares.append(",");
                        combines.append(" * ");
                    }

                    declares.append("textureColor");
                    declares.append(idx);

                    loads.append("  textureColor");
                    loads.append(idx);
                    loads.append(" = texture2D(uSampler");
                    loads.append(idx);
                    loads.append(",vCoord");
                    loads.append(idx);
                    loads.append(");");

                    combines.append("textureColor");
                    combines.append(idx);
                }
                declares.append(";");
                combines.append(");");

                //builder.append("  vec4 textureColor;");
                //builder.append("  textureColor = texture2D(uSampler,vCoord);");
                //builder.append("  gl_FragColor = vColor + textureColor;");
                builder.append(declares);
                builder.append(loads);
                builder.append(combines);
            }

            if(hasColor(attributes) && !hasTexture(attributes)) {
                builder.append("  gl_FragColor = vColor;");
            }

            if(!hasColor(attributes) && hasTexture(attributes)) {
                StringBuilder declares = new StringBuilder();
                declares.append("  vec4 ");
                StringBuilder loads = new StringBuilder();
                StringBuilder combines = new StringBuilder();
                combines.append("  gl_FragColor = ");

                for(int idx = 1; idx <= numTextures; idx++) {
                    if(idx != 1) {
                        declares.append(",");
                        combines.append(" * ");
                    }

                    declares.append("textureColor");
                    declares.append(idx);

                    loads.append("  textureColor");
                    loads.append(idx);
                    loads.append(" = texture2D(uSampler");
                    loads.append(idx);
                    loads.append(",vCoord");
                    loads.append(idx);
                    loads.append(");");

                    combines.append("textureColor");
                    combines.append(idx);
                }
                declares.append(";");
                combines.append(";");

                builder.append(declares);
                builder.append(loads);
                builder.append(combines);

                //builder.append("  gl_FragColor = texture2D(uSampler,vCoord);");
            }

        } else {
            builder.append("  gl_FragColor = vec4(1.0,1.0,1.0,1);");
        }

        builder.append("}");

        return builder.toString();
    }

    private static boolean hasVertices(Set<GLESProgram.Attributes> attributes) { return attributes.contains(GLESProgram.Attributes.VERTICES);}
    private static boolean hasColor(Set<GLESProgram.Attributes> attributes) { return attributes.contains(GLESProgram.Attributes.COLOR);}
    private static boolean hasTexture(Set<GLESProgram.Attributes> attributes) { return attributes.contains(GLESProgram.Attributes.TEXTURE);}
    private static boolean hasTranslations(Set<GLESProgram.Attributes> attributes) { return attributes.contains(GLESProgram.Attributes.TRANSLATION);}
}
