#include <stdio.h>
#include <math.h>
#include <GLES/gl.h>

 float *sScratch = NULL ;

 const char* gluErrorString(int error) {
        switch (error) {
        case GL_NO_ERROR:
            return "no error";
        case GL_INVALID_ENUM:
            return "invalid enum";
        case GL_INVALID_VALUE:
            return "invalid value";
        case GL_INVALID_OPERATION:
            return "invalid operation";
        case GL_STACK_OVERFLOW:
            return "stack overflow";
        case GL_STACK_UNDERFLOW:
            return "stack underflow";
        case GL_OUT_OF_MEMORY:
            return "out of memory";
        default:
            return NULL;
        }
    }

 void setLookAtM(float rm[],
             float eyeX, float eyeY, float eyeZ,
             float centerX, float centerY, float centerZ, float upX, float upY,
             float upZ) {

         // See the OpenGL GLUT documentation for gluLookAt for a description
         // of the algorithm. We implement it in a straightforward way:

         float fx = centerX - eyeX;
         float fy = centerY - eyeY;
         float fz = centerZ - eyeZ;

         // Normalize f
         float rlf = 1.0f /sqrt(fx*fx+fy*fy+fz*fz);
         fx *= rlf;
         fy *= rlf;
         fz *= rlf;

         // compute s = f x up (x means "cross product")
         float sx = fy * upZ - fz * upY;
         float sy = fz * upX - fx * upZ;
         float sz = fx * upY - fy * upX;

         // and normalize s
         float rls = 1.0f / sqrt(sx*sx+sy*sy+sz*sz);
         sx *= rls;
         sy *= rls;
         sz *= rls;

         // compute u = s x f
         float ux = sy * fz - sz * fy;
         float uy = sz * fx - sx * fz;
         float uz = sx * fy - sy * fx;

         rm[0] = sx;
         rm[1] = ux;
         rm[2] = -fx;
         rm[3] = 0.0f;

         rm[4] = sy;
         rm[5] = uy;
         rm[6] = -fy;
         rm[7] = 0.0f;

         rm[8] = sz;
         rm[9] = uz;
         rm[10] = -fz;
         rm[11] = 0.0f;

         rm[12] = 0.0f;
         rm[13] = 0.0f;
         rm[14] = 0.0f;
         rm[15] = 1.0f;

         //translateM(rm, -eyeX, -eyeY, -eyeZ);
         for (int i=0 ; i<4 ; i++) {
                   rm[12 + i] += -rm[i] * eyeX - rm[4 + i] * eyeY - rm[8 + i] * eyeZ;
         }
  }
  void gluLookAt(float eyeX, float eyeY, float eyeZ,
            float centerX, float centerY, float centerZ, float upX, float upY,
            float upZ) {
            sScratch = new float[32];
            setLookAtM(sScratch, eyeX, eyeY, eyeZ, centerX, centerY, centerZ,upX, upY, upZ);
            glMultMatrixf(sScratch);
  }

 void gluOrtho2D(float left, float right,float bottom, float top) {
        glOrthof(left, right, bottom, top, -1.0f, 1.0f);
}

void gluPerspective(float fovy, float aspect,float zNear, float zFar) {
        float top = zNear * (float)atanf(fovy * (3.1415926f / 360.0));
        float bottom = -top;
        float left = bottom * aspect;
        float right = top * aspect;
        glFrustumf(left, right, bottom, top, zNear, zFar);
}
