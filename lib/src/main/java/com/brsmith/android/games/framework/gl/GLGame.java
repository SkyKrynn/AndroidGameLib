package com.brsmith.android.games.framework.gl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.brsmith.android.games.framework.Screen.BaseScreen;
import com.brsmith.android.games.framework.input.AndroidInput;
import com.brsmith.android.games.framework.interfaces.IAudio;
import com.brsmith.android.games.framework.interfaces.IFileIO;
import com.brsmith.android.games.framework.interfaces.IGLGraphics;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.interfaces.IInput;
import com.brsmith.android.games.framework.resource.AndroidAudio;
import com.brsmith.android.games.framework.resource.AndroidFileIO;

public abstract class GLGame extends Activity implements IGame, Renderer
{
	enum GLGameState
	{
		Initialized,
		Running,
		Paused,
		Finished,
		Idle
	}
	
	GLSurfaceView glView;
	IGLGraphics glGraphics;
	IAudio audio;
	IInput input;
	IFileIO fileIO;
	BaseScreen screen;
	GLGameState state = GLGameState.Initialized;
	Object stateChanged = new Object();
	long startTime = System.nanoTime();
	WakeLock wakeLock;
	
	BaseScreen returnScreen;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		glView = new GLSurfaceView(this);
		glView.setRenderer(this);
		setContentView(glView);
		
		glGraphics = new GLGraphics(glView);
		fileIO = new AndroidFileIO(getAssets());
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, glView, 1, 1);
		PowerManager powerManager = (PowerManager)
				getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
	}
	
	public void onPause()
	{
		synchronized(stateChanged)
		{
			if(isFinishing())
				state = GLGameState.Finished;
			else
				state = GLGameState.Paused;
			
			while(true)
			{
				try
				{
					stateChanged.wait();
					break;
				}
				catch(InterruptedException e)
				{
					// do nothing
				}
			}
		}
		
		wakeLock.release();
		glView.onPause();
		super.onPause();
	}
	
	public void onResume()
	{
		super.onResume();
		glView.onResume();
		wakeLock.acquire();
	}

	public void onDrawFrame(GL10 gl) 
	{
		GLGameState state = null;
		
		synchronized(stateChanged)
		{
			state = this.state;
		}
		
		if(state == GLGameState.Running)
		{
			float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			
			screen.update(deltaTime);
			screen.present(deltaTime);
		}
		
		if(state == GLGameState.Paused)
		{
			screen.pause();
			synchronized(stateChanged)
			{
				this.state = GLGameState.Idle;
				stateChanged.notifyAll();
			}
		}
		
		if(state == GLGameState.Finished)
		{
			screen.pause();
			screen.dispose();
			synchronized(stateChanged)
			{
				this.state = GLGameState.Idle;
				stateChanged.notifyAll();
			}
		}
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
        //TODO: onSurfaceCreated() ???
		//glGraphics.setGL(gl);
		
		synchronized(stateChanged)
		{
			if(state == GLGameState.Initialized)
				screen = getStartScreen();
			state = GLGameState.Running;
			screen.resume();
			startTime = System.nanoTime();
		}
	}
	
	public IGLGraphics getGLGraphics()
	{
		return glGraphics;
	}

	public IInput getInput()
	{
		return input;
	}

	public IFileIO getFileIO()
	{
		return fileIO;
	}

	public IAudio getAudio()
	{
		return audio;
	}

	public void setScreen(BaseScreen screen)
	{
		setScreen(screen, false);
	}
	
	public void setScreen(BaseScreen screen, boolean disposeCurrent)
	{
		if(screen == null)
			throw new IllegalArgumentException("Screen must be null");
		
		this.screen.pause();
		
		if(disposeCurrent)
		{
			this.screen.dispose();
			this.screen = null;
		}
		
		this.returnScreen = this.screen;
		
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}

	public BaseScreen getCurrentScreen()
	{
		return screen;
	}

	public BaseScreen getReturnScreen()
	{
		return returnScreen;
	}

	public void exitGame()
	{
		this.finish();
	}

}
