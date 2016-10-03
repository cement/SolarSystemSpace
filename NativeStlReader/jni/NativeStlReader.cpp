#include <global.h>
#include <GLES/gl.h>
#include <GLES/glext.h>
#include "com_cement_stlreader_reader_NativeRenderer.h"
#include "ConstomGLU.h"
#include "StlReader.h"
#define  COLORS 4
#define  COORDS 3

//extern long   facet_count;
float* facetNormal;
float angle = 0;
float trans = 0;
float scale = 0;
float rang = 0;
bool  ddown = false;
void surfaceCreated(){
	glClearColor(0.1f,0.3f,0.4f,0.5f);
}
void surfaceChanged(int width,int height){
    glViewport(0,0,width,height);

    rang = (float)(max - min);
    LOGI("width: %d;   height: %d",width,height);
    LOGI("max = %f; ",max);
    LOGI("min = %f; ",min);
    LOGI("rang = %f; ",rang);
    float radio = (float)width/height;
    glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	//glOrthof(0,0,0,0,0,0);
//	glFrustumf(-100*radio,100*radio,-100,100,100,300);
	glFrustumf(-rang*radio,rang*radio,-rang,rang,rang,rang*3);
	//gluPerspective(90,width/height,rang,rang*3);

	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	gluLookAt(0,-rang*2,0,0,0,0,0,0,1);

	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);
	glShadeModel(GL_SMOOTH);


/*******************************************************************************************************/
	float amb[] = { 1.0f, 1.0f, 1.0f, 1.0f, };
	float diff[] = { 1.0f, 1.0f, 1.0f, 1.0f, };
	float spec[] = { 1.0f, 1.0f, 1.0f, 1.0f, };
	//float pos[] = { 0.0f, 5.0f, 5.0f, 1.0f, };
	float pos[] = {rang,rang,rang, 0.0f};
	//float pos[] = { 0.0f, 50.0f, 50.0f, 1.0f, };
	float spot_dir[] = { 0.0f, -1.0f, 0.0f, };
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);

	glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, diff);
	glLightfv(GL_LIGHT0, GL_SPECULAR, spec);
	glLightfv(GL_LIGHT0, GL_POSITION, pos);
//	glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, spot_dir);
//	glLightf(GL_LIGHT0, GL_SPOT_EXPONENT, 0.0f);
/*******************************************************************************************************/
	float mat_amb[] = {0.2f * 0.4f, 0.2f * 0.4f, 0.2f * 1.0f, 1.0f,};
	float mat_diff[] = {0.4f, 0.4f, 1.0f, 1.0f,};
	float mat_spec[] = {0.5f, 0.5f, 0.5f, 1.0f,};

	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, mat_amb);
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, mat_diff);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, mat_spec);
	//glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 64.0f);

/*******************************************************************************************************/
   glEnable(GL_NORMALIZE);
   glEnable(GL_RESCALE_NORMAL);

}
float* colorBuffer = NULL;
float* coordBuffer = NULL;
void surfaceDrawing(){

	// Draw background color
	    glColor4f(0.3f,0.4f,0.5f,0.5f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		/*.....................................................................*/
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);
		//glEnableClientState(GL_COLOR_ARRAY);

		//glColor4f(0.4f, 0.6f, 0.7f, 0.5f);
		//glColorPointer(COLORS, GL_FLOAT, 0, colorBuffer);
		//glColorPointer(4, GL_BYTE, 0, colorBuffer);
		glVertexPointer(COORDS, GL_FLOAT, 0, facets.coords);

		//glNormal3f(1,1,1);
		glNormalPointer(GL_FLOAT,0,facetNormal);
		glDrawArrays(GL_TRIANGLES, 0, facets.counts*3);

		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_NORMAL_ARRAY);
		//glDisableClientState(GL_COLOR_ARRAY);

		/*.....................................................................*/

//		glRotatef(mAngle, 0.0f, 0.0f, 1.0f);
//		glTranslatef(0.0f, 0.0f, mTrans);
//		if(mDdown){
//			glScalef(mScale, mScale, mScale);
//		}
		glRotatef(angle,0,0,1);
		glTranslatef(0,0,trans*rang);
		if(ddown){
		    glScalef(scale,scale,scale)	;
		}
}

JNIEXPORT void JNICALL Java_com_cement_stlreader_reader_NativeRenderer_setZoomRatio
  (JNIEnv *env, jobject jobj, jfloat ratio){
    scale = ratio;
}

JNIEXPORT void JNICALL Java_com_cement_stlreader_reader_NativeRenderer_setTransDistX
  (JNIEnv *, jobject, jfloat){

}

JNIEXPORT void JNICALL Java_com_cement_stlreader_reader_NativeRenderer_setTransDistY
  (JNIEnv *env, jobject jobj, jfloat transY){
	trans = transY;
	LOGW("transY:  %f",transY);
}

JNIEXPORT void JNICALL Java_com_cement_stlreader_reader_NativeRenderer_setRotateAngleX
  (JNIEnv *env, jobject jobj, jfloat angleX){
	angle = angleX;
	LOGW("angleX:   %f",angleX);
}

JNIEXPORT void JNICALL Java_com_cement_stlreader_reader_NativeRenderer_setRotateAngleY
  (JNIEnv *, jobject, jfloat){

}

JNIEXPORT void JNICALL Java_com_cement_stlreader_reader_NativeRenderer_setDobleDown
  (JNIEnv *env, jobject jobj, jboolean dDown){
	ddown = dDown;
}

JNIEXPORT void JNICALL Java_com_cement_stlreader_reader_NativeRenderer_drawStlModel
  (JNIEnv *env, jobject jobj, jstring jpath){
	const char* path = env->GetStringUTFChars(jpath,JNI_FALSE);
	readStlFile(path);
	env->ReleaseStringUTFChars(jpath,path);
	float *normal = new float[facets.counts*9];
	int index = 0;
	for (int x = 0; x < facets.counts; x++) {
		for (int y = 0; y < 3; y++) {
			normal[index++]=facets.normal[x];
		}
	}
	facetNormal = normal;
	LOGI("path:  %s",path);

}
JNIEXPORT void JNICALL Java_com_cement_stlreader_reader_NativeRenderer_surfaceCreated
  (JNIEnv *env, jobject jobj){
	surfaceCreated();
	LOGI("surfaceCreated:......");
}

JNIEXPORT void JNICALL Java_com_cement_stlreader_reader_NativeRenderer_surfaceChanged
  (JNIEnv *env, jobject jobj, jint width, jint height){
	surfaceChanged(width,height);
	LOGI("surfaceChanged:......");
}

JNIEXPORT void JNICALL Java_com_cement_stlreader_reader_NativeRenderer_surfaceDrawing
  (JNIEnv *env, jobject jobj){
	surfaceDrawing();
	//LOGI("surfaceDrawing:......");

}
