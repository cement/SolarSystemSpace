package com.cement.earth.system;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.cement.solar.aster.AssistRenderer;
import com.cement.solar.aster.FitScene;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class EarthSystemRenderer implements Renderer  {
	

	private FitScene mAster;
	private Renderer mAssistRenderer;
	
	private int[] mTextureIds;
	
	private float angle;
	
	public EarthSystemRenderer() {
		mAster = new EarthAster();
		mAssistRenderer = new AssistRenderer();
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		mAssistRenderer.onSurfaceCreated(gl, config);
		mAster.fitLlights(gl);
		mAster.fitMateral(gl);
		if(mTextureIds==null){
			mTextureIds = mAster.fitTexture(gl);
		}
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		mAssistRenderer.onSurfaceChanged(gl, width, height);
	}
	@Override
	public void onDrawFrame(GL10 gl) {
		draw(gl);
	}
	public void draw(GL10 gl){
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glLoadIdentity();
    	GLU.gluLookAt(gl,0.0f, 0.0f, 10.0f, 0.0f, 0.0f, 0.0f,0.0f, 1.0f, 0.0f);
       
    	gl.glPushMatrix(); 
	
    	gl.glRotatef(angle, 0, 1.0f, 0.0f);
    	gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[0]);
		mAster.fitDraw(gl); 
	
		gl.glPopMatrix();  
    	gl.glPushMatrix(); 
		//draw Moon
    	gl.glRotatef(angle*27/30, 0, 1, 0); 
		gl.glTranslatef(3.3f, 0, 0); 
		gl.glScalef(.16f, .16f, .16f); 
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[1]);
		mAster.fitDraw(gl); 
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		
		gl.glRotatef(-angle, 0, 1, 1); 
		gl.glTranslatef(1.2f, 0, 0); 
		gl.glScalef(.03f, .03f, .03f); 
		//gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[1]);
		mAster.fitDraw(gl);  
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		
		gl.glRotatef(angle*2, 0, 1, 1); 
		gl.glTranslatef(1.15f, 0, 0); 
		gl.glScalef(.025f, .025f, .025f);
		
		mAster.fitDraw(gl); 
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		
		gl.glRotatef(angle*1.5f, 0, 0, 1); 
		gl.glTranslatef(1.15f, 0, 0); 
		gl.glScalef(.025f, .025f, .025f);
		mAster.fitDraw(gl);  
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		
		gl.glRotatef(angle, 0, 1, 0); 
		gl.glTranslatef(1.15f, 0, 0); 
		gl.glScalef(.025f, .025f, .025f);
		mAster.fitDraw(gl);  
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		
		gl.glRotatef(angle, 0, 1, 1); 
		gl.glTranslatef(1.1f, 0, 0); 
		gl.glScalef(.02f, .02f, .02f);
		mAster.fitDraw(gl);  
		
		gl.glPopMatrix();
		
		angle++;
	}
}
