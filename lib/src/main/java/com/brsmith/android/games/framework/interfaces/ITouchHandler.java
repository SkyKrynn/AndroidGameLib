package com.brsmith.android.games.framework.interfaces;

import java.util.List;

import com.brsmith.android.games.framework.interfaces.IInput.TouchEvent;

import android.view.View.OnTouchListener;

public interface ITouchHandler extends OnTouchListener
{
	public boolean isTouchDown(int pointer);
	public int getTouchX(int pointer);
	public int getTouchY(int pointer);
	public List<TouchEvent> getTouchEvents();
}
