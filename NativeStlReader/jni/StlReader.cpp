#include <global.h>
#include <StlReader.h>
struct Model facets;

float max;
float min;
static bool isAscii(FILE* file){
	char hearder[80];
	fgets(hearder,80,file);
	rewind(file);
	return strstr(hearder,"solid")!= NULL;
}
static bool isBinary(FILE* file){
	fseek(file,-24,SEEK_END);
			for (int i = 0; i < 24; i++) {
				if(getc(file)=='\n') {
					return false;
				}
	}
	return true;
}
static void readStlFileAscii(FILE* file){
    long size=0;
    int coordsIndex=0;
    int normalIndex=0;
    fseek(file,0L,SEEK_END);
    size=ftell(file);
    rewind(file);
    while (getc(file) != '\n');
    int var = 0;
    for(int i = 0;feof(file)==0;++var){
		if(getc(file)!='\0')
		if(++var==7) break;
    }
    float const * freeCoords = facets.coords;
    float const * freeNormal = facets.normal;
    facets.counts = size/var;
    facets.coords = new float[facets.counts*9];
    facets.normal = new float[facets.counts*3];
    //facet_coords = (float*)malloc(facet_count*9);
    for (int i=0;i<facets.counts;i++){
        char x[20],y[20],z[20];
        if(3==fscanf(file,"%*s %*s %20s %20s %20s\n",x,y,z)){
        	facets.normal[normalIndex++]=atof(x);
        	facets.normal[normalIndex++]=atof(y);
        	facets.normal[normalIndex++]=atof(z);
        }
        fscanf(file,"%*s %*s");
        for (int j = 0; j < 3; j++) {
			if (3==fscanf(file,"%*s %20s %20s %20s\n",x,y,z)){
			facets.coords[coordsIndex++]=atof(x);
			facets.coords[coordsIndex++]=atof(y);
			facets.coords[coordsIndex++]=atof(z);
			}
		}
        fscanf(file,"%*s");
        fscanf(file,"%*s");
    }
	max = FLT_MIN;min = FLT_MAX;
	for (int i = 0; i < facets.counts*9; i++) {
		if(facets.coords[i] > max) max = facets.coords[i];
		if(facets.coords[i] < min) min = facets.coords[i];
	}
	delete []freeCoords;
	freeCoords =NULL;
	delete []freeNormal;
	freeNormal =NULL;

}
static void readStlFileBinary(FILE* file){
	char name[80];
	long facetCount;
	rewind(file);
	if(fread(name,80,1,file)==1){
		LOGI("read file name success: %s",name);
		float const * freeCoords = facets.coords;
		float const * freeNormal = facets.normal;
		if(fread(&facetCount,4,1,file)==1){
			LOGI("read length success : %ld",facetCount);
			facets.counts = facetCount;
			facets.coords = new float[facets.counts*9];
			facets.normal = new float[facets.counts*3];
			//facet_coords = (float*)malloc(facet_count*9);
			for(int x=0;x<facetCount;x++){
				//fseek(file,12,SEEK_CUR);
				fread(facets.normal+x*3,12,1,file);
				fread(facets.coords+x*9,36,1,file);
				//fread(facets.colors,2,1,file);
				fseek(file,2,SEEK_CUR);
			}
			max = FLT_MIN;min = FLT_MAX;
			for (int i = 0; i < facets.counts*9; i++) {
				if(facets.coords[i] > max) max = facets.coords[i];
				if(facets.coords[i] < min) min = facets.coords[i];
			}
			delete [] freeCoords;
			freeCoords = NULL;
			delete [] freeNormal;
			freeNormal = NULL;
		}
	}
}

void readStlFile(const char* path){
	FILE *file = fopen(path,"r");
	LOGI(".................call native method readFile().............;  %s",path);
	if(file!=NULL){
		if(isAscii(file)&&!isBinary(file)){
			LOGI(".................ascii................");
			readStlFileAscii(file);
		}else if(isBinary(file)){
			LOGI(".................binary................");
			readStlFileBinary(file);
		}else{
			LOGI("file is not STL foramtter or it has been bad!");
		}
	}
	fclose(file);
}
