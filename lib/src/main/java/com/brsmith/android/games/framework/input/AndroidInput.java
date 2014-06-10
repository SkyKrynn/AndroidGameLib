package com.brsmith.android.games.framework.input;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.brsmith.android.games.framework.interfaces.ITouchHandler;
import com.brsmith.android.games.framework.interfaces.IInput;

public class AndroidInput implements IInput
{
	AccelerometerHandler accelHandler;
	KeyboardHandler keyHandler;
	ITouchHandler touchHandler;
	
	public AndroidInput(Context context, View view, float scaleX, float scaleY)
	{
		accelHandler = new AccelerometerHandler(context);
		keyHandler = new KeyboardHandler(view);
		touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
	}

	public boolean isKeyPressed(int keyCode) 
	{
		return keyHandler.isKeyPressed(keyCode);
	}

	public boolean isTouchDown(int pointer)
	{
		return touchHandler.isTouchDown(pointer);
	}

	public int getTouchX(int pointer) 
	{
		return touchHandler.getTouchX(pointer);
	}

	public int getTouchY(int pointer)
	{
		return touchHandler.getTouchY(pointer);
	}

	public float getAccelX() 
	{
		return accelHandler.getAccelX();
	}

	public float getAccelY() 
	{
		return accelHandler.getAccelY();
	}

	public float getAccelZ()
	{
		return accelHandler.getAccelZ();
	}

	public List<KeyEvent> getKeyEvents()
	{
		return keyHandler.getKeyEvents();
	}

	public List<TouchEvent> getTouchEvents()
	{
		return touchHandler.getTouchEvents();
	}

}
