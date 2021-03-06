package com.brsmith.android.games.framework.resource;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.brsmith.android.games.framework.interfaces.IMusic;

public class AndroidMusic implements IMusic, OnCompletionListener
{
	MediaPlayer mediaPlayer;
	boolean isPrepared = false;
	
	public AndroidMusic(AssetFileDescriptor assetDescriptor)
	{
		mediaPlayer = new MediaPlayer();
		
		try
		{
			mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
					assetDescriptor.getStartOffset(),
					assetDescriptor.getLength());
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Could not load music");
		}
	}

	public void onCompletion(MediaPlayer player)
	{
		synchronized(this)
		{
			isPrepared = false;
		}
	}

	public void play()
	{
		if(mediaPlayer.isPlaying())
			return;
		
		try
		{
			synchronized(this)
			{
				if(!isPrepared)
				{
					mediaPlayer.prepare();
					isPrepared = true;
				}
				mediaPlayer.start();
			}
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void stop()
	{
		mediaPlayer.stop();
		synchronized(this)
		{
			isPrepared = false;
		}
	}

	public void pause()
	{
		mediaPlayer.pause();
	}

	public void setLooping(boolean looping)
	{
		mediaPlayer.setLooping(looping);
	}

	public void setVolume(float volume)
	{
		mediaPlayer.setVolume(volume, volume);
	}

	public boolean isPlaying()
	{
		return mediaPlayer.isPlaying();
	}

	public boolean isStopped()
	{
		return !isPrepared;
	}

	public boolean isLooping()
	{
		return mediaPlayer.isLooping();
	}

	public void dispose()
	{
		if(mediaPlayer.isPlaying())
			mediaPlayer.stop();
		mediaPlayer.release();
	}

}
