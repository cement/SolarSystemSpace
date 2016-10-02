
package com.cement.solar.system;

import com.cement.solar.aster.SolarSystemRenderer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;


public class SolarSystemGLSurfaceView extends GLSurfaceView {
	
	private int width;
	private int height;
    
	private GestureDetector mSingleGesture;
	//private SimpleOnGestureListener mSignlGesture;
	private ScaleGestureDetector mDoubleGesture;
	//private SimpleOnScaleGestureListener mDobleGesture;
	
	private SolarSystemRenderer mRenderer;
	private Vibrator vibrator;
	private long [] pattern = {0,100};
	public SolarSystemGLSurfaceView(Context context) {
        super(context);
        setId(123456);
        width = getContext().getResources().getDisplayMetrics().widthPixels;
        height = getContext().getResources().getDisplayMetrics().heightPixels;
        
        mSingleGesture = new GestureDetector(context, new SingleGestureListener());  
        mDoubleGesture = new ScaleGestureDetector(context, new DoubleGestureListener());
        vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);  
    }

	public void bindRenderer(SolarSystemRenderer renderer){
		mRenderer = renderer;
		setRenderer(renderer);
	}
	@Override
    public boolean onTouchEvent(MotionEvent event) {
		mSingleGesture.onTouchEvent(event);
		if(!mRenderer.isLongPress()){
			mDoubleGesture.onTouchEvent(event);
		}
		if(event.getAction()==MotionEvent.ACTION_UP){
			mRenderer.setLongPress(false);
			Log.v(getClass().getSimpleName(), "............MotionEvent.ACTION_UP  ..............");
		}
		return true;
    }
	
	@Override
	protected Parcelable onSaveInstanceState() {
		System.out.println(".............onSaveInstanceState...............");
		Bundle saveBundle = new Bundle();
		saveBundle.putFloat("RotateAngle", mRenderer.getRotateAngle());
		saveBundle.putFloat("ScaleTotal", mRenderer.getScaleTotal());
		saveBundle.putFloat("ReversalTotal", mRenderer.getReversalTotal());
		saveBundle.putFloat("TransXTotal", mRenderer.getTransXTotal());
		saveBundle.putFloat("TransYTotal", mRenderer.getTransYTotal());
		saveBundle.putParcelable("system", super.onSaveInstanceState());
		return saveBundle;
	}
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		System.out.println(".............onRestoreInstanceState...............");
		Bundle saveBundle = (Bundle)state;
		mRenderer.setRotateAngle(saveBundle.getFloat("RotateAngle",0));
		mRenderer.setScaleTotal(saveBundle.getFloat("ScaleTotal",1.0f));
		mRenderer.setReversalTotal(saveBundle.getFloat("ReversalTotal",0));
		mRenderer.setTransXTotal(saveBundle.getFloat("TransXTotal",0));
		mRenderer.setTransYTotal(saveBundle.getFloat("TransYTotal",0));
		super.onRestoreInstanceState(saveBundle.getParcelable("system"));
	}
	@Override
	public void onPause() {
		super.onPause();
		Log.v(getClass().getSimpleName(), "............View.onPause.............");
	}
	@Override
	public void onResume() {
		super.onResume();
		Log.v(getClass().getSimpleName(), "............View.onResume.............");
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
			mRenderer.setLongPress(true);
	        vibrator.vibrate(pattern,1);
			super.onLongPress(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			if(mRenderer.isLongPress()){
				mRenderer.setReversalAngle(distanceY/height*180); 
			}else if(!mRenderer.isDoubleDown()){
				mRenderer.setTransDistX(distanceX/width); 
				mRenderer.setTransDistY(distanceY/height); 
			}
			
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
			mRenderer.setSignleDown(true);
			return super.onDown(e);
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
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
			float factor = detector.getScaleFactor();
			mRenderer.setScaleFactor(factor);
			//requestRender();
			return true;
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			mRenderer.setDoubledown(true);
			return true;
		}

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			Log.i(getClass().getSimpleName(), "...call...: "+Thread.currentThread().getStackTrace()[2].getMethodName()+"......");
			mRenderer.setDoubledown(false);;
			super.onScaleEnd(detector);
		}
    	
    }
    
    
}
