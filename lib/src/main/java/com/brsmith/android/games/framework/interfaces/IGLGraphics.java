package com.brsmith.android.games.framework.interfaces;

import android.graphics.Bitmap;

public interface IGLGraphics {
    int[] GenTextures(int numTextures);
    void DeleteTextures(int[] textures, int numTextures);
    void SetTextureFilters();
    void BindTexture(int textureId);
    void AssociateTexture(Bitmap bitmap);

    void SetVertexPointer();
    void SetColorPointer();
    void SetTexCoordPointer();
    void DisableStates();
}
