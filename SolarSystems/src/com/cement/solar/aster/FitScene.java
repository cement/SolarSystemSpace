package com.cement.solar.aster;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;

public interface FitScene{
	
	public void fitDraw(GL10 gl);
	public void fitLlights(GL10 gl);
	public void fitMateral(GL10 gl);
	public int[] fitTexture(GL10 gl);
}
