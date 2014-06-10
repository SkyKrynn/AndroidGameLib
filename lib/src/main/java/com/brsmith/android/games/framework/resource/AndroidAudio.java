package com.brsmith.android.games.framework.resource;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.brsmith.android.games.framework.interfaces.IAudio;
import com.brsmith.android.games.framework.interfaces.IMusic;
import com.brsmith.android.games.framework.interfaces.ISound;

public class AndroidAudio implements IAudio
{
	AssetManager assets;
	SoundPool soundPool;
	
	public AndroidAudio(Activity activity)
	{
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}

	public IMusic newMusic(String filename)
	{
		try
		{
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			return new AndroidMusic(assetDescriptor);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Could not load music '" + filename + "'");
		}
	}

	public ISound newSound(String filename)
	{
		try
		{
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new AndroidSound(soundPool, soundId);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Could not load sound '" + filename + "'");
		}
	}

}
