package com.brsmith.android.games.framework.input;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.brsmith.android.games.framework.Pool;
import com.brsmith.android.games.framework.Pool.PoolObjectFactory;
import com.brsmith.android.games.framework.interfaces.IInput.TouchEvent;
import com.brsmith.android.games.framework.interfaces.ITouchHandler;

public class SingleTouchHandler implements ITouchHandler
{
	boolean isTouched;
	int touchX;
	int touchY;
	Pool<TouchEvent> touchEventPool;
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	float scaleX;
	float scaleY;
	
	public SingleTouchHandler(View view, float scaleX, float scaleY)
	{
		PoolObjectFactory<TouchEvent> factory  = new PoolObjectFactory<TouchEvent>()
				{
					public TouchEvent createObject()
					{
						return new TouchEvent();
					}
				};

		touchEventPool = new Pool<TouchEvent>(factory, 100);
		view.setOnTouchListener(this);
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	public boolean onTouch(View view, MotionEvent event) 
	{
		synchronized(this)
		{
			TouchEvent touchEvent = touchEventPool.newObject();
			switch(event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
				touchEvent.type = TouchEvent.TOUCH_DOWN;
				isTouched = true;
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				touchEvent.type = TouchEvent.TOUCH_UP;
				isTouched = false;
				break;
			}
			
			touchEvent.x = touchX = (int)(event.getX() * scaleX);
			touchEvent.y = touchY = (int)(event.getY() * scaleY);
			touchEventsBuffer.add(touchEvent);
			
			return true;
		}
	}

	public boolean isTouchDown(int pointer)
	{
		synchronized(this)
		{
			if(pointer == 0)
				return isTouched;
			
			return false;
		}
	}

	public int getTouchX(int pointer) 
	{
		synchronized(this)
		{
			return touchX;
		}
	}

	public int getTouchY(int pointer) {
		synchronized(this)
		{
			return touchY;
		}
	}

	public List<TouchEvent> getTouchEvents() 
	{
		synchronized(this)
		{
			int len = touchEvents.size();
			for(int idx = 0; idx < len; idx++)
				touchEventPool.free(touchEvents.get(idx));
			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();
			return touchEvents;
		}
	}

}
