package com.cement.solar.aster;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class SolarSystemRenderer implements Renderer  {
	
	private AssistRenderer mAssistRenderer;
	
	//private float mRotateAngle;
	
	private boolean isSignleDown;
	private boolean isDoubleDown;
	
	private boolean isLongPress;
	private boolean isRecover;
	
	
	private float mReversalAngle;
	private float mScaleFactor;
	private float mTransDistX;  
	private float mTransDistY;
	
	private float mScaleTotal = 1.0f;
	//private float mRotateTotal = 0.0f;
	private float mReversalTotal = 0.0f;
	private float mTransXTotal = 0.0f;
	private float mTransYTotal = 0.0f;
	
	public SolarSystemRenderer() {
		mAssistRenderer = new AssistRenderer();
	}
	public SolarSystemRenderer(int accuracy) {
		mAssistRenderer = new AssistRenderer(accuracy);
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		mAssistRenderer.onSurfaceCreated(gl, config);
		
		Log.v(getClass().getSimpleName(), "..............onSurface  Created...........");
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		mAssistRenderer.onSurfaceChanged(gl, width, height);
		isRecover = true;
		Log.v(getClass().getSimpleName(), "..............onSurface  Changed...........");

	}
	@Override
	public void onDrawFrame(GL10 gl) {
		this.controlDraw(gl);
		mAssistRenderer.fitDraw(gl);
	}
	public void controlDraw(GL10 gl){
		
		if(isLongPress){
			gl.glRotatef(-mReversalAngle, 1, 0, 0);
			mReversalAngle = 0;
		}
		if(isSignleDown){
			gl.glTranslatef(-mTransDistX, mTransDistY, 0);
			mTransDistY=0;
			mTransDistX=0;
		}
		if(isDoubleDown){
			gl.glScalef(mScaleFactor, mScaleFactor, mScaleFactor);
		}
		if(isRecover){
			gl.glRotatef(mReversalTotal, 1, 0, 0);
			gl.glTranslatef(-mTransXTotal, mTransYTotal, 0);
			gl.glScalef(mScaleTotal, mScaleTotal, mScaleTotal);
			isRecover = false;
		}
	}
	
	public void setScaleFactor(float scale) {
		// TODO Auto-generated method stub
		this.mScaleFactor = scale;
		this.mScaleTotal *= scale;
	}
	public void setDoubledown(boolean isDoubledown) {
		// TODO Auto-generated method stub
		this.isDoubleDown = isDoubledown;
	}
	public float getRotateAngle() {
		return mAssistRenderer.getRotateAngle();
	}
	public void setRotateAngle(float rotateAngle) {
		mAssistRenderer.setRotateAngle(rotateAngle);
	}
	public void setTransDistX(float transDistX) {
		mTransXTotal += transDistX;
		this.mTransDistX = transDistX;
	}
	public void setTransDistY(float transDistY) {
		mTransYTotal += transDistY;
		this.mTransDistY = transDistY;
	}
	public boolean isDoubleDown() {
		return isDoubleDown;
	}
	public void setDoubleDown(boolean isDoubleDown) {
		this.isDoubleDown = isDoubleDown;
	}
	public void setSignleDown(boolean isDown) {
		this.isSignleDown = isDown;
	}
	public boolean isLongPress() {
		return isLongPress;
	}
	public void setLongPress(boolean isLongPress) {
		this.isLongPress = isLongPress;
	}
	public void setReversalAngle(float reversalAngle) {
		this.mReversalAngle = reversalAngle;
		this.mReversalTotal += reversalAngle;
	}
	public float getScaleTotal() {
		return mScaleTotal;
	}
	public void setScaleTotal(float scaleTotal) {
		this.mScaleTotal = scaleTotal;
	}
	public void setRecover(boolean isRecover) {
		this.isRecover = isRecover;
	}
	public float getReversalTotal() {
		return mReversalTotal;
	}
	public void setReversalTotal(float reversalTotal) {
		this.mReversalTotal = reversalTotal;
	}
	public float getTransXTotal() {
		return mTransXTotal;
	}
	public void setTransXTotal(float transXTotal) {
		this.mTransXTotal = transXTotal;
	}
	public float getTransYTotal() {
		return mTransYTotal;
	}
	public void setTransYTotal(float transYTotal) {
		this.mTransYTotal = transYTotal;
	}
	public float[] gluUnprojectTest(GL10 gl){
		return null;
		//GLU.gluUnProject(winX, winY, winZ, model, modelOffset, project, projectOffset, view, viewOffset, obj, objOffset)
	}
}
