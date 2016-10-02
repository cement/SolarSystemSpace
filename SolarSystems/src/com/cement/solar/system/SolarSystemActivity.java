package com.cement.solar.system;

import com.cement.solar.aster.SolarSystemRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SolarSystemActivity extends Activity{

	private SolarSystemGLSurfaceView mGLView;
	private SolarSystemRenderer mRenderer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mGLView = new SolarSystemGLSurfaceView(this);
		
		
		mRenderer = new SolarSystemRenderer(5);
		
		mGLView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		mGLView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		mGLView.setZOrderOnTop(true);
	   // mGLView.setRenderer(mRenderer);
	    mGLView.bindRenderer(mRenderer);
	    mGLView.setBackgroundResource(R.drawable.space);
	    mGLView.setDebugFlags(GLSurfaceView.DEBUG_CHECK_GL_ERROR);
	   // mGLView.refreshDrawableState();
	    setContentView(mGLView);
	}

	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mGLView != null) {
			mGLView.onResume();
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mGLView != null) {
			mGLView.onPause();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.start_earth_activity){
			return true;
		}
		if (id == R.id.start_stlreader_activity) {
			if(mGLView.getRenderMode()==GLSurfaceView.RENDERMODE_CONTINUOUSLY){
				mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			}else{
				mGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
			}
			return true;
		}
		if (id == R.id.start_Sphere_activity) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
