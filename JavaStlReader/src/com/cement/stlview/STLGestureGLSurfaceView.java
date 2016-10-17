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

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;


public class STLGestureGLSurfaceView extends GLSurfaceView {

    private STLGLRenderer mRenderer;

    float[] colors = {0.8f,0.3f,0.6f,0.9f};

	private int width;
	private int height;
    
	private GestureDetector mSingleGesture;
	//private SimpleOnGestureListener mSignlGesture;
	private ScaleGestureDetector mDoubleGesture;
	//private SimpleOnScaleGestureListener mDobleGesture;
	
	public STLGestureGLSurfaceView(Context context,STLGLRenderer renderer) {
		this(context);
		this.mRenderer = renderer;
	}
	public STLGestureGLSurfaceView(Context context) {
        super(context);
        
        width = getContext().getResources().getDisplayMetrics().widthPixels;
        height = getContext().getResources().getDisplayMetrics().heightPixels;
        
        mSingleGesture = new GestureDetector(context, new SingleGestureListener());  
        mDoubleGesture = new ScaleGestureDetector(context, new DoubleGestureListener());
    }

	public STLGestureGLSurfaceView bindRenderer(STLGLRenderer renderer){
		mRenderer = renderer;
		this.setRenderer(mRenderer);
		return this;
	}
	@Override
    public boolean onTouchEvent(MotionEvent event) {
		mSingleGesture.onTouchEvent(event);
		mDoubleGesture.onTouchEvent(event);
		return true;
    }
    private class SingleGestureListener extends SimpleOnGestureListener{
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			return super.onSingleTapUp(e);
		}

		@Override
		public void onLongPress(MotionEvent e) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			super.onLongPress(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			mRenderer.setAngle(distanceX*360/width); 
    		mRenderer.setTrans(distanceY/height); 
    		requestRender();
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public void onShowPress(MotionEvent e) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			super.onShowPress(e);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			return super.onDown(e);
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			//onResume();
			//requestRender();
			return super.onDoubleTap(e);
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			return super.onDoubleTapEvent(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			return super.onSingleTapConfirmed(e);
		}

		@Override
		public boolean onContextClick(MotionEvent e) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			return super.onContextClick(e);
		}
    	
    }
    private class DoubleGestureListener extends SimpleOnScaleGestureListener{

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			float distance = detector.getCurrentSpan()-detector.getPreviousSpan();
			mRenderer.setScale(1+distance/height);
			requestRender();
			return true;
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			mRenderer.setDdown(true);;
			return super.onScaleBegin(detector);
		}

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			mRenderer.setDdown(false);;
			super.onScaleEnd(detector);
		}
    	
    }
    
    
}
