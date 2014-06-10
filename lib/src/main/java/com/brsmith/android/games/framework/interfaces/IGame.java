package com.brsmith.android.games.framework.interfaces;

import com.brsmith.android.games.framework.Screen.BaseScreen;

public interface IGame
{
	public IInput getInput();
	public IFileIO getFileIO();
	public IAudio getAudio();
	public void setScreen(BaseScreen screen);
	public BaseScreen getCurrentScreen();
	public BaseScreen getReturnScreen();
	public BaseScreen getStartScreen();
	public void exitGame();
}
