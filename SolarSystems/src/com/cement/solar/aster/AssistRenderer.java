package com.cement.solar.aster;

import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class AssistRenderer implements FitScene,Renderer{

	private float mRotateAngle;
	
	public float getRotateAngle() {
		return mRotateAngle;
	}
	public void setRotateAngle(float mRotateAngle) {
		this.mRotateAngle = mRotateAngle;
	}

	private SolarAster mAster;
	private int[] mTextureIds;
	
	public AssistRenderer() {
		mAster = new SolarAster();
	}
	public AssistRenderer(int accuracy) {
		mAster = new SolarAster(accuracy);
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		surfaceCreated(gl);
		fitComplate(gl);
	}

	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		surfaceChanged(gl, width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		fitDraw(gl);
	}
	
	private void surfaceCreated(GL10 gl) {
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
    	gl.glEnable(GL10.GL_CULL_FACE);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	private void surfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 30.0f, (float) width / (float) height, 1.0f, 100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		GLU.gluLookAt(gl, 0.0f, 0.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
	}

	@Override
	public void fitDraw(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

//		gl.glLoadIdentity();
//	    GLU.gluLookAt(gl,0,0, 10, 0, 0, 0,0, 1, 0);
	
		gl.glPushMatrix(); 
		
    	gl.glRotatef(mRotateAngle/10, 0.0f, 1.0f, 0.0f);
    	//gl.glScalef(1.5f, 1.5f, 1.5f);
    	gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[0]);
		mAster.fitDraw(gl);
		
		
		gl.glPopMatrix();  
		gl.glPushMatrix();  //水星 
		
		gl.glRotatef(mRotateAngle*2f, 0, 1, 0); 
		gl.glTranslatef(3*0.38f, 0, 0); 
		gl.glScalef(.06f, .06f, .06f); 
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[1]);
		mAster.fitDraw(gl); 
		
		gl.glPopMatrix();  
		gl.glPushMatrix(); //金星
		
		gl.glRotatef(mRotateAngle*1.5f, 0, 1, 0); 
		gl.glTranslatef(3*0.72f, 0, 0); 
		gl.glScalef(.14f, .14f, .14f); 
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[2]);
		
		mAster.fitDraw(gl); 
		
		gl.glPopMatrix();  
    	gl.glPushMatrix(); //地球
    	 
    	gl.glRotatef(mRotateAngle, 0, 1, 0); 
		gl.glTranslatef(3.0f, 0, 0); 
		gl.glScalef(.15f, .15f, .15f); 
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[3]);
		mAster.fitDraw(gl); 
		
		gl.glRotatef(13.0f, 1, 0, 0);
		
//		gl.glPopMatrix();
//		gl.glPushMatrix();
		
		gl.glRotatef(mRotateAngle*3, 0, 1, 0); 
		gl.glTranslatef(1.5f, 0, 0); 
		gl.glScalef(.2f, .2f, .2f); //月球
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[4]);
		mAster.fitDraw(gl);  
		
		
		gl.glPopMatrix();
		gl.glPushMatrix();//火星
		
		gl.glRotatef(mRotateAngle/1.5f, 0, 1, 0); 
		gl.glTranslatef(3*1.52f, 0, 0); 
		gl.glScalef(.08f, .08f, .08f);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[5]);
		
		mAster.fitDraw(gl); 
		
		gl.glPopMatrix();
		gl.glPushMatrix();//木星
		
		gl.glRotatef(mRotateAngle/2f, 0, 1, 0); 
		gl.glTranslatef(3*5.2f, 0, 0); 
		gl.glScalef(0.15f*5.0f,   0.15f*5.0f, 0.15f*5.0f);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[6]);
		mAster.fitDraw(gl);  
		
		
		gl.glPushMatrix();
		
		gl.glRotatef(mRotateAngle*2.5f, 0, 1, 0); 
		gl.glTranslatef(1.0f, 0, 0); 
		gl.glScalef(.15f, .15f, .15f); //木卫三
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[4]);
		mAster.fitDraw(gl); 
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		
		gl.glRotatef(mRotateAngle*3, 0, 1, 0); 
		gl.glTranslatef(0.8f, 0, 0); 
		gl.glScalef(.1f, .1f, .1f); //木卫二
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[4]);
		mAster.fitDraw(gl); 
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		
		gl.glRotatef(mRotateAngle*2.0f, 0, 1, 0); 
		gl.glTranslatef(1.0f, 0, 0); 
		gl.glScalef(.15f, .15f, .15f); //木卫一
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[4]);
		mAster.fitDraw(gl);
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		
		gl.glRotatef(-mRotateAngle*2.0f, 0, 1, 0); 
		gl.glTranslatef(1.0f, 0, 0); 
		gl.glScalef(.1f, .1f, .1f); //木卫
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[4]);
		mAster.fitDraw(gl);
		
		gl.glPopMatrix();
		
		gl.glPopMatrix();
		gl.glPushMatrix();//土星
		
		
		gl.glRotatef(mRotateAngle/2.5f, 0, 1, 0); 
		gl.glTranslatef(3*5.0f, 0, 0); 
		gl.glScalef(0.15f*3.5f, 0.15f*3.5f, 0.15f*3.5f);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[7]);
		mAster.fitDraw(gl);
	
		gl.glRotatef(11.0f, 1, 0, 1); 
		
		//gl.glRotatef(angle/2.5f, 0, 1, 0); 
		//gl.glTranslatef(3*9.54f, 0, 0); 
		gl.glScalef(1.5f, 0.15f, 1.5f);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[7]);
		mAster.fitDraw(gl);  
		
		
		gl.glPushMatrix();
		
		gl.glRotatef(-mRotateAngle*2, 0, 1, 0); 
		gl.glTranslatef(0.5f, 0, 0); 
		gl.glScalef(.2f, .2f, .2f); //土卫六
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[4]);
		mAster.fitDraw(gl); 
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		
		gl.glRotatef(mRotateAngle*2.5f, 0, 1, 0); 
		gl.glTranslatef(0.8f, 0, 0); 
		gl.glScalef(.1f, .1f, .1f); //土卫五
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[4]);
		mAster.fitDraw(gl);
		
		gl.glPopMatrix();
		
		gl.glPopMatrix();
		gl.glPushMatrix();//天王星
		
		gl.glRotatef(mRotateAngle/3, 0, 1, 0); 
		gl.glTranslatef(3*10.0f, 0, 0); 
		gl.glScalef(0.15f*2.0f, 0.15f*2.5f, 0.15f*2.5f);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[8]);
		mAster.fitDraw(gl);  
		
		gl.glPopMatrix();
		gl.glPushMatrix();//海王星
		
		gl.glRotatef(mRotateAngle/4, 0, 1, 0); 
		gl.glTranslatef(3*15.0f, 0, 0); 
		gl.glScalef(0.15f*3.88f, 0.15f*3.88f, 0.15f*3.88f);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureIds[9]);
		mAster.fitDraw(gl);  
		gl.glPopMatrix();
		
		mRotateAngle++;
	}
	@Override
	public void fitLlights(GL10 gl) {
		// TODO Auto-generated method stub
		mAster.fitLlights(gl);
	}
	@Override
	public void fitMateral(GL10 gl) {
		// TODO Auto-generated method stub
		mAster.fitMateral(gl);
	}
	@Override
	public int[] fitTexture(GL10 gl) {
		Log.v(getClass().getSimpleName(), "............mTextureIds[0]............."+Arrays.toString(mTextureIds));
		mTextureIds = mAster.fitTexture(gl);
		return mTextureIds;
	}
	
	public void fitComplate(GL10 gl){
		fitLlights(gl);
		fitMateral(gl);
		fitTexture(gl);
	}
}
