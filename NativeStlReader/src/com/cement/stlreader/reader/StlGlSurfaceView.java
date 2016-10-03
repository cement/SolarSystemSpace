package com.cement.stlreader.reader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.cement.stlreader.main.R;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class StlGlSurfaceView extends GLSurfaceView implements Renderer{

	private NativeRenderer nRenderer;
	
	private GestureDetector mSingleGesture;
	private ScaleGestureDetector mDoubleGesture;


	private int mWidth;
	private int mHeight;

	public StlGlSurfaceView(Context context) {
		this(context,null);
	}

	public StlGlSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mWidth = getContext().getResources().getDisplayMetrics().widthPixels;
	    mHeight = getContext().getResources().getDisplayMetrics().heightPixels;
	    mSingleGesture = new GestureDetector(context, new SingleGestureListener());  
	    mDoubleGesture = new ScaleGestureDetector(context, new DoubleGestureListener());
	}

	public void bindNativeRenderer(NativeRenderer renderer){
		// TODO Auto-generated method stub
		nRenderer = renderer;
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		if (nRenderer == null) {
			bindNativeRenderer(new NativeRenderer());
		}
		nRenderer.surfaceCreated();
		Log.i(getClass().getSimpleName(), "call......onSurfaceCreated()......");
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		if (nRenderer != null) {
			nRenderer.surfaceChanged(width, height);
		}
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		if (nRenderer != null) {
			nRenderer.surfaceDrawing();
		}
	}

	public void drawStlModel(String path){
		if (nRenderer != null) {
			nRenderer.drawStlModel(path);
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		mSingleGesture.onTouchEvent(event);
		mDoubleGesture.onTouchEvent(event);
		return true;
	}
	
	public class SingleGestureListener extends SimpleOnGestureListener{

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			Log.i(getClass().getSimpleName(), "call......onScroll()......");
			//nRenderer.setTransDistX(distanceX*360/mWidth); 
			nRenderer.setRotateAngleX(distanceX*360/mWidth);
    		nRenderer.setTransDistY(distanceY/mHeight); 
    		if(StlGlSurfaceView.this.getRenderMode()==StlGlSurfaceView.RENDERMODE_WHEN_DIRTY){
    			requestRender();
    		}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			// TODO Auto-generated method stub
			Log.i(getClass().getSimpleName(), "call......onFling()......");
//			nRenderer.setRotateAngleX(velocityX);
//			nRenderer.setRotateAngleY(velocityY);
//			if(StlGlSurfaceView.this.getRenderMode()==StlGlSurfaceView.RENDERMODE_WHEN_DIRTY){
//    			requestRender();
//    		}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
		
	}
	public class DoubleGestureListener extends SimpleOnScaleGestureListener{

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			// TODO Auto-generated method stub
			float distance = detector.getCurrentSpan()-detector.getPreviousSpan();
			nRenderer.setZoomRatio(1+distance/mHeight);
			if(StlGlSurfaceView.this.getRenderMode()==StlGlSurfaceView.RENDERMODE_WHEN_DIRTY){
				StlGlSurfaceView.this.requestRender();
			}
			return true;
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			// TODO Auto-generated method stub
			nRenderer.setDobleDown(true);
			return super.onScaleBegin(detector);
		}

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			// TODO Auto-generated method stub
			nRenderer.setDobleDown(false);
			super.onScaleEnd(detector);
		}
		
	}
}
