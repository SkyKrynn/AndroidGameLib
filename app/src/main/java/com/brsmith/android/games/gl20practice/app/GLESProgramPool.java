package com.brsmith.android.games.gl20practice.app;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GLESProgramPool {
    private static Map<String, GLESProgram> pool = new HashMap<String, GLESProgram>();

    public static void resetPool() {
        for(GLESProgram program : pool.values())
        {
            Log.d("Program Pool", "Removed program " + String.valueOf(program.getProgramId()));
            program.remoteProgram();
        }
        pool.clear();
    }

    public static GLESProgram getProgram(Set<GLESProgram.Attributes> attributes) {
        return getProgram(attributes, 1);
    }

    public static GLESProgram getProgram(Set<GLESProgram.Attributes> attributes, int numTextures) {
        StringBuilder builder = new StringBuilder();

        if(attributes.contains(GLESProgram.Attributes.VERTICES))
            builder.append("V");

        if(attributes.contains(GLESProgram.Attributes.COLOR))
            builder.append("C");

        if(attributes.contains(GLESProgram.Attributes.TEXTURE)) {
            builder.append("X");
            builder.append(numTextures);
        }

        if(attributes.contains(GLESProgram.Attributes.TRANSLATION))
            builder.append("T");

        String key = builder.toString();

        if(!pool.containsKey(key)) {
            GLESProgram program = GLESProgramFactory.generateProgram(attributes, numTextures);
            pool.put(key, program);
        }

        GLESProgram program = pool.get(key);
        if(attributes.contains(GLESProgram.Attributes.TEXTURE)) {
            program.reset();
        }
        return program;
    }
}
