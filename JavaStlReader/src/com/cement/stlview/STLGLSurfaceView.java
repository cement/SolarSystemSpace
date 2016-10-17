package com.cement.stlview;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;


public class STLGLSurfaceView extends GLSurfaceView {

    private  STLGLRenderer mRenderer;

    float[] colors = {0.8f,0.3f,0.6f,0.9f};

	private int width;
	private int height;
	
	public STLGLSurfaceView(Context context) {
        super(context);
        
        width = getContext().getResources().getDisplayMetrics().widthPixels;
        height = getContext().getResources().getDisplayMetrics().heightPixels;
       
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

	private float ds;


	@Override
    public boolean onTouchEvent(MotionEvent event) {

    	int action = MotionEventCompat.getActionMasked(event);
        float x = event.getX();
        float y = event.getY();
        switch (action) {
        	case MotionEvent.ACTION_DOWN:
        		break;
            case MotionEvent.ACTION_MOVE:
                if(mRenderer.isDdown()){
                	float dm = spacing(event);
                	mRenderer.setScale((1+ (dm-ds)/height));  
                	ds = dm;
                }
            	float dx = x - mPreviousX;
            	float dy = y - mPreviousY;
        		mRenderer.setAngle(dx*360/width); 
        		mRenderer.setTrans(dy/height);  
                
                requestRender();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            	mRenderer.setDdown(true);
            	ds = spacing(event);
            	break;
            case MotionEvent.ACTION_POINTER_UP:
            	mRenderer.setDdown(false);
            	break;
            default:
        }
        mPreviousX = x;
        mPreviousY = y;

        return true;
    }
    private float spacing(MotionEvent event){ 
        float x = event.getX(0)-event.getY(1); 
        float y = event.getY(0)-event.getY(1); 
        return (float)Math.sqrt(x*x+y*y); 
    }
    
    
    
}
