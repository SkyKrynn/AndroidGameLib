package com.brsmith.android.games.framework.interfaces;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IFileIO
{
	public InputStream readAsset(String filename) throws IOException;
    public InputStream readResource(int resource, Context context) throws IOException;
	public InputStream readFile(String filename) throws IOException;
	public OutputStream writeFile(String filename) throws IOException;
}
