package com.brsmith.android.games.gl20practice.app;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public class GLESIndexBuffer {
    ShortBuffer dataBuffer;
    int unitSize;
    int dataLength;
    int numUnits;

    public GLESIndexBuffer(short[] data) {
        setDataPoints(data);
    }

    private void setDataPoints(short[] data) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(data.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        dataBuffer = buffer.asShortBuffer();
        dataBuffer.put(data);
        dataBuffer.position(0);

        dataLength = data.length;
        this.unitSize = 1;
        numUnits = dataLength / unitSize;
    }

    public ShortBuffer getBuffer() { return dataBuffer; }
    public int getUnitSize() { return unitSize; }
    public int getNumUnits() { return numUnits; }
}
