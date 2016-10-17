/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cement.stlview;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

public class STLGLRenderer implements GLSurfaceView.Renderer {
	private float mAngle;
	private float mTrans;
	private float mScale = 1.0f;
	private boolean mDdown;

	static float coords[] = {
	            0.0f,  0.622008459f, 0.0f,// top
	           -0.5f, -0.311004243f, 0.0f,// bottom left
	            0.5f, -0.311004243f, 0.0f // bottom right
	    };

	static float colors[] = { 
			0.63671875f, 0.76953125f, 0.22265625f, 0.4f, 
			0.63671875f, 0.76953125f, 0.22265625f, 0.5f, 
			0.63671875f, 0.76953125f, 0.22265625f, 0.6f, 
			0.63671875f, 0.76953125f, 0.22265625f, 0.7f, 
			};

	private FloatBuffer coordBuffer = convent(coords);
	private FloatBuffer colorBuffer = convent(colors);

	// number of coordinates per vertex in this array
	private static int COORDS = 3;
	private static int COLORS = 4;

	private static float min = Float.MAX_VALUE;
	private static float max = Float.MIN_VALUE;
	private static float dima = 1;
	
	public void bindData(float[] coords,float[] colors) {
		 min = Float.MAX_VALUE;
		 max = Float.MIN_VALUE;
		for (int i = 0; i < coords.length; i++) {
			if(coords[i]>max)  max = coords[i];
			if(coords[i]<min)  min = coords[i];
		}
		dima = max-min;
		System.out.println(">>>>>>>>>>>>>>max>>>>>>:  "+max);
		System.out.println(">>>>>>>>>>>>>>min>>>>>>:  "+min);
		System.out.println(">>>>>>>>>>>>>>dima>>>>>>:  "+dima);
		coordBuffer = convent(coords);
		colorBuffer = convent(colors);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d("........>>>>>>>>>>>>>>>>>>>>>>>>>>>>............", config.toString());
		// Set the background frame color
		gl.glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		gl.glViewport(0, 0, width, height);
		float ratio = (float) width / height;
		gl.glMatrixMode(GL10.GL_PROJECTION); // set matrix to projection mode
		gl.glLoadIdentity(); // reset the matrix to its default state
		//gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7); // apply the projection
		gl.glFrustumf(-ratio*dima, ratio*dima,-1*dima, 1*dima, 1*dima, 3*dima); // apply the projection
		//GLU.gluPerspective(gl,60, ratio, 1.0f, 100.0f);
		
		//gl.glOrthof(-dima*ratio, dima*ratio,-dima, dima, dima, dima*3);
		//gl.glNormalPointer(type, stride, pointer);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity(); // reset the matrix to its default state
		//GLU.gluLookAt(gl, 0, dima*2, 0, 0, 0, 0, 0.0f, 0.0f, 1.0f);
		GLU.gluLookAt(gl, 0, dima*2, 0, 0, 0, 0, 0.0f, 0.0f, 1.0f);
	}
	@Override
	public void onDrawFrame(GL10 gl) {
		// Draw background color
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	
		/*.....................................................................*/
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		//gl.glColor4f(0.4f, 0.6f, 0.7f, 0.5f);
		gl.glColorPointer(COLORS, GL10.GL_FLOAT, 0, colorBuffer);
		//gl.glColorPointer(4, GL10.GL_BYTE, 0, colorBuffer);
		gl.glVertexPointer(COORDS, GL10.GL_FLOAT, 0, coordBuffer);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, coordBuffer.capacity()/ COORDS);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

		/*.....................................................................*/

		gl.glRotatef(mAngle, 0.0f, 0.0f, 1.0f);
		gl.glTranslatef(0.0f, 0.0f, mTrans);
		if(mDdown){
			gl.glScalef(mScale, mScale, mScale);
		}
		
	}


	public static FloatBuffer convent(float[] floats){
		FloatBuffer floatBuffer = ByteBuffer.allocateDirect(floats.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		floatBuffer.put(floats).position(0);
		return floatBuffer;
	}
	public static ByteBuffer convent(byte[] bytes){
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder());
		byteBuffer.put(bytes).position(0);
		return byteBuffer;
	}
	public float getAngle() {
		return mAngle;
	}
	public void setAngle(float angle) {
		mAngle = angle;
	}
	public float getTrans() {
		return mTrans;
	}
	public void setTrans(float mTrans) {
		this.mTrans = mTrans;
	}
	public float getScale() {
		return mScale;
	}
	public void setScale(float mScale) {
		this.mScale = mScale;
	}
	public boolean isDdown() {
		return mDdown;
	}
	public void setDdown(boolean mDdown) {
		this.mDdown = mDdown;
	}
}