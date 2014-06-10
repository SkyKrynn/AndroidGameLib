package com.brsmith.android.games.gl20practice.app;

/**
 * Created by brsmith on 3/28/2014.
 */
public class NumberCycler {
    float start;
    float end;
    float incr;
    float incrTime;

    float timeDelta;
    float currentNumber;

    float lastTime = -1;

    public NumberCycler(float start, float end, float incr, float incrTime) {
        this.start = start;
        this.end = end;
        this.incr = incr;
        this.incrTime = incrTime;
        timeDelta = 0;
        currentNumber = start;
    }

    public float getNumber() {

        float currentTime = System.nanoTime();
        if(lastTime < 0) {
            lastTime = currentTime;
        }
        timeDelta += currentTime - lastTime;
        lastTime = currentTime;

        if(timeDelta >= incrTime) {
            currentNumber += incr;
            if(currentNumber > end)
                currentNumber = start;
            timeDelta = 0;
        }

        return currentNumber;
    }
}
