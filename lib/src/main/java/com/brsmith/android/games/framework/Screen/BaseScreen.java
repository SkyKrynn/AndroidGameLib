package com.brsmith.android.games.framework.Screen;

import android.content.Intent;
import com.brsmith.android.games.framework.interfaces.IGame;

public abstract class BaseScreen
{
	protected final IGame game;

	public BaseScreen(IGame game)
	{
		this.game = game;
	}
	
	public abstract void update(float deltaTime);
	public abstract void present(float deltaTime);
	public abstract void pause();
	public abstract void resume();
	public abstract void dispose();
	
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
	}
}
