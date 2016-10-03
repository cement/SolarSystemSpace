#ifndef _StlReader_H
#define _StlReader_H
struct Model{
	long   counts;
	float* normal;
	float* coords;
	int* colors;
};
extern struct Model facets;
extern float max;
extern float min;

void readStlFile(const char* path);
#endif
