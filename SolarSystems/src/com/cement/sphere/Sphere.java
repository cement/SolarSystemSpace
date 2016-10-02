package com.cement.sphere;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Sphere {
	
	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private FloatBuffer normalBuffer;
	private int accuracy;
	
	public Sphere() {
		this(10);
	}
	public Sphere(int accuracy) {
		setAccuracy(accuracy);
		createSphere();
	}
	public final Sphere createSphere(){
		return createSphere(accuracy);
	}
	private final Sphere createSphere(int step){
		float coslat,cosLat,sinlat,sinLat,coslon,sinlon;
		int capacity = (180/step)*(360/step+1)*4*2;
		vertexBuffer = normalBuffer =  ByteBuffer.allocateDirect(capacity*3).order(ByteOrder.nativeOrder()).asFloatBuffer();
    	textureBuffer = ByteBuffer.allocateDirect(capacity*2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		for (float lat = -90; lat <90; lat += step) {
			coslat = (float)Math.cos(lat * Math.PI / 180.0);
            sinlat = -(float)Math.sin(lat * Math.PI / 180.0);
           
            cosLat = (float)Math.cos((lat+step) * Math.PI / 180.0);
            sinLat = -(float)Math.sin((lat+step) * Math.PI / 180.0);
            	for (float lon = 0; lon <=360; lon += step) {
            		coslon = (float)Math.cos(lon * Math.PI / 180.0);
            		sinlon = (float)Math.sin(lon* Math.PI / 180.0);
            		
            		vertexBuffer.put(coslat*sinlon);
        			vertexBuffer.put(sinlat);
        			vertexBuffer.put(coslat*coslon);
        			
        			vertexBuffer.put(cosLat*sinlon);
        			vertexBuffer.put(sinLat);
        			vertexBuffer.put(cosLat*coslon);
        			
        			textureBuffer.put(lon/360);
        			textureBuffer.put((lat+90)/180);
        			
        			textureBuffer.put((lon)/360);
        			textureBuffer.put((lat+step+90)/180);
            	}
		}
		textureBuffer.position(0);
		vertexBuffer.position(0);
		return this;
	}
	

	public FloatBuffer getVertexBuffer() {
		return vertexBuffer;
	}
	public void setVertexBuffer(FloatBuffer vertexBuffer) {
		this.vertexBuffer = vertexBuffer;
	}
	public FloatBuffer getTextureBuffer() {
		return textureBuffer;
	}
	public void setTextureBuffer(FloatBuffer textureBuffer) {
		this.textureBuffer = textureBuffer;
	}
	public FloatBuffer getNormalBuffer() {
		return normalBuffer;
	}
	public void setNormalBuffer(FloatBuffer normalBuffer) {
		this.normalBuffer = normalBuffer;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		int step = accuracy;
		if(accuracy<2){
			accuracy = 2;
		}else{ 
			for (step = accuracy%90;90%step != 0; step--);
		}
		this.accuracy = step;
	}
}
